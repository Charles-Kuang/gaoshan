<template>
  <div class="app-container">
    <el-row :gutter="20">

      <!--省市县数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="cityName"
            placeholder="请输入省市县名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            v-loading="load"
            :data="cityOptions"
            node-key="cityCode"
            :props="defaultProps"
            lazy
            :load="loadSearch"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>

      <!--用户数据-->
      <el-col :span="20" :xs="24">

        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="行政编码" prop="code">
            <el-input
              v-model="queryParams.code"
              placeholder="请输入行政区编码"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="行政区名称" prop="name"  label-width="110px">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入行政区简称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="审核状态" prop="auditState">
            <el-select v-model="queryParams.auditState" placeholder="请选择审核状态">
              <el-option
                v-for="dict in auditStateOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="parseInt(dict.dictValue)"
              ></el-option>
            </el-select>
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
              v-hasPermi="['applet:region:add']"
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
              v-hasPermi="['applet:region:edit']"
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
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['applet:region:remove']"
            >删除
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="regionList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="行政区划代码" align="left" prop="code" header-align="center" :show-overflow-tooltip="true"/>
          <el-table-column label="行政区划简称" align="center" prop="name" :show-overflow-tooltip="true"/>
          <el-table-column label="行政区划全称" align="center" width="240" prop="fullName" :show-overflow-tooltip="true"/>
          <el-table-column label="拼音首字母" align="center" prop="firstLetter" :show-overflow-tooltip="true"/>
          <el-table-column label="所属乡镇/街道" align="center" width="100" prop="parentName" :show-overflow-tooltip="true"/>
          <el-table-column label="中心经度" align="center" prop="lng" :show-overflow-tooltip="true"/>
          <el-table-column label="中心纬度" align="center" prop="lat" :show-overflow-tooltip="true"/>
          <el-table-column label="所属公安机关" align="center" prop="policeName" :show-overflow-tooltip="true"/>
          <el-table-column label="城乡分类" align="center" prop="type" :formatter="typeFormat"/>
          <el-table-column label="数据状态" align="center" key="invalidFlag">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.invalidFlag"
                active-value="0"
                inactive-value="1"
                @change="handleInvalidFlagChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="创建人" align="center" prop="createUser"/>
          <el-table-column label="提交状态" align="center" prop="commitStatus" :formatter="commitStatusFormat"/>
          <el-table-column label="最后更新人" align="center" prop="lastUpdateUser"/>
          <el-table-column label="审核状态" align="center" prop="auditState" :formatter="auditStateFormat"/>
          <el-table-column label="审核人" align="center" prop="auditUser"/>
          <el-table-column
            label="操作"
            align="center"
            width="180"
            fixed="right"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
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
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['applet:region:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['applet:region:remove']"
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

      </el-col>

      <!-- 添加或修改行政区对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="510px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="所属乡镇/街道" prop="parentCode" label-width="110px" v-show="!isHide&&!isView">
            <el-cascader
              :options="regionOptions"
              v-model="form.parentCode"
              :props="{checkStrictly: false,emitPath: false}"
              placeholder="请选择乡镇/街道"
              :disabled="isShow">
            </el-cascader>&nbsp;
            <el-button
              type="primary"
              plain
              @click="handleAddOne"
              v-hasPermi="['applet:region:add']"
              :disabled="isShow"
              v-show="!isHide&&!isView"
            >新增乡镇/街道
            </el-button>
          </el-form-item>
          <el-form-item label="所属县/区" prop="parentCode"  label-width="110px" v-show="isHide">
            <el-select v-model="form.parentCode" placeholder="请选择所属县/区" :disabled="isShow">
              <el-option
                v-for="dict in regionOptionOnes"
                :key="dict.code"
                :label="dict.name"
                :value="dict.code"
              ></el-option>
            </el-select>
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              @click="handleAddTwo"
              v-hasPermi="['applet:region:add']"
              :disabled="isShow"
              style="margin-left: 7px;"
              v-show="isHide"
            >新增县/区
            </el-button>
          </el-form-item>
          <el-form-item label="市级行政区" prop="parentCode"  label-width="110px" v-show="isView">
            <el-select v-model="form.parentCode" placeholder="请选择所属市级行政区" :disabled="isShow">
              <el-option
                v-for="dict in regionOptionTwos"
                :key="dict.code"
                :label="dict.name"
                :value="dict.code"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="行政编码" prop="code"  label-width="110px">
            <el-input v-model="form.code" placeholder="请输入行政区编码" :disabled="isShow"/>
          </el-form-item>
          <el-form-item label="行政区名称" prop="name"  label-width="110px">
            <el-input v-model="form.name" placeholder="请输入行政区简称" :disabled="isShow"/>
          </el-form-item>
          <el-form-item label="所属公安机关" prop="police"  label-width="110px">
            <el-cascader
              :options="policeOptions"
              v-model="form.police"
              :props="{checkStrictly: true,emitPath: false,value: 'id'}"
              placeholder="请选择所属公安机关"
              :disabled="isShow">
            </el-cascader>
          </el-form-item>
          <el-form-item label="城乡分类" prop="type"  label-width="110px" v-show="!isHide&&!isView">
            <el-select v-model="form.type" placeholder="请选择城乡分类" :disabled="isShow">
              <el-option
                v-for="dict in typeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="中心经度" prop="lng" v-show="look"  label-width="110px">
            <el-input v-model="form.lng" placeholder="请输入中心经度" :disabled="isShow"/>
          </el-form-item>
          <el-form-item label="中心纬度" prop="lat" v-show="look"  label-width="110px">
            <el-input v-model="form.lat" placeholder="请输入中心纬度" :disabled="isShow"/>
          </el-form-item>
          <el-form-item label="拼音首字母" prop="firstLetter" v-show="look"  label-width="110px">
            <el-input v-model="form.firstLetter" placeholder="请输入拼音首字母" :disabled="isShow"/>
          </el-form-item>
          <el-form-item label="数据状态" label-width="110px">
            <el-radio-group v-model="form.invalidFlag">
              <el-radio
                v-for="dict in invalidFlagOptions"
                :key="dict.dictValue"
                :label="dict.dictValue"
                :disabled="isShow"
              >{{dict.dictLabel}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <!--          <el-form-item label="创建人编号" v-show="isShow" prop="createUserId"  label-width="110px">-->
          <!--            <el-input v-model="form.createUserId" placeholder="请输入创建人编号" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <el-form-item label="创建人名称" v-show="isShow" prop="createUser"  label-width="110px">
            <el-input v-model="form.createUser" placeholder="请输入创建用户名称" :disabled="true"/>
          </el-form-item>
          <!--          <el-form-item label="创建用户联系电话" v-show="isShow" prop="createUserPhone"  label-width="110px">-->
          <!--            <el-input v-model="form.createUserPhone" placeholder="请输入创建用户联系电话" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="最后更新时间" v-show="isShow" prop="lastUpdateTime"  label-width="110px">-->
          <!--            <el-date-picker clearable size="small"-->
          <!--                            v-model="form.lastUpdateTime"-->
          <!--                            type="date"-->
          <!--                            :disabled="true"-->
          <!--                            value-format="yyyy-MM-dd"-->
          <!--                            placeholder="选择最后更新时间">-->
          <!--            </el-date-picker>-->
          <!--          </el-form-item>-->
          <el-form-item label="最后更新人" v-show="isShow" prop="lastUpdateUser"  label-width="110px">
            <el-input v-model="form.lastUpdateUser" placeholder="请输入最后更新人" :disabled="true"/>
          </el-form-item>
          <!--          <el-form-item label="最后更新人编号" v-show="isShow" prop="lastUpdateUserId"  label-width="110px">-->
          <!--            <el-input v-model="form.lastUpdateUserId" placeholder="请输入最后更新人编号" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="最后更新人联系电话" v-show="isShow" prop="lastUpdateUserPhone"  label-width="110px">-->
          <!--            <el-input v-model="form.lastUpdateUserPhone" placeholder="请输入最后更新人联系电话" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="审核人" v-show="isShow" prop="auditUser"  label-width="110px">-->
          <!--            <el-input v-model="form.auditUser" placeholder="请输入审核人" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="审核人编号" v-show="isShow" prop="auditUserId"  label-width="110px">-->
          <!--            <el-input v-model="form.auditUserId" placeholder="请输入审核人编号" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="审核状态" prop="auditState"  label-width="110px">-->
          <!--            <el-select v-model="form.auditState" placeholder="请选择审核状态" :disabled="true">-->
          <!--              <el-option-->
          <!--                v-for="dict in auditStateOptions"-->
          <!--                :key="dict.dictValue"-->
          <!--                :label="dict.dictLabel"-->
          <!--                :value="parseInt(dict.dictValue)"-->
          <!--              ></el-option>-->
          <!--            </el-select>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="审核人电话" v-show="isShow" prop="auditUserPhone"  label-width="110px">-->
          <!--            <el-input v-model="form.auditUserPhone" placeholder="请输入审核人电话" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <el-form-item label="审核" v-show="isShow"  label-width="110px">
            <el-radio-group v-model="form.commitStatus">
              <el-radio
                v-for="dict in commitStatusOptionsOne"
                :key="dict.dictValue"
                :label="dict.dictValue"
              >{{ dict.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" v-show="isShow" @click="submitFormOne">确 定</el-button>
          <el-button type="primary" v-show="!isShow" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

    </el-row>
  </div>
</template>

<script>
import {
  getRegion,
  delRegion,
  addRegion,
  updateRegion,
  updateRegionOne,
  cityTree, listRegionTwo, getChildOne, listGsRegion, listRegionOne, changeInvalidFlag
} from "@/api/applet/region";
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {treeselect} from "@/api/system/dept";

export default {
  name: "County",
  components: {
    TreeSelect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 遮罩层
      load: true,
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
      // 行政区表格数据
      regionList: [],
      // 行政区树选项
      regionOptions: [],
      // 行政区树选项
      regionOptionOnes: [],
      // 行政区树选项
      regionOptionTwos: [],
      // 所属行政区树选项
      policeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      isShow: false,
      isHide: false,
      isView: false,
      // 是否显示弹出层
      look: false,
      // 详址包含乡镇街道字典
      includeTownOptions: [],
      // 类型(1村委会 2居委会)字典
      typeOptions: [],
      // 详址包含社区、村(居)委会字典
      committeeOptions: [],
      // 审核状态字典
      auditStateOptions: [],
      // 提交状态字典
      commitStatusOptions: [],
      // 提交状态字典
      commitStatusOptionsOne: [],
      // 作废标识字典
      invalidFlagOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parentCode: null,
        parentName: null,
        forefathers: "0,520000",
        code: null,
        name: null,
        auditState: null,
        level: 4
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "行政区名称不能为空", trigger: "blur"}
        ],
        code: [
          {required: true, message: "行政编码不能为空", trigger: "blur"}
        ],
        police: [
          {required: true, message: "所属行政机关不能为空", trigger: "blur"}
        ],
      },
      maps: new Map(),
      cityOptions: undefined,
      cityName: undefined,
      defaultProps: {
        children: "children",
        label: "cityName",
        isLeaf: "!hasChild",
      },
      // 查询参数
      queryParamOnes: {
        name: null,
        forefathers: "0,520000",
        level: 3
      },
      // 查询参数
      queryParamTwos: {
        level: 2,
        forefathers: "0,520000",
      }
    }
  },
  watch: {
    // 根据名称筛选行政机关树
    cityName(val) {
      this.defaultProps.isLeaf = "!hasChild"
      if (val == null || val === "" || val === " ") {
        cityTree().then(response => {
          this.cityOptions = response.data;
        });
      } else {
        this.queryParamOnes.name = val
        listGsRegion(this.queryParamOnes).then(response => {
          let cityOptions = []
          for (const rowElement of response.data) {
            if (rowElement.cityCode.toString().length > 8) {
              this.defaultProps.isLeaf = "hasChild"
            }
            cityOptions.push(rowElement)
          }
          this.cityOptions = cityOptions;
        });
      }
    }
  },
  created() {
    this.getList();
    this.getCityTree();
    this.handleRegionOptions();
    this.getTreeselect();
    this.getDicts("gs_address_element_type").then(response => {
      this.addressTypeOptions = response.data;
    });
    this.getDicts("gs_type_of_residence").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.committeeOptions = response.data;
      this.includeTownOptions = response.data;
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
    this.getDicts("sys_normal_disable").then(response => {
      this.invalidFlagOptions = response.data;
    });
  },
  methods: {
    /** 查询行政区列表 */
    getList() {
      this.loading = true;
      listRegionTwo(this.queryParams).then(response => {
        this.regionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询下拉树结构 */
    getCityTree() {
      cityTree().then(response => {
        this.cityOptions = response.data;
        this.load = false
      });
    },
    /** 转换行政区数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        code: node.code,
        label: node.name,
        children: node.children
      };
    },
    // 详址包含乡镇街道字典翻译
    includeTownFormat(row, column) {
      return this.selectDictLabel(this.includeTownOptions, row.includeTown);
    },
    // 类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 详址包含社区、村(居)委会字典翻译
    committeeFormat(row, column) {
      return this.selectDictLabel(this.committeeOptions, row.committee);
    },    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState);
    },
    // 提交状态字典翻译
    commitStatusFormat(row, column) {
      return this.selectDictLabel(this.commitStatusOptions, row.commitStatus);
    },
    // 作废标识字典翻译
    invalidFlagFormat(row, column) {
      return this.selectDictLabel(this.invalidFlagOptions, row.invalidFlag);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.isHide = false;
      this.isView = false;
      this.isShow = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        regionId: null,
        parentCode: 0,
        parentName: null,
        forefathers: null,
        police: null,
        code: null,
        name: null,
        fullName: null,
        firstLetter: null,
        lng: null,
        lat: null,
        geographicGrid: null,
        geographicEnity: null,
        type: null,
        hasChild: null,
        level: 4,
        invalidFlag: "0",
        createTime: null,
        createUserId: null,
        createUser: null,
        createUserPhone: null,
        lastUpdateTime: null,
        lastUpdateUser: null,
        lastUpdateUserId: null,
        lastUpdateUserPhone: null,
        auditUser: null,
        auditUserId: null,
        auditState: null,
        auditUserPhone: null,
        commitStatus: "0"
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.page = 1;
      this.queryParams.forefathers = "0,520000";
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 审核按钮操作 */
    handleAudit(row) {
      this.reset();
      const ids = row.code || this.ids
      getRegion(ids).then(response => {
        this.form = response.data;
        this.open = true;
        this.isHide = false;
        this.isView = false;
        this.isShow = true;
        this.look = true;
        this.title = "审核村(居)委会";
        this.commitStatusOptionsOne = this.commitStatusOptions.slice(1);
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.code)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.isHide = false;
      this.isView = false;
      this.open = true;
      this.look = false;
      this.title = "添加行政区";
    },
    /** 新增按钮操作 */
    handleAddOne() {
      this.reset();
      this.handleRegionOptionOnes();
      this.look = false;
      this.open = true;
      this.isHide = true;
      this.isView = false;
      this.title = "添加乡镇/街道";
    },
    handleRegionOptionOnes() {
      listRegionTwo(this.queryParamTwos).then(response => {
        this.regionOptionOnes = response.rows;
      })
    },
    /** 新增按钮操作 */
    handleAddTwo() {
      this.reset();
      this.handleRegionOptionTwos();
      this.look = false;
      this.open = true;
      this.isHide = false;
      this.isView = true;
      this.title = "添加县/区";
    },
    handleRegionOptionTwos() {
      this.queryParamTwos.level = 1
      listRegionTwo(this.queryParamTwos).then(response => {
        this.regionOptionTwos = response.rows;
        this.queryParamTwos.level = 2
      })
    },
    /** 查询行政机关下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.policeOptions = response.data;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const ids = row.code || this.ids
      getRegion(ids).then(response => {
        this.form = response.data;
        this.queryParams.forefathers = "0,520000";
        this.isHide = false;
        this.isView = false;
        this.open = true;
        this.isShow = false;
        this.look = true;
        this.title = "修改行政区";
      });
    },
    handleRegionOptions() {
      this.queryParamTwos.level = 3
      listRegionOne(this.queryParamTwos).then(response => {
        let regionOptions = response.data;
        regionOptions.forEach(function (val, index) {
          val.value = val.code
          val.label = val.name
        })
        this.regionOptions = this.getTreeData(this.handleTree(regionOptions, "code", "parentCode"));
        this.queryParamTwos.level = 2
      })
    },
    getTreeData(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].children.length < 1) {
          // child若为空数组，则将child设为undefined
          data[i].children = undefined;
        } else {
          // child若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].children);
        }
      }
      return data;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.regionId != null) {
            updateRegion(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.isHide = false;
              this.isView = false;
              this.open = false;
              this.queryParams.forefathers = "0,520000";
              this.getList();
            });
          } else {
            addRegion(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.isHide = false;
              this.isView = false;
              this.open = false;
              this.queryParams.forefathers = "0,520000";
              this.getList();
            });
          }
        }
      });
    },
    /** 提交按钮 */
    submitFormOne() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.auditState != "3") {
            updateRegionOne(this.form).then(response => {
                this.msgSuccess("审核成功");
                this.isHide = false;
                this.isView = false;
                this.isShow = false;
                this.open = false;
                this.queryParams.forefathers = "0,520000";
                this.getList();
              }
            )
          } else {
            this.msgSuccess("该数据已审核过");
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除行政区编号为"' + row.regionId + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delRegion(row.regionId);
      }).then(() => {
        this.queryParams.forefathers = "0,520000";
        this.getList();
        this.msgSuccess("删除成功");
      })
    },

    changeColor({row}) {
      if (row.level === 0) {
        return 'row';
      } else if (row.level === 1) {
        return 'success-one';
      } else if (row.level === 2) {
        return 'success-two';
      } else if (row.level === 3) {
        return 'success-three';
      } else if (row.level === 4) {
        return 'success-four';
      }
      return 'row';
    },

    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.parentCode = null;
      this.queryParams.forefathers = "0,520000";
      if (data.cityCode.toString().length > 6) {
        this.queryParams.parentCode = data.cityCode;
      } else {
        if (data.cityCode !== 520000 && !data.cityCode.toString().endsWith("00")) {
          this.queryParams.forefathers = this.queryParams.forefathers + "," + data.cityCode.toString().substring(0, 4) + "00" + "," + data.cityCode;

        } else {
          if (data.cityCode !== 520000) {
            this.queryParams.forefathers = this.queryParams.forefathers + "," + data.cityCode;
          }
        }
      }
      this.getList();
    },

    async loadSearch(row, resolve) {
      let loadData = [];
      getChildOne(row.data.cityCode).then(response => {
        for (const rowElement of response.data) {
          if (rowElement.cityCode.toString().length >= 9) {
            this.defaultProps.isLeaf = "hasChild"
          }
          loadData.push(rowElement)
        }
      });
      setTimeout(() => {
        resolve(loadData)
      }, 400)
      await this.setNewRowData(row, loadData)
    },
    setNewRowData(row, loadData) {
      row.children = loadData
    },
    setChildren(children, type) {
      // 编辑多个子层级
      children.map(j => {
        this.toggleSelection(j, type)
        if (j.children) {
          this.setChildren(j.children, type)
        }
      })
    },
    // 用户状态修改
    handleInvalidFlagChange(row) {
      let text = row.invalidFlag === "0" ? "启用" : "注销";
      if(text!=="启用"){
        this.$confirm('确认要"' + text + '""' + row.name + '"区域吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return changeInvalidFlag(row.regionId, row.invalidFlag);
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function() {
          row.invalidFlag = row.invalidFlag === "0" ? "1" : "0";
        });
      }else {
        this.msgInfo("需要权限才能启用喔");
      }
    },
  },
};
</script>
<style>

.table_cell .cell {
  white-space: nowrap;
}

.flex-item {
  color: tomato;
}

.el-table .row {
  background: #ffffff;
}

.el-table .success-one {
  background: rgba(253, 245, 230, 0.8);
}

.el-table .success-two {
  background: rgba(250, 175, 129, 0.4);
}

.el-table .success-three {
  background: rgba(255, 126, 71, 0.4);
}

.el-table .success-four {
  background: rgba(255, 99, 71, 0.4);
}

</style>

