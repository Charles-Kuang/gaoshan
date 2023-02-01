<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input
          v-model="queryParams.roleName"
          placeholder="请输入角色名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="角色权限字符串" prop="roleKey">
        <el-input
          v-model="queryParams.roleKey"
          placeholder="请输入角色权限字符串"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="显示顺序" prop="roleSort">
        <el-input
          v-model="queryParams.roleSort"
          placeholder="请输入显示顺序"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据范围" prop="dataScope">
        <el-select v-model="queryParams.dataScope" placeholder="请选择数据范围" clearable size="small">
          <el-option
            v-for="dict in dataScopeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="角色状态" prop="roleState">
        <el-select v-model="queryParams.roleState" placeholder="请选择角色状态" clearable size="small">
          <el-option
            v-for="dict in roleStateOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="数据状态" prop="dataState">
        <el-select v-model="queryParams.dataState" placeholder="请选择数据状态" clearable size="small">
          <el-option
            v-for="dict in dataStateOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
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
          v-hasPermi="['core:gs_role:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['core:gs_role:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['core:gs_role:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['core:gs_role:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gs_roleList" cell-class-name="table_cell" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="角色" align="center" prop="roleId" />
      <el-table-column label="角色名称" align="center" prop="roleName" />
      <el-table-column label="角色权限字符串" align="center" prop="roleKey" />
      <el-table-column label="显示顺序" align="center" prop="roleSort" />
      <el-table-column label="数据范围" align="center" prop="dataScope" :formatter="dataScopeFormat" />
      <el-table-column label="角色状态" align="center" prop="roleState" :formatter="roleStateFormat" />
      <el-table-column label="数据状态" align="center" prop="dataState" :formatter="dataStateFormat" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['core:gs_role:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['core:gs_role:remove']"
          >删除</el-button>
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

    <!-- 添加或修改前台角色对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="角色名称" prop="roleName" label-width="100px">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色权限字符串" prop="roleKey" label-width="100px">
          <el-input v-model="form.roleKey" placeholder="请输入角色权限字符串" />
        </el-form-item>
        <el-form-item label="显示顺序" prop="roleSort" label-width="100px">
          <el-input v-model="form.roleSort" placeholder="请输入显示顺序" />
        </el-form-item>
        <el-form-item label="数据范围" prop="dataScope" label-width="100px">
          <el-select v-model="form.dataScope" placeholder="请选择数据范围">
            <el-option
              v-for="dict in dataScopeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="管辖行政区" v-show="form.dataScope==5" prop="regionIds" label-width="100px">
          <el-cascader
            v-model="form.regionIds"
            :props="props"
            :show-all-levels="false"
            clearable></el-cascader>
        </el-form-item>
        <el-form-item label="角色状态" prop="roleState" label-width="100px">
          <el-select v-model="form.roleState" placeholder="请选择角色状态">
            <el-option
              v-for="dict in roleStateOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据状态" prop="dataState" label-width="100px">
          <el-select v-model="form.dataState" placeholder="请选择数据状态">
            <el-option
              v-for="dict in dataStateOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark" label-width="100px">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listGs_role, getGs_role, delGs_role, addGs_role, updateGs_role, exportGs_role } from "@/api/core/gs_role";
import {getChild, listRegionOne} from "@/api/applet/region";

export default {
  name: "Gs_role",
  components: {
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
      // 前台角色表格数据
      gs_roleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 数据范围字典
      dataScopeOptions: [],
      // 角色状态字典
      roleStateOptions: [],
      // 数据状态字典
      dataStateOptions: [],
      // 行政区树选项
      regionOptions: [],
      maps: new Map(),
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleName: null,
        roleKey: null,
        roleSort: null,
        dataScope: null,
        roleState: null,
        dataState: null,
      },
      // 查询参数
      queryParamOnes: {
        parentCode: null,
        code: null,
        name: null,
        type: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        defaultProps: {
          children: "children",
          label: "label"
        },
        roleName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        roleKey: [
          { required: true, message: "角色权限字符串不能为空", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "显示顺序不能为空", trigger: "blur" }
        ],
        roleState: [
          { required: true, message: "角色状态不能为空", trigger: "change" }
        ],
      },
      props: {
        checkStrictly:true,
        multiple:true,
        lazy: true,
        emitPath: false,
        lazyLoad: this.lazyLoad
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("gs_data_field").then(response => {
      this.dataScopeOptions = response.data;
    });
    this.getDicts("sys_normal_disable").then(response => {
      this.roleStateOptions = response.data;
    });
    this.getDicts("sys_normal_disable").then(response => {
      this.dataStateOptions = response.data;
    });
  },
  methods: {
    /** 查询前台角色列表 */
    getList() {
      this.loading = true;
      listGs_role(this.queryParams).then(response => {
        this.gs_roleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 数据范围字典翻译
    dataScopeFormat(row, column) {
      return this.selectDictLabel(this.dataScopeOptions, row.dataScope);
    },
    // 角色状态字典翻译
    roleStateFormat(row, column) {
      return this.selectDictLabel(this.roleStateOptions, row.roleState);
    },
    // 数据状态字典翻译
    dataStateFormat(row, column) {
      return this.selectDictLabel(this.dataStateOptions, row.dataState);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        roleId: null,
        roleName: null,
        roleKey: null,
        roleSort: null,
        dataScope: null,
        roleState: null,
        dataState: null,
        regionId: null,
        regionIds: [],
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加前台角色";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const roleId = row.roleId || this.ids
      getGs_role(roleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.regionOptions=response.data.children;
        this.title = "修改前台角色";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.roleId != null) {
            updateGs_role(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGs_role(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const roleIds = row.roleId || this.ids;
      this.$confirm('是否确认删除前台角色编号为"' + roleIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delGs_role(roleIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有前台角色数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportGs_role(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },

    async lazyLoad(node, resolve) {
      let level = node.level
      if(this.queryParamOnes.length===1){
        this.queryParamOnes.pop()
        node=null
      }
      if (!node.data || this.queryParamOnes.length===1) {
        this.queryParamOnes.parentCode = 0
        let res = await listRegionOne(this.queryParamOnes)
        const nodes = Array.from(res.data).map(item => ({
          value: parseInt(item.code),
          label: item.name,
          leaf: !item.hasChild
        }))
        resolve(nodes)
      } else {
        this.queryParamOnes.parentCode = node.value
        let res = await listRegionOne(this.queryParamOnes)
        const nodes = Array.from(res.data).map(item => ({
          value: parseInt(item.code),
          label: item.name,
          leaf: !item.hasChild,
        }))
        resolve(nodes)
      }
      this.queryParamOnes.parentCode = null
    }
  }
};
</script>
<style>

.table_cell .cell {
  white-space: nowrap;
}

</style>
