<template>
  <div class="app-container">
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
      <el-form-item label="行政区名称" prop="name" label-width="100px">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入行政区简称"
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

    <!--    <el-row :gutter="10" class="mb8">-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="primary"-->
    <!--          plain-->
    <!--          icon="el-icon-plus"-->
    <!--          size="mini"-->
    <!--          @click="handleAdd"-->
    <!--          v-hasPermi="['applet:region:add']"-->
    <!--        >新增-->
    <!--        </el-button>-->
    <!--      </el-col>-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="success"-->
    <!--          plain-->
    <!--          icon="el-icon-edit"-->
    <!--          size="mini"-->
    <!--          :disabled="single"-->
    <!--          @click="handleUpdate"-->
    <!--          v-hasPermi="['applet:region:edit']"-->
    <!--        >修改-->
    <!--        </el-button>-->
    <!--      </el-col>-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="warning"-->
    <!--          plain-->
    <!--          icon="el-icon-s-check"-->
    <!--          size="mini"-->
    <!--          :disabled="single"-->
    <!--          @click="handleAudit"-->
    <!--        >审核-->
    <!--        </el-button>-->
    <!--      </el-col>-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="danger"-->
    <!--          plain-->
    <!--          icon="el-icon-delete"-->
    <!--          size="mini"-->
    <!--          :disabled="multiple"-->
    <!--          @click="handleDelete"-->
    <!--          v-hasPermi="['applet:region:remove']"-->
    <!--        >删除-->
    <!--        </el-button>-->
    <!--      </el-col>-->
    <!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    <!--    </el-row>-->

    <el-table
      v-loading="loading"
      :data="regionList"
      row-key="code"
      cell-class-name="table_cell"
      lazy
      :load="loadSearch"
      :row-class-name="changeColor"
      ref="multipleTable"
      :tree-props="{hasChildren: 'hasChild'}"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="70" align="right" eader-align="left"/>
      <el-table-column label="行政区划代码" align="center" prop="code" header-align="center"/>
      <el-table-column label="行政区划简称" align="center" prop="name"/>
      <el-table-column label="行政区划全称" align="center" prop="fullName"/>
      <el-table-column label="拼音首字母" align="center" prop="firstLetter"/>
      <el-table-column label="所属公安机关" align="center" prop="policeName" :show-overflow-tooltip="true"/>
      <!--      <el-table-column label="上级行政区划" align="center" width="100" prop="parentName"/>-->
      <!--      <el-table-column label="中心经度" align="center" prop="lng"/>-->
      <!--      <el-table-column label="中心纬度" align="center" prop="lat"/>-->
      <!--      <el-table-column label="提交状态" align="center" prop="commitStatus" :formatter="commitStatusFormat"/>-->
      <!--      <el-table-column label="审核状态" align="center" prop="auditState" :formatter="auditStateFormat"/>-->
      <!--      <el-table-column label="操作" fixed="right" align="center" width="180">-->
      <!--        <template slot-scope="scope">-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-s-check"-->
      <!--            @click="handleAudit(scope.row)"-->
      <!--          >审核-->
      <!--          </el-button>-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-edit"-->
      <!--            @click="handleUpdate(scope.row)"-->
      <!--            v-hasPermi="['applet:region:edit']"-->
      <!--          >修改-->
      <!--          </el-button>-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-delete"-->
      <!--            @click="handleDelete(scope.row)"-->
      <!--            v-hasPermi="['applet:region:remove']"-->
      <!--          >删除-->
      <!--          </el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
    </el-table>

    <!-- 添加或修改行政区对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上级行政区" prop="parentCode" label-width="100px">
          <el-cascader
            :options="regionOptions"
            v-model="form.parentCode"
            :props="props"
            :disabled="isShow"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="行政编码" prop="code" label-width="100px">
          <el-input v-model="form.code" placeholder="请输入行政区编码" :disabled="isShow"/>
        </el-form-item>
        <el-form-item label="行政区名称" prop="name" label-width="100px">
          <el-input v-model="form.name" placeholder="请输入行政区简称" :disabled="isShow"/>
        </el-form-item>
        <el-form-item label="所属公安机关" prop="police" label-width="100px">
          <el-cascader
            :options="policeOptions"
            v-model="form.police"
            :props="{checkStrictly: true,emitPath: false,value: 'id'}"
            placeholder="请选择所属公安机关"
            :disabled="isShow">
          </el-cascader>
        </el-form-item>
        <el-form-item label="创建人编号" v-show="isShow" prop="createUserId" label-width="100px">
          <el-input v-model="form.createUserId" placeholder="请输入创建人编号" :disabled="true"/>
        </el-form-item>
        <el-form-item label="创建用户名称" v-show="isShow" prop="createUser" label-width="100px">
          <el-input v-model="form.createUser" placeholder="请输入创建用户名称" :disabled="true"/>
        </el-form-item>
        <el-form-item label="创建用户联系电话" v-show="isShow" prop="createUserPhone" label-width="100px">
          <el-input v-model="form.createUserPhone" placeholder="请输入创建用户联系电话" :disabled="true"/>
        </el-form-item>
        <el-form-item label="最后更新时间" v-show="isShow" prop="lastUpdateTime" label-width="100px">
          <el-date-picker clearable size="small"
                          v-model="form.lastUpdateTime"
                          type="date"
                          :disabled="true"
                          value-format="yyyy-MM-dd"
                          placeholder="选择最后更新时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最后更新人" v-show="isShow" prop="lastUpdateUser" label-width="100px">
          <el-input v-model="form.lastUpdateUser" placeholder="请输入最后更新人" :disabled="true"/>
        </el-form-item>
        <el-form-item label="最后更新人编号" v-show="isShow" prop="lastUpdateUserId" label-width="100px">
          <el-input v-model="form.lastUpdateUserId" placeholder="请输入最后更新人编号" :disabled="true"/>
        </el-form-item>
        <el-form-item label="最后更新人联系电话" v-show="isShow" prop="lastUpdateUserPhone" label-width="100px">
          <el-input v-model="form.lastUpdateUserPhone" placeholder="请输入最后更新人联系电话" :disabled="true"/>
        </el-form-item>
        <el-form-item label="审核人" v-show="isShow" prop="auditUser" label-width="100px">
          <el-input v-model="form.auditUser" placeholder="请输入审核人" :disabled="true"/>
        </el-form-item>
        <el-form-item label="审核人编号" v-show="isShow" prop="auditUserId" label-width="100px">
          <el-input v-model="form.auditUserId" placeholder="请输入审核人编号" :disabled="true"/>
        </el-form-item>
        <el-form-item label="审核状态" prop="auditState" label-width="100px">
          <el-select v-model="form.auditState" placeholder="请选择审核状态" :disabled="true">
            <el-option
              v-for="dict in auditStateOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核人电话" v-show="isShow" prop="auditUserPhone" label-width="100px">
          <el-input v-model="form.auditUserPhone" placeholder="请输入审核人电话" :disabled="true"/>
        </el-form-item>
        <el-form-item label="提交状态" v-show="isShow">
          <el-radio-group v-model="form.commitStatus">
            <el-radio
              v-for="dict in commitStatusOptions"
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
  </div>
</template>

<script>
import {
  listRegion,
  getRegion,
  delRegion,
  addRegion,
  updateRegion,
  getChild,
  listRegionOne,
  loadChildRegion, updateRegionOne
} from "@/api/applet/region";
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {treeselect} from "@/api/system/dept";

export default {
  name: "Region",
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
      // 行政区表格数据
      regionList: [],
      // 行政区树选项
      regionOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      isShow: false,
      // 详址包含乡镇街道字典
      includeTownOptions: [],
      // 类型(1村委会 2居委会)字典
      typeOptions: [],
      // 所属行政区树选项
      policeOptions: [],
      // 详址包含社区、村(居)委会字典
      committeeOptions: [],
      // 审核状态字典
      auditStateOptions: [],
      // 提交状态字典
      commitStatusOptions: [],
      // 查询参数
      queryParams: {
        police: null,
        parentCode: null,
        code: null,
        name: null,
        auditState: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentCode: [
          {required: true, message: "上级行政区不能为空", trigger: "blur"}
        ],
        name: [
          {required: true, message: "行政区名称不能为空", trigger: "blur"}
        ],
        code: [
          {required: true, message: "行政编码不能为空", trigger: "blur"}
        ],
      },
      maps: new Map(),
      props: {
        checkStrictly: true,
        lazy: true,
        emitPath: false,
        lazyLoad: this.lazyLoad
      },
    }
  },
  created() {
    this.getList();
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
  },
  methods: {
    /** 查询行政区列表 */
    getList() {
      this.loading = true;
      if (this.queryParams.code === null && this.queryParams.name === null) {
        this.queryParams.parentCode = 0
      }
      listRegion(this.queryParams).then(response => {
        if (this.queryParams.code === null && this.queryParams.name === null) {
          this.regionList = this.handleTree(response.data, "code", "parentCode");
        } else {
          this.regionList = response.data;
        }
        this.queryParams.parentCode = null;
        this.loading = false;
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.isShow = false;
      this.reset();
      this.regionOptions = []
      this.tableData.map(el => {
        if (el.children) {
          // 解决子组件没有被勾选到
          this.setChildren(el.children, false)
        }
      })
    },
    // 表单重置
    reset() {
      this.form = {
        regionId: null,
        parentCode: null,
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
        level: null,
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
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 审核按钮操作 */
    handleAudit(row) {
      this.msgInfo("您暂无权限");
      // this.reset();
      // const ids = row.code || this.ids
      // getRegion(ids).then(response => {
      //   this.form = response.data;
      //   this.handleRegionOptions();
      //   this.open = true;
      //   this.isShow = true;
      //   this.title = "审核实有房屋";
      // });
    },
    /** 查询行政机关下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.policeOptions = response.data;
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
      this.msgInfo("您暂无权限");
      // this.reset();
      // this.open = true;
      // this.isShow = false;
      // this.title = "添加行政区";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.msgInfo("您暂无权限");
      // this.reset();
      // const ids = row.code || this.ids
      // getRegion(ids).then(response => {
      //   this.form = response.data;
      //   this.handleRegionOptions();
      //   this.open = true;
      //   this.isShow = false;
      //   this.title = "修改行政区";
      // });
    },
    handleRegionOptions() {
      if (this.form != null && (this.form.parentCode === null || this.form.parentCode === 0)) {
        this.form.parentCode = 0
        this.form.parentName = '中国'
        this.form.hasChild = true
      }
      if (this.regionOptions.length === 0) {
        this.regionOptions.push({value: this.form.parentCode, label: this.form.parentName, leaf: !this.form.hasChild})
      }
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
        this.regionOptions = [];
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
        this.regionOptions = []
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.msgInfo("您暂无权限");
      // this.$confirm('是否确认删除行政区编号为"' + row.regionId + '"的数据项?', "警告", {
      //   confirmButtonText: "确定",
      //   cancelButtonText: "取消",
      //   type: "warning"
      // }).then(function () {
      //   return delRegion(row.regionId);
      // }).then(() => {
      //   this.getList();
      //   this.msgSuccess("删除成功");
      // })
    },

    async loadSearch(row, treeNode, resolve) {
      this.maps.set(row.id, {row, treeNode, resolve})
      let loadData = [];
      getChild(row.code).then(response => {
        loadData = response.data;
      });
      setTimeout(() => {
        resolve(loadData)
      }, 2000)
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

    /** 拉取子行政区操作 */
    handleLoad(row) {
      loadChildRegion(row.code).then(response => {
        this.msgSuccess("拉取成功");
      });
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
    async lazyLoad(node, resolve) {
      let level = node.level
      if (this.regionOptions.length === 1) {
        this.regionOptions.pop()
        node = null
      }
      if (!node.data || this.regionOptions.length === 1) {
        this.queryParams.parentCode = 0
        let res = await listRegionOne(this.queryParams)
        const nodes = Array.from(res.data).map(item => ({
          value: parseInt(item.code),
          label: item.name,
          leaf: !item.hasChild
        }))
        resolve(nodes)
      } else {
        this.queryParams.parentCode = node.value
        let res = await listRegionOne(this.queryParams)
        const nodes = Array.from(res.data).map(item => ({
          value: parseInt(item.code),
          label: item.name,
          leaf: !item.hasChild
        }))
        resolve(nodes)
      }
      this.queryParams.parentCode = null
    }
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

