<template>
  <div class="container pt-10">
    <div class="app-container">
    <div id="mapChoose">
      <span v-for="(item,index) in selectedMaps" :key="item.code">
        <span class="title" @click="chooseMap(item,index)">{{item.name == 'guizhou' ? '贵州' : item.name}}</span>
        <span class="icon" v-show="index + 1 != selectedMaps.length">></span>
      </span>
    </div>
      <div class="white text-center" :style="{'width':'100%','fontSize':'24px','color':'#000000', 'top':'10px','font-size':'26px', 'fontStyle':'italic', 'fontWeight':'bolder','fontFamily':'sans-serif'}">
        贵州行政区划地图
      </div>
      <div id="echart"></div>
    </div>
    <div class="stream-data">
         <div id="echart2"></div>
         <div class="flow-data" id="flow-data">
            <ul  class="infinite-list" style="overflow:auto">
              <li v-for="(item,index) in flowDataList" class="infinite-list-item">
                {{item.text}}
              </li>
            </ul>
         </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import mapCode from '@/assets/js/mapCode.js';

//https://apis.map.qq.com/ws/district/v1/search?&keyword=520000&key=4XBBZ-7KIRD-VZY4Y-PFMS7-ZRQH7-MPFIR&get_polygon=2
let myChart;
export default {
  name: "index",
  data() {
    return {
      //地图数据
      myChart: null,
      guizhouCode:520000,
      curMapName:'guizhou', // Main Map
      selectedMaps:[
        {
          name:'guizhou',
          code:'520000',
        }
      ],

      flowDataList:[{"text":"贵阳市-观山湖-由XXXX录入实有房屋"},{"text":"贵阳市-观山湖-由XXXX录入实有单位"},{"text":"贵阳市-观山湖-由XXXX录入实有人口"}],
      mapJson:"https://geo.datav.aliyun.com/areas_v3/bound/520000_full.json",

      option2:{
        title: {
          text: '各区域数据',
          subtext: 'Fake Data',
          left: 'center',
          textStyle: {             
            //标题颜色
	        	color: '#444466'
	      	},
        },
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            name: '区域数据',
            type: 'pie',
            radius: '55%',
            
            itemStyle: {
              normal: {
                color: function (colors) {
                  var colorList = [
                    '#b5d9ff',
                    '#a4d0ff',
                    '#a0c8ff',
                    '#95c4ff',
                    '#90b8ff',
                    '#89b1ff',
                    '#84afff',
                    '#7aaaff',
                    '#71a9FF'
                  ];
                  return colorList[colors.dataIndex];
                }
              },
            },
            data: [
            {name: '贵阳市',value: 20057,code:"520100"},
            {name: '遵义市',value: 15477,code:"520300"},
            {name: '六盘水市',value: 31686,code:"520200"},
            {name: '毕节市',value: 16992,code:"520500"},
            {name: '铜仁市',value: 44045,code:"520600"},
            {name: '安顺市',value: 40689,code:"520400"},
            {name: '黔西南布依族苗族自治州',value: 37659,code:"522300"},
            {name: '黔东南苗族侗族自治州', value: 45180,code:"522600"},
            {name: '黔南布依族苗族自治州',value: 55204,code:"522700"},
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
    };
  },

 mounted() {
    //this.init();
    this.init2();
    this.dataInit();
    
    //初始化地图
    this.getMapData(this.guizhouCode);
    window.addEventListener('resize', this.resizeCharts);
  },
  beforeDestroy() {
    window.addEventListener('resize',this.resizeCharts);
  },
  methods: {
    dataInit(){
      let that=this;
      let i=0;
    },
    scrollToBottom() {
        var div = document.getElementById('flow-data')
        div.scrollTop = div.scrollHeight
    },
    goTarget(href) {
      window.open(href, "_blank");
    },
    async getJson(){
       let res = await axios.get(this.mapJson);
       return res;
    },
    init(){
       let that=this;
        myChart = this.$echarts.init(
          document.getElementById("echart")
        );
         this.getLoadEcharts();
         myChart.on('click',function(mapR){
           that.changeMap(mapR);
         })
    },
    init2(){
      let that=this;
       let myChart2 = this.$echarts.init(
          document.getElementById("echart2")
        );
        let option = this.option2;
        myChart2.setOption(option);
    },
     getLoadEcharts() {
         this.getJson().then(res=>{
            this.$echarts.registerMap('贵州', JSON.stringify(res.data));
            let option = this.options;
            myChart.setOption(option);
         })
      },
      changeMap(item){
        console.log(item.data);
        this.options.title.text=item.data.name;
        this.mapJson="https://geo.datav.aliyun.com/areas_v3/bound/"+item.data.code+"_full.json";
        this.getLoadEcharts();
      },
      fullScreen(){

      },

      //绘制地图
      drawMapChart(mapName,mapJSON){
        if (this.myChart != null && this.myChart != "" && this.myChart != undefined) {
            this.myChart.dispose();
        }

        this.myChart = this.$echarts.init(document.getElementById('echart'));
        this.$echarts.registerMap(mapName,mapJSON);
        this.myChart.setOption({
          //地图选项
          tooltip: {
              formatter:function(params,ticket, callback){
                  let population = 0
                  for(let i=0; i<mapCode.length; i++) {
                      if(mapCode[i].name == params.name) {
                          population = mapCode[i].population;
                          break;
                      }
                  }
                  return params.data.name+'<br />人口'+ ':' + population
              }//数据格式化
          },
          /*非渐变色
          dataRange: {
              splitList: [
                  {start: 0, end: 100000, color: '#E0E0E0'},
                  {start: 100000, end: 300000, color: '#F3A482'},
                  {start: 300000, end: 500000, color: '#FCDBC7'},
                  {start: 500000, end: 700000, color: '#92C5DE'},
                  {start: 700000, end: 900000, color: '#5996B7'},
                  {start: 900000, color: '#2066AC'}
              ]
          },*/
          //渐变色
          visualMap: {
            min: 100000,
            max: 1000000,
            // text: ['100', '0'],
            realtime: false,
            calculable: true,
            inRange: {
              color: ['#A4C8FF', '#71A9FF']
            }
          },
          series: [
              {
              type: 'map',
              map: mapName,
              roam: true,  // 是否开启鼠标缩放和平移漫游
              selectedMode: 'false', // 是否允许选中多个区域
              label:{
                  show:true,
                  color:'#a8e6e1',
              },
              emphasis:{
                  label:{
                  show:true,
                  color:'#ffffff',
                  }
              },
              itemStyle: {
                  normal: {
                  areaColor: "rgba(23, 27, 57,0)",
                  borderColor: "#1dc199",
                  borderWidth: 1
                  }
              },
              data: this.initMapData(mapJSON),
              }
          ]
        });
  
        
  
        this.myChart.on('click', (params) => {
            let map = null;
            for(let i=0; i<mapCode.length; i++) {
                if(mapCode[i].name == params.name) {
                    map = mapCode[i].code;
                    break;
                }
            }
            const files = require.context('/public/json/map/', false, /\.json/);
            const filedata = files.keys();
            let code_exist = false;
            filedata.forEach((item) => {
                if(item === './' + map + '.json') {
                    code_exist = true;
                }
            });
            if(code_exist){
                this.curMapName = params.name;
                this.getMapData(map);
    
                // 为地图标题菜单存入（过滤同一地图多次点击情况）点击地图信息
                let selectedCodes = [];
                this.selectedMaps.forEach( item => selectedCodes.push(item.code));
                if(!selectedCodes.includes(map)){
                this.$set(this.selectedMaps,this.selectedMaps.length,{name: this.curMapName, code: map}); 
                }
                
            }else{
                this.$message({message: '暂无地图数据',type: 'warning',showClose: true});
            }
        });
      },
      initMapData(mapJson) {
        let mapData = [];
        for (let i = 0; i < mapJson.features.length; i++) {
            let population = 0;
            for(let j=0; j<mapCode.length; j++) {
                if(mapJson.features[i].properties.name == mapCode[j].name) {
                    population = mapCode[j].population;
                    break;
                }
            }
            mapData.push({ name: mapJson.features[i].properties.name, value: population});
        }
        return mapData;
      },
      // 地图标题菜单点击
      chooseMap(item,index){
        this.selectedMaps.splice(index + 1); 
        this.getMapData(item.code)
      },
      // 获取地图数据
      getMapData(map){
        axios.get(`/json/map/${map}.json`).then((res) => {
          if(res.status == 200){
            const mapJSON = res.data;
            this.drawMapChart(this.curMapName,mapJSON);
          } 
        }).catch((err) => {
          this.$message({message: err,type: 'error',showClose: true});
        })
      }
  },
};
</script>

<style scoped lang="scss">
  .container{
    //background-image: url('../assets/images/bgImg.png');
    min-height:calc(700px);
  }
  .mapView{
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
  .app-container{
      width: 65%;
      height: 1000px;
      margin-left: 30px;
      float: left;
  }
  #echart{
      width: 100%;
      height: 55%;
      float: right;
  }
  #mapChoose {
    position: relative;
    top: 60px;
    left: 20px;
    color: #555;
    float: left;
    .title {
      padding: 5px;
      border-top: 1px solid rgba(132, 219, 233, 0.8);
      border-bottom: 1px solid rgba(147, 235, 248, 0.8);
      cursor: pointer;
    }
    .icon {
      font-family: 'simsun';
      font-size: 25px;
      margin: 0 11px;
    }
  }
  #echart2{
      width: 100%;
      height: 300px;
      font-size: 20px;
      top: 30px;
      color: #224422;
  }
  .stream-data{
    float: right;
    border:1px solid #903e3e;
    height: 600px;
    width: 30%;
    overflow: hidden;
    font-style: oblique;
  }
  .fun-btn{
    float: left;
  }
  .flow-data{
    height:500px;
    overflow-y: scroll;
    color: #442244;
  }

  ::-webkit-scrollbar {
  width:12px;
  }
  /* 滚动槽 */
  ::-webkit-scrollbar-track {
  -webkit-box-shadow:inset006pxrgba(0,0,0,0.3);
  border-radius:10px;
  }
  /* 滚动条滑块 */
  ::-webkit-scrollbar-thumb {
  border-radius:10px;
  background:rgba(0,0,0,0.1);
  -webkit-box-shadow:inset006pxrgba(0,0,0,0.5);
  }
  ::-webkit-scrollbar-thumb:window-inactive {
  background:rgba(255,0,0,0.4);
  }
</style>
