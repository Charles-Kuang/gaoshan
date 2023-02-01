<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="街路巷" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入街路巷"
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
          v-hasPermi="['core:street:add']"
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
          v-hasPermi="['core:street:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-view"
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
          v-hasPermi="['core:street:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="streetList"
      row-key="streetId"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column type="selection" width="55" align="right" eader-align="left"/>
      <el-table-column label="街路巷" align="center" prop="name" :show-overflow-tooltip="true" />
      <el-table-column label="所属行政区划名称" align="center" prop="region" :show-overflow-tooltip="true" />
      <el-table-column label="所属乡镇街道名称" align="center" prop="towns" :show-overflow-tooltip="true" />
      <el-table-column label="所属居(村)委会名称" align="center" prop="committee" :show-overflow-tooltip="true" />
      <el-table-column label="所属公安机关" align="center" prop="dept.deptName" :show-overflow-tooltip="true" />
      <el-table-column label="拼音首字母" align="center" prop="firstLetter" :show-overflow-tooltip="true" />
      <el-table-column label="别名" align="center" prop="alias" :show-overflow-tooltip="true" />
      <el-table-column label="中心经度" align="center" prop="lng" :show-overflow-tooltip="true" />
      <el-table-column label="中心纬度" align="center" prop="lat" :show-overflow-tooltip="true" />
      <el-table-column label="地址围栏" align="center" prop="addressFence" :show-overflow-tooltip="true" />
      <el-table-column label="数据来源" align="center" prop="dataSources" :formatter="dataSourcesFormat"/>
      <el-table-column label="是否入库" align="center" prop="inStorage" :formatter="inStorageFormat"/>
      <el-table-column label="创建人" align="center" prop="createUser" :show-overflow-tooltip="true" />
      <el-table-column label="入库人" align="center" prop="storageUser" :show-overflow-tooltip="true" />
      <el-table-column label="最后更新人" align="center" prop="lastUpdateUser" :show-overflow-tooltip="true" />
      <el-table-column label="审核状态" align="center" prop="auditState" :formatter="auditStateFormat" :show-overflow-tooltip="true" />
      <el-table-column label="审核人" align="center" prop="auditUser" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" width="180" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['core:street:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleAudit(scope.row)"
          >审核
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['core:street:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改街路巷管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="街路巷" prop="name" label-width="105px">
              <el-input v-model="form.name" placeholder="请输入街路巷" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="行政区" prop="regionCode" label-width="105px">
              <el-cascader
                :options="regionOptions"
                v-model="form.regionCode"
                :props="{checkStrictly: false,emitPath: false}"
                placeholder="请选择行政区"
                :disabled="isShow"
                @change="chooseCity"></el-cascader>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="乡镇街道" prop="townsCode" label-width="105px">
              <el-select v-model="form.townsCode" placeholder="请输入街道" :disabled="isShow" @change="chooseCounty">
                <el-option
                  v-for="county in countyData"
                  :key="county.code"
                  :label="county.name"
                  :value="county.code"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="居(村)委会" prop="committeeCode" label-width="105px">
              <el-select v-model="form.committeeCode" placeholder="请输入居(村)委会编码" :disabled="isShow">
                <el-option
                  v-for="town in townData"
                  :key="town.code"
                  :label="town.name"
                  :value="town.code"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公安机关" prop="policeId" label-width="105px">
              <el-cascader
                :options="policeOptions"
                v-model="form.policeId"
                :props="{checkStrictly: true,emitPath: false,value: 'id'}"
                placeholder="请选择所属公安机关"
                :disabled="isShow">
              </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址围栏" prop="addressFence" label-width="105px">
              <el-input v-model="form.addressFence" placeholder="请输入地址围栏" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="父街路巷" prop="parentId" label-width="105px">
              <treeselect v-model="form.parentId" :options="streetOptions" :normalizer="normalizer"
                          placeholder="请选择父街路巷" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="别名" prop="alias" label-width="105px">
              <el-input v-model="form.alias" placeholder="请输入别名" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否入库" prop="inStorage" label-width="105px">
              <el-radio-group v-model="form.inStorage" :disabled="isShow">
                <el-radio
                  v-for="dict in inStorageOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拼音首字母" prop="firstLetter" v-show="look" label-width="105px">
              <el-input v-model="form.firstLetter" placeholder="请输入拼音首字母" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="中心经度" prop="lng" v-show="look" label-width="105px">
              <el-input v-model="form.lng" placeholder="请输入中心经度" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="中心纬度" prop="lat" v-show="look" label-width="105px">
              <el-input v-model="form.lat" placeholder="请输入中心纬度" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="创建人" v-show="isShow" prop="createUser" label-width="110px">
              <el-input v-model="form.createUser" placeholder="请输入创建人名称" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入库人" v-show="isShow" prop="storageUser" label-width="110px">
              <el-input v-model="form.storageUser" placeholder="请输入入库人" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="最后更新人" v-show="isShow" prop="lastUpdateUser" label-width="110px">
              <el-input v-model="form.lastUpdateUser" placeholder="请输入最后更新人" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审核状态" prop="auditState" v-show="isShow" label-width="105px">
              <el-radio-group v-model="form.auditState">
                <el-radio
                  v-for="dict in auditStateOptionsOne"
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
  </div>
</template>

<script>
import {listStreet, getStreet, delStreet, addStreet, updateStreet, exportStreet, auditStreet} from "@/api/core/street";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listRegion, listRegionOne} from "@/api/applet/region";
import {treeselect} from "@/api/system/dept";

export default {
  name: "Street",
  components: {
    Treeselect
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
      // 街路巷管理表格数据
      streetList: [],
      // 街路巷管理树选项
      streetOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      isShow: false,
      // 是否显示弹出层
      look: false,
      // 作废标识字典
      invalidFlagOptions: [],
      // 是否入库字典
      inStorageOptions: [],
      // 审核状态字典
      auditStateOptions: [],
      // 审核状态字典
      auditStateOptionsOne: [],
      // 行政区树选项
      regionOptions: [],
      // 行政区树选项
      policeOptions: [],
      //数据来源
      dataSourcesOptions: [],
      // 查询参数
      queryParams: {
        name: null,
        regionCode: null,
        region: null,
        townsCode: null,
        towns: null,
        committeeCode: null,
        committee: null,
        policeId: null,
        firstLetter: null,
        alias: null,
        lng: null,
        lat: null,
        addressFence: null,
        dataSources: null,
        parentId: null,
        ancestors: null,
        invalidFlag: null,
        inStorage: null,
        storageUser: null,
        storageUserId: null,
        storageTime: null,
        createUnit: null,
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
        auditUserPhone: null
      },
      queryParamOnes: {
        parentCode: null,
        code: null,
        name: null,
        type: null
      },
      queryParamTwos: {
        forefathers: "0,520000",
        level: 2
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      countyData: [],
      townData: [],
    };
  },
  created() {
    this.getList();
    this.getTreeselectOne();
    this.handleRegionOptionOnes();
    this.getDicts("sys_normal_disable").then(response => {
      this.invalidFlagOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.inStorageOptions = response.data;
    });
    this.getDicts("gs_commit_status").then(response => {
      this.auditStateOptions = response.data;
      let auditStateOptions = this.auditStateOptions
      auditStateOptions.forEach(function (val, index) {
        val.dictValue = Number(val.dictValue)
      })
      this.auditStateOptions = auditStateOptions
    });
    this.getDicts("gs_data_sources").then(response => {
      this.dataSourcesOptions = response.data;
    });
  },
  methods: {
    /** 查询街路巷管理列表 */
    getList() {
      this.loading = true;
      listStreet(this.queryParams).then(response => {
        this.streetList = this.handleTree(response.data, "streetId", "parentId");
        this.loading = false;
      });
    },
    /** 转换街路巷管理数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.streetId,
        label: node.name,
        children: node.children
      };
    },
    /** 查询行政机关下拉树结构 */
    getTreeselect() {
      listStreet().then(response => {
        this.streetOptions = [];
        const data = {streetId: 0, name: '顶级节点', children: []};
        data.children = this.handleTree(response.data, "streetId", "parentId");
        this.streetOptions.push(data);
      });
    },
    /** 查询行政机关下拉树结构 */
    getTreeselectOne() {
      treeselect().then(response => {
        this.policeOptions = response.data;
      });
    },
    // 作废标识字典翻译
    invalidFlagFormat(row, column) {
      return this.selectDictLabel(this.invalidFlagOptions, row.invalidFlag);
    },
    // 是否入库字典翻译
    inStorageFormat(row, column) {
      return this.selectDictLabel(this.inStorageOptions, row.inStorage);
    },
    dataSourcesFormat(row, column) {
      return this.selectDictLabel(this.dataSourcesOptions, row.dataSources);
    },
    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        streetId: null,
        name: null,
        regionCode: null,
        region: null,
        townsCode: null,
        towns: null,
        committeeCode: null,
        committee: null,
        policeId: null,
        firstLetter: null,
        alias: null,
        lng: null,
        lat: null,
        addressFence: null,
        dataSources: "0",
        parentId: null,
        ancestors: null,
        invalidFlag: "0",
        inStorage: "0",
        storageUser: null,
        storageUserId: null,
        storageTime: null,
        createUnit: null,
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
        auditState: "0",
        auditUserPhone: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      this.open = true;
      this.isShow = false;
      this.look = false;
      this.title = "添加街路巷管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.streetId;
      }
      getStreet(row.streetId).then(response => {
        this.form = response.data;
        this.handleRegionOptions();
        this.open = true;
        this.isShow = false;
        this.look = true;
        this.title = "修改街路巷管理";
      });
    },
    /** 审核按钮操作 */
    handleAudit(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.streetId;
      }
      getStreet(row.streetId).then(response => {
        this.form = response.data;
        this.handleRegionOptions();
        this.open = true;
        this.isShow = true;
        this.look = true;
        this.auditStateOptionsOne=this.auditStateOptions.slice(1);
        this.title = "修改街路巷管理";
      });
    },
    handleRegionOptionOnes() {
      listRegionOne(this.queryParamTwos).then(response => {
        let regionOptions = response.data;
        regionOptions.forEach(function (val, index) {
          val.value = val.code
          val.label = val.name
        })
        this.regionOptions = this.getTreeData(this.handleTree(regionOptions, "code", "parentCode"));
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
    handleRegionOptions() {
      this.chooseCity()
      if(this.form.townsCode!=null){
        this.chooseCounty()
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.streetId != null) {
            updateStreet(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStreet(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
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
          if (this.form.streetId != null) {
            auditStreet(this.form).then(response => {
              this.msgSuccess("审核成功");
              this.isShow = false;
              this.open = false;
              this.look = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除街路巷管理编号为"' + row.streetId + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delStreet(row.streetId);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },

    // 得到四级联查乡镇/街道列表
    chooseCity() {
      this.queryParamOnes.parentCode = this.form.regionCode;
      listRegion(this.queryParamOnes).then(response => {
        this.countyData = response.data;
      });
      this.queryParams.parentCode = null;
    },
    //得到四级联查乡镇(街道)级
    chooseCounty() {
      this.queryParamOnes.parentCode = this.form.townsCode;
      listRegion(this.queryParamOnes).then(response => {
        this.townData = response.data;
      });
      this.queryParams.parentCode = null;
    },
  }
};
</script>
