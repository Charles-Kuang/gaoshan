<template>
  <div class="app-container">
    <el-row :gutter="20">

      <!--省市数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="cityName"
            placeholder="请输入省市名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="cityOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
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
          <el-form-item label="行政区名称" prop="name" label-width="110px">
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
          <el-table-column label="所属市级行政区" align="center" width="100" prop="parentName" :show-overflow-tooltip="true"/>
          <el-table-column label="所属公安机关" align="center" prop="policeName" :show-overflow-tooltip="true"/>
          <el-table-column label="中心经度" align="center" prop="lng" :show-overflow-tooltip="true"/>
          <el-table-column label="中心纬度" align="center" prop="lat" :show-overflow-tooltip="true"/>
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
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="市级行政区" prop="parentCode" label-width="110px">
            <el-select v-model="form.parentCode" placeholder="请选择所属市级行政区" :disabled="isShow">
              <el-option
                v-for="dict in regionOptions"
                :key="dict.code"
                :label="dict.name"
                :value="dict.code"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="行政编码" prop="code" label-width="110px">
            <el-input v-model="form.code" placeholder="请输入行政区编码" :disabled="isShow"/>
          </el-form-item>
          <el-form-item label="行政区名称" prop="name" label-width="110px">
            <el-input v-model="form.name" placeholder="请输入行政区简称" :disabled="isShow"/>
          </el-form-item>
          <el-form-item label="所属公安机关" prop="police" label-width="110px">
            <el-cascader
              :options="policeOptions"
              v-model="form.police"
              :props="{checkStrictly: true,emitPath: false,value: 'id'}"
              placeholder="请选择所属公安机关"
              :disabled="isShow">
            </el-cascader>
          </el-form-item>
          <el-form-item label="中心经度" prop="lng" v-show="look" label-width="110px">
            <el-input v-model="form.lng" placeholder="请输入中心经度" :disabled="isShow"/>
          </el-form-item>
          <el-form-item label="中心纬度" prop="lat" v-show="look" label-width="110px">
            <el-input v-model="form.lat" placeholder="请输入中心纬度" :disabled="isShow"/>
          </el-form-item>
          <el-form-item label="拼音首字母" prop="firstLetter" v-show="look" label-width="110px">
            <el-input v-model="form.firstLetter" placeholder="请输入拼音首字母" :disabled="isShow"/>
          </el-form-item>
          <!--          <el-form-item label="创建人编号" v-show="isShow" prop="createUserId" label-width="110px">-->
          <!--            <el-input v-model="form.createUserId" placeholder="请输入创建人编号" :disabled="true"/>-->
          <!--          </el-form-item>-->
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
          <el-form-item label="创建人名称" v-show="isShow" prop="createUser" label-width="110px">
            <el-input v-model="form.createUser" placeholder="请输入创建人名称" :disabled="true"/>
          </el-form-item>
          <!--          <el-form-item label="创建人联系电话" v-show="isShow" prop="createUserPhone" label-width="110px">-->
          <!--            <el-input v-model="form.createUserPhone" placeholder="请输入创建人联系电话" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="最后更新时间" v-show="isShow" prop="lastUpdateTime" label-width="110px">-->
          <!--            <el-date-picker clearable size="small"-->
          <!--                            v-model="form.lastUpdateTime"-->
          <!--                            type="date"-->
          <!--                            :disabled="true"-->
          <!--                            value-format="yyyy-MM-dd"-->
          <!--                            placeholder="选择最后更新时间">-->
          <!--            </el-date-picker>-->
          <!--          </el-form-item>-->
          <el-form-item label="最后更新人" v-show="isShow" prop="lastUpdateUser" label-width="110px">
            <el-input v-model="form.lastUpdateUser" placeholder="请输入最后更新人" :disabled="true"/>
          </el-form-item>
          <!--          <el-form-item label="最后更新人编号" v-show="isShow" prop="lastUpdateUserId" label-width="110px">-->
          <!--            <el-input v-model="form.lastUpdateUserId" placeholder="请输入最后更新人编号" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="最后更新人联系电话" v-show="isShow" prop="lastUpdateUserPhone" label-width="110px">-->
          <!--            <el-input v-model="form.lastUpdateUserPhone" placeholder="请输入最后更新人联系电话" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="审核人姓名" v-show="isShow" prop="auditUser" label-width="110px">-->
          <!--            <el-input v-model="form.auditUser" placeholder="请输入审核人" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="审核人编号" v-show="isShow" prop="auditUserId" label-width="110px">-->
          <!--            <el-input v-model="form.auditUserId" placeholder="请输入审核人编号" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="审核状态" prop="auditState" label-width="110px">-->
          <!--            <el-select v-model="form.auditState" placeholder="请选择审核状态" :disabled="true">-->
          <!--              <el-option-->
          <!--                v-for="dict in auditStateOptions"-->
          <!--                :key="dict.dictValue"-->
          <!--                :label="dict.dictLabel"-->
          <!--                :value="parseInt(dict.dictValue)"-->
          <!--              ></el-option>-->
          <!--            </el-select>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="审核人电话" v-show="isShow" prop="auditUserPhone" label-width="110px">-->
          <!--            <el-input v-model="form.auditUserPhone" placeholder="请输入审核人电话" :disabled="true"/>-->
          <!--          </el-form-item>-->
          <el-form-item label="审核" v-show="isShow" label-width="110px">
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
  addRegion, changeInvalidFlag,
  cityTree,
  delRegion,
  getRegion,
  listGsRegion,
  listRegionTwo,
  updateRegion,
  updateRegionOne
} from "@/api/applet/region";
import {treeselect} from "@/api/system/dept";
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "County",
  components: {
    TreeSelect
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
      // 行政区表格数据
      regionList: [],
      // 行政区树选项
      regionOptions: [],
      // 所属行政区树选项
      policeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      isShow: false,
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
        police: null,
        parentCode: null,
        parentName: null,
        forefathers: "0,520000",
        code: null,
        name: null,
        auditState: null,
        level: 2
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
        label: "cityName"
      },
      // 查询参数
      queryParamOnes: {
        name: null,
        level: 1,
        forefathers: "0,520000",
      },
      // 查询参数
      queryParamTwos: {
        level: 1,
        forefathers: "0,520000",
      }
    }
  },
  watch: {
    // 根据名称筛选行政机关树
    cityName(val) {
      if (val == null || val === "" || val === " ") {
        cityTree().then(response => {
          this.cityOptions = response.data;
        });
      } else {
        this.queryParamOnes.level = 1
        if (val === "贵州省") {
          this.queryParamOnes.level = 0
        }
        this.queryParamOnes.name = val
        listGsRegion(this.queryParamOnes).then(response => {
          this.cityOptions = response.data;
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
      });
    },
    /** 查询行政机关下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.policeOptions = response.data;
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
        level: 2,
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
        this.look = true;
        this.isShow = true;
        this.title = "审核县级县/区";
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
      this.look = false;
      this.open = true;
      this.title = "添加县/区";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const ids = row.code || this.ids
      getRegion(ids).then(response => {
        this.form = response.data;
        this.look = true;
        this.open = true;
        this.isShow = false;
        this.title = "修改县/区";
      });
    },
    handleRegionOptions() {
      listRegionTwo(this.queryParamTwos).then(response => {
        this.regionOptions = response.rows;
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.regionId != null) {
            updateRegion(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRegion(this.form).then(response => {
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
          if (this.form.auditState != "3") {
            updateRegionOne(this.form).then(response => {
                this.msgSuccess("审核成功");
                this.isShow = false;
                this.open = false;
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
      this.$confirm('是否确认删除县/区编号为"' + row.regionId + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delRegion(row.regionId);
      }).then(() => {
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
      if (data.cityCode !== 520000) {
        this.queryParams.parentCode = data.cityCode;
      }
      this.getList();
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

