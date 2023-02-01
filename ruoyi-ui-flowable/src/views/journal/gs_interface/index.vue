<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="请求路由" prop="reqUrl">
        <el-input
          v-model="queryParams.reqUrl"
          placeholder="请输入请求路由"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="请求方法" prop="reqMethod">
        <el-input
          v-model="queryParams.reqMethod"
          placeholder="请输入请求方法"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="请求描述" prop="executeRemark">
        <el-input
          v-model="queryParams.executeRemark"
          placeholder="请输入请求描述"
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
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['journal:gs_interface:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['journal:gs_interface:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gs_interfaceList" cell-class-name="table_cell" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="授权开关" align="center" prop="authFlag" :formatter="authFlagFormat" />
      <el-table-column label="请求路由" align="center" prop="reqUrl" show-overflow-tooltip/>
      <el-table-column label="请求参数" align="center" prop="reqParams" show-overflow-tooltip/>
      <el-table-column label="返回参数" align="center" prop="resultParams" show-overflow-tooltip/>
      <el-table-column label="请求方法" align="center" prop="reqMethod" show-overflow-tooltip/>
      <el-table-column label="请求描述" align="center" prop="executeRemark" show-overflow-tooltip/>
      <el-table-column label="执行时间" align="center" prop="executeTime" />
      <el-table-column label="请求ip" align="center" prop="reqIp" />
      <el-table-column label="token授权值" align="center" prop="token" show-overflow-tooltip/>
       <el-table-column label="记录时间" align="center" prop="createTime" show-overflow-tooltip/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['journal:gs_interface:edit']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['journal:gs_interface:remove']"
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

    <!-- 添加或修改接口日志对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="80%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="授权开关">
          <el-radio-group v-model="form.authFlag">
            <el-radio
              v-for="dict in authFlagOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="请求路由" prop="reqUrl">
          <el-input v-model="form.reqUrl" placeholder="请输入请求路由" />
        </el-form-item>
        <el-form-item label="请求参数" prop="reqParams">
          <el-input v-model="form.reqParams" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="返回参数" prop="resultParams">
          <el-input v-model="form.resultParams" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="请求方法" prop="reqMethod">
          <el-input v-model="form.reqMethod" placeholder="请输入请求方法" />
        </el-form-item>
        <el-form-item label="请求描述" prop="executeRemark">
          <el-input v-model="form.executeRemark" placeholder="请输入请求描述" />
        </el-form-item>
        <el-form-item label="执行时间" prop="executeTime">
          <el-input v-model="form.executeTime" placeholder="请输入执行时间" />
        </el-form-item>
        <el-form-item label="请求ip" prop="reqIp">
          <el-input v-model="form.reqIp" placeholder="请输入请求ip" />
        </el-form-item>
        <el-form-item label="执行异常描述" prop="excetionRemark">
          <el-input v-model="form.excetionRemark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="token授权值" prop="token">
          <el-input v-model="form.token" placeholder="请输入token授权值" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <!-- <el-button type="primary" @click="submitForm">确 定</el-button> -->
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listGs_interface, getGs_interface, delGs_interface, addGs_interface, updateGs_interface, exportGs_interface } from "@/api/journal/gs_interface";

export default {
  name: "Gs_interface",
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
      // 接口日志表格数据
      gs_interfaceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 授权开关 0false 1true字典
      authFlagOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        authFlag: null,
        reqUrl: null,
        reqParams: null,
        resultParams: null,
        reqMethod: null,
        executeRemark: null,
        executeTime: null,
        reqIp: null,
        excetionRemark: null,
        token: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.authFlagOptions = response.data;
    });
  },
  methods: {
    /** 查询接口日志列表 */
    getList() {
      this.loading = true;
      listGs_interface(this.queryParams).then(response => {
        this.gs_interfaceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 授权开关 0false 1true字典翻译
    authFlagFormat(row, column) {
      return this.selectDictLabel(this.authFlagOptions, row.authFlag);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        authFlag: 0,
        reqUrl: null,
        reqParams: null,
        resultParams: null,
        reqMethod: null,
        executeRemark: null,
        executeTime: null,
        reqIp: null,
        createTime: null,
        excetionRemark: null,
        token: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加接口日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGs_interface(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改接口日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGs_interface(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGs_interface(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除接口日志编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delGs_interface(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有接口日志数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportGs_interface(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
<style>

.table_cell .cell{
  white-space: nowrap;
}

</style>
