<template>
  <div class="app-container">
    <div><el-button type="primary"  @click="mapModel">表单模式</el-button></div>
    <div id="container"></div>
  </div>
</template>
<script>
import {
  listAddress_house,
  getAddress_house,
  delAddress_house,
  addAddress_house,
  updateAddress_house,
  exportAddress_house,
  updateAddress_houseOne,
  updateAddress_houseTwo
} from "@/api/applet/address_house";
import ImageUpload from '@/components/ImageUpload';
import {listRegion} from "@/api/applet/region";
import Axios from "axios";

export default {
  name: "Address_house_map",
  components: {
    ImageUpload,
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // addressHouse表格数据
      address_houseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      isShow: false,
      // 是否显示弹出层
      look: false,
      // 是否显示弹出层
      watch: false,
      // 审核状态字典
      auditStateOptions: [],
      // 提交状态字典
      commitStatusOptions: [],
      // 提交状态字典
      commitStatusOptionsOne: [],
      // 地址类型字典
      addressTypeOptions: [],
      // 国家字典
      nationCodeOptions: [],
      // 产权类型字典
      propertyTypeOptions: [],
      // 房屋类型字典
      houseTypeOptions: [],
      // 居住现状字典
      livingStatusOptions: [],
      // 房屋用途字典
      houseUseOptions: [],
      // 房屋结构字典
      buildingStructureOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      queryParamOnes: {
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      provinceData: [],
      cityData: [],
      countyData: [],
      townData: [],
      committeeData: [],
      province: {},
      city: {},
      county: {},
      town: {},
      committeeValue: {},
      number: null,
    };
  },
  mounted(){
    this.initMap();
  },
  created() {
    //this.getList();
    this.getDicts("gs_address_type").then(response => {
      this.addressTypeOptions = response.data;
    });
    this.getDicts("gs_country_citizenship").then(response => {
      this.nationCodeOptions = response.data;
    });
    this.getDicts("gs_ownership_type").then(response => {
      this.propertyTypeOptions = response.data;
    });
    this.getDicts("gs_house_category").then(response => {
      this.houseTypeOptions = response.data;
    });
    this.getDicts("gs_living_status").then(response => {
      this.livingStatusOptions = response.data;
    });
    this.getDicts("gs_audit_type").then(response => {
      this.auditStateOptions = response.data;
    });
    this.getDicts("gs_commit_status").then(response => {
      this.commitStatusOptions = response.data;
      let commitStatusOptions = this.commitStatusOptions
      commitStatusOptions.forEach(function (val, index) {
        val.dictValue = Number(val.dictValue)
      })
      this.commitStatusOptions = commitStatusOptions
    });
    this.getDicts("gs_house_use").then(response => {
      this.houseUseOptions = response.data;
    });
    this.getDicts("gs_house_structure").then(response => {
      this.buildingStructureOptions = response.data;
    });
  },
  methods: {
    mapModel(){
       this.$store.dispatch("tagsView/delView", this.$route);
       this.$router.replace({ path: '/smallProgramManagement/address_house',query: {}})
    },
    initMap() {
            Axios.get("https://apis.map.qq.com/ws/district/v1/search?&keyword=520000&key=4XBBZ-7KIRD-VZY4Y-PFMS7-ZRQH7-MPFIR&get_polygon=2").then(res=>{
              console.log(res);
            })
            //https://apis.map.qq.com/ws/district/v1/search?&keyword=520000&key=4XBBZ-7KIRD-VZY4Y-PFMS7-ZRQH7-MPFIR&get_polygon=2
    				//初始化地图
    				var map = new window.TMap.Map("container", {
    					zoom: 18, //设置地图缩放级别
              style:"style1"
    				});
    				//创建并初始化MultiMarker
    				var markerLayer = new TMap.MultiMarker({
    					map: map, //指定地图容器
    					//样式定义
    					styles: {
    						// 点标记样式
    						"marker": new TMap.MarkerStyle({
    							"width": 20, // 样式宽
    							"height": 30, // 样式高
    							"src": this.markerUrl,
    							"anchor": {
    								x: 10,
    								y: 30
    							}, // 描点位置
    						}),
    					},
    					//点标记数据数组
    					geometries: [{
    						"id": "1", //点标记唯一标识，后续如果有删除、修改位置等操作，都需要此id
    						"styleId": 'marker', //指定样式id
    						"position": new TMap.LatLng(22.736481, 113.920514), //点标记坐标位置
    					}]
    				});
    				console.log(map);
    },
    /** 查询addressHouse列表 */
    getList() {
      this.loading = true;
      listAddress_house(this.queryParams).then(response => {
        this.address_houseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 地址类型字典翻译
    addressTypeFormat(row, column) {
      return this.selectDictLabel(this.addressTypeOptions, row.addressType);
    },
    // 国家字典翻译
    nationCodeFormat(row, column) {
      return this.selectDictLabel(this.nationCodeOptions, row.nationCode);
    },
    // 产权类型字典翻译
    propertyTypeFormat(row, column) {
      return this.selectDictLabel(this.propertyTypeOptions, row.propertyType);
    },
    // 房屋类型字典翻译
    houseTypeFormat(row, column) {
      return this.selectDictLabel(this.houseTypeOptions, row.houseType);
    },
    buildingStructureFormat(row, column) {
      return this.selectDictLabel(this.buildingStructureOptions, row.buildingStructure);
    },
    // 居住现状字典翻译
    livingStatusFormat(row, column) {
      return this.selectDictLabel(this.livingStatusOptions, row.livingStatus);
    },
    // 居住现状字典翻译
    houseUseFormat(row, column) {
      return this.selectDictLabel(this.houseUseOptions, row.houseUse);
    },
    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState);
    },
    // 提交状态字典翻译
    commitStatusFormat(row, column) {
      return this.selectDictLabel(this.commitStatusOptions, row.commitStatus);
    },
    // 取消按钮
    cancel() {
      this.isShow = false;
      this.open = false;
      this.watch = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        qrCode: null,
        address: null,
        doorPhoto: null,
        addressType: null,
        latitude: null,
        longitude: null,
        adcode: null,
        cityCode: null,
        townCode: null,
        nationCode: null,
        addressGroup: null,
        doorplate: null,
        propertyType: null,
        propertyUnit: null,
        propertyPerson: null,
        identityNumber: null,
        phoneNumber: null,
        houseType: null,
        buildingStructure: null,
        roomArea: null,
        houseNumber: null,
        houseUse: null,
        livingStatus: "0",
        remark: null,
        createUserId: null,
        createUser: null,
        createTime: null,
        createUserPhone: null,
        lastUpdateUserId: null,
        lastUpdateUser: null,
        lastUpdateUserPhone: null,
        lastUpdateTime: null,
        auditUser: null,
        auditUserId: null,
        auditState: null,
        auditUserPhone: null,
        commitStatus: 0
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getProvince(null);
      this.open = true;
      this.title = "新增地址房屋";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAddress_house(id).then(response => {
        this.form = response.data;
        this.getProvince(row);
        this.open = true;
        this.isShow = false;
        this.title = "修改地址房屋";
      });
    },
    /** 审核按钮操作 */
    handleAudit(row) {
      this.reset();
      const id = row.id || this.ids
      getAddress_house(id).then(response => {
        this.form = response.data;
        this.getProvince(row);
        this.open = true;
        this.isShow = true;
        this.title = "审核实有房屋";
        this.commitStatusOptionsOne = this.commitStatusOptions.slice(1);
      });
    },
    /** 审核按钮操作 */
    handleQRCode() {
      this.title = "生成二维码";
      this.watch = true;
    },

    handleShowQR(row) {
      this.reset();
      const id = row.id || this.ids
      getAddress_house(id).then(response => {
        this.form = response.data;
        this.look = true;
        this.title = "地址房屋二维码";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAddress_house(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.isShow = false;
              this.getList();
            });
          } else {
            addAddress_house(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.isShow = false;
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    getMoreQRCode() {
      updateAddress_houseTwo(this.number).then(response => {
        this.msgSuccess("生成成功");
        this.watch = false;
        this.number = null;
        this.getList();
      });
    },
    /** 提交按钮 */
    submitFormOne() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.auditState != "3") {
            if (this.form.id != null) {
              updateAddress_houseOne(this.form).then(response => {
                this.msgSuccess("审核成功");
                this.isShow = false;
                this.open = false;
                this.getList();
              });
            }
          } else {
            this.msgSuccess("该数据已审核过");
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除addressHouse编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delAddress_house(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有地址房屋数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportAddress_house(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    // 得到四级联查省级列表
    getProvince(row) {
      this.queryParamOnes.parentCode = 0;
      listRegion(this.queryParamOnes).then(response => {
        this.provinceData = response.data;
      });
      this.queryParams.parentCode = null;
    },
    // 得到四级联查市级列表
    chooseProvince() {
      this.queryParamOnes.parentCode = this.form.adcode;
      listRegion(this.queryParamOnes).then(response => {
        this.cityData = response.data;
      });
      this.queryParams.parentCode = null;
    },
    // 得到四级联查乡镇/街道列表
    chooseCity() {
      this.queryParamOnes.parentCode = this.form.cityCode;
      listRegion(this.queryParamOnes).then(response => {
        this.countyData = response.data;
      });
      this.queryParams.parentCode = null;
    },
    //得到四级联查乡镇(街道)级
    chooseCounty() {
      this.queryParamOnes.parentCode = this.form.townCode;
      listRegion(this.queryParamOnes).then(response => {
        this.townData = response.data;
      });
      this.queryParams.parentCode = null;
    },
  }
};
</script>
<style>

.table_cell .cell {
  white-space: nowrap;
}
#maps{
  width: 100%;
  min-width: 500px;
  height: 100%;
  min-height: 500px;
  background-color: blue;
}
</style>
