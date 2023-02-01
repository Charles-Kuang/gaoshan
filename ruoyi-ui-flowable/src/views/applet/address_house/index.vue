<template>
  <div class="app-container">
    <el-button type="primary"  @click="mapModel">地图模式</el-button>
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['applet:address_house:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['applet:address_house:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-s-check"
          size="mini"
          :disabled="single"
          @click="handleAudit"
        >审核
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-video-play"
          size="mini"
          @click="handleQRCode"
        >生成二维码
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-view"
          size="mini"
          :disabled="single"
          @click="handleShowQR"
        >查看二维码
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['applet:address_house:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="address_houseList" cell-class-name="table_cell"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="二维码" align="center" prop="qrCode" show-overflow-tooltip/>
      <el-table-column label="全地址" align="center" prop="address" show-overflow-tooltip/>
      <el-table-column label="地址门头照" align="center" prop="doorPhoto"/>
      <el-table-column label="地址类型" align="center" prop="addressType" :formatter="addressTypeFormat"/>
      <el-table-column label="纬度" align="center" prop="latitude" />
      <el-table-column label="经度" align="center" prop="longitude"/>
      <el-table-column label="行政区划" align="center" prop="adcode"/>
      <el-table-column label="城市" align="center" prop="cityCode"/>
      <el-table-column label="街道" align="center" prop="townCode"/>
      <el-table-column label="国家" align="center" prop="nationCode" :formatter="nationCodeFormat"/>
      <el-table-column label="楼/栋/村民组" align="center" prop="addressGroup"/>
      <el-table-column label="门牌号/房间号" align="center" prop="doorplate"/>
      <el-table-column label="产权类型" align="center" prop="propertyType" :formatter="propertyTypeFormat"/>
      <el-table-column label="产权单位" align="center" prop="propertyUnit"/>
      <el-table-column label="产权人" align="center" prop="propertyPerson"/>
      <el-table-column label="身份识别号" align="center" prop="identityNumber"/>
      <el-table-column label="联系电话" align="center" prop="phoneNumber"/>
      <el-table-column label="房屋类型" align="center" prop="houseType" :formatter="houseTypeFormat"/>
      <el-table-column label="房屋结构" align="center" prop="buildingStructure" :formatter="buildingStructureFormat"/>
      <el-table-column label="房屋面积" align="center" prop="roomArea"/>
      <el-table-column label="房屋间数" align="center" prop="houseNumber"/>
      <el-table-column label="房屋用途" align="center" prop="houseUse" :formatter="houseUseFormat"/>
      <el-table-column label="居住现状" align="center" prop="livingStatus" :formatter="livingStatusFormat"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="创建人" align="center" prop="createUser"/>
      <el-table-column label="更新人" align="center" prop="lastUpdateUser"/>
      <el-table-column label="审核人" align="center" prop="auditUser"/>
      <el-table-column label="提交状态" align="center" prop="commitStatus" :formatter="commitStatusFormat"/>
      <el-table-column label="操作" align="center" width="260" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['applet:address_house:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-check"
            @click="handleAudit(scope.row)"
          >审核
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleShowQR(scope.row)"
          >查看二维码
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['applet:address_house:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改地址房屋对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="国家" prop="nationCode" label-width="105px">
              <el-select v-model="form.nationCode" placeholder="请选择国家" :disabled="isShow" @change="getProvince">
                <el-option
                  v-for="dict in nationCodeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="省份" prop="adcode" label-width="105px">
              <el-select v-model="form.adcode" placeholder="请选择省份" :disabled="isShow" @change="chooseProvince">
                <el-option
                  v-for="province in provinceData"
                  :key="province.code"
                  :label="province.name"
                  :value="province.code.toString()"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="城市" prop="cityCode" label-width="105px">
              <el-select v-model="form.cityCode" placeholder="请选择城市" :disabled="isShow" @change="chooseCity">
                <el-option
                  v-for="city in cityData"
                  :key="city.code"
                  :label="city.name"
                  :value="city.code.toString()"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="街道" prop="townCode" label-width="105px">
              <el-select v-model="form.townCode" placeholder="请输入街道" :disabled="isShow" @change="chooseCounty">
                <el-option
                  v-for="county in countyData"
                  :key="county.code"
                  :label="county.name"
                  :value="county.code.toString()"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="楼/栋/村民组" prop="addressGroup" label-width="105px">
              <el-select v-model="form.addressGroup" placeholder="请输入楼/栋/村民组" :disabled="isShow">
                <el-option
                  v-for="town in townData"
                  :key="town.code"
                  :label="town.name"
                  :value="town.code"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="门牌/房间号" prop="doorplate" label-width="105px">
              <el-input v-model="form.doorplate" placeholder="请输入门牌号/房间号" :disabled="isShow"/>
            </el-form-item>
            <el-form-item label="地址类型" prop="addressType" label-width="105px">
              <el-select v-model="form.addressType" placeholder="请选择地址类型" :disabled="isShow">
                <el-option
                  v-for="dict in addressTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址门头照" label-width="105px">
              <imageUpload v-model="form.doorPhoto" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="产权类型" prop="propertyType" label-width="105px">
              <el-select v-model="form.propertyType" placeholder="请选择产权类型" :disabled="isShow">
                <el-option
                  v-for="dict in propertyTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产权单位" prop="propertyUnit" label-width="105px">
              <el-input v-model="form.propertyUnit" placeholder="请输入产权单位" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="产权人" prop="propertyPerson" label-width="105px">
              <el-input v-model="form.propertyPerson" placeholder="请输入产权人" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份识别号" prop="identityNumber" label-width="105px">
              <el-input v-model="form.identityNumber" placeholder="请输入身份识别号" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phoneNumber" label-width="105px">
              <el-input v-model="form.phoneNumber" placeholder="请输入联系电话" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房屋类型" prop="houseType" label-width="105px">
              <el-select v-model="form.houseType" placeholder="请选择房屋类型" :disabled="isShow">
                <el-option
                  v-for="dict in houseTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="房屋结构" prop="buildingStructure" label-width="105px">
              <el-select v-model="form.buildingStructure" placeholder="请输入房屋结构" :disabled="isShow">
                <el-option
                  v-for="dict in buildingStructureOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房屋面积" prop="roomArea" label-width="105px">
              <el-input v-model="form.roomArea" placeholder="请输入房屋面积" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="房屋间数" prop="houseNumber" label-width="105px">
              <el-input v-model="form.houseNumber" placeholder="请输入房屋间数" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房屋用途" prop="houseUse" label-width="105px">
              <el-select v-model="form.houseUse" placeholder="请输入房屋用途" :disabled="isShow">
                <el-option
                  v-for="dict in houseUseOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="居住现状" prop="livingStatus" label-width="105px">
              <el-select v-model="form.livingStatus" placeholder="请选择居住现状" :disabled="isShow">
                <el-option
                  v-for="dict in livingStatusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="提交状态" v-show="isShow" label-width="105px">
              <el-radio-group v-model="form.commitStatus">
                <el-radio
                  v-for="dict in commitStatusOptionsOne"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-show="isShow" @click="submitFormOne">确 定</el-button>
        <el-button type="primary" v-show="!isShow" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改地址房屋对话框 -->
    <el-dialog :title="title" :visible.sync="look" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules">
        <el-image align="right" :src="form.qrCode"></el-image>
      </el-form>
    </el-dialog>

    <!-- 添加或修改地址房屋对话框 -->
    <el-dialog :title="title" :visible.sync="watch" width="500px" append-to-body>
      <el-form ref="form" :rules="rules">
        <el-form-item label="请输入生成二维码的数量" prop="roomArea">
          <el-input v-model="number" placeholder="请输入数字"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="getMoreQRCode">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

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
  updateAddress_houseOne, updateAddress_houseTwo
} from "@/api/applet/address_house";
import ImageUpload from '@/components/ImageUpload';
import {listRegion} from "@/api/applet/region";

export default {
  name: "Address_house",
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
  created() {
    this.getList();
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
       this.$router.replace({ path: '/mapaddress/address_house/addressHouseDetail',query: {}})
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
      this.open = true;
      this.title = "新增地址房屋";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAddress_house(id).then(response => {
        this.form = response.data;
        this.handleRegionOptions();
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
        this.handleRegionOptions();
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
    handleRegionOptions() {
      if(this.form.nationCode=='0'){
        this.getProvince()
        if(this.form.adcode!==null){
          this.chooseProvince()
          if(this.form.adcode!==null){
            this.chooseCity()
            if(this.form.townsCode!=null){
              this.chooseCounty()
            }
          }
        }
      }
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
      if(this.form.nationCode==='0'){
        this.queryParamOnes.parentCode = 0;
        listRegion(this.queryParamOnes).then(response => {
          this.provinceData = response.data;
        });
        this.queryParams.parentCode = null;
      }
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

</style>
