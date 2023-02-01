<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="操作人" prop="operationUser">
        <el-input
          v-model="queryParams.operationUser"
          placeholder="请输入操作人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人手机号" prop="operationUserPhone">
        <el-input
          v-model="queryParams.operationUserPhone"
          placeholder="请输入操作人手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作时间范围" prop="operationTime">
        <el-date-picker clearable size="small"
                        v-model="queryParams.operationTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择操作时间">
        </el-date-picker>
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
          v-hasPermi="['journal:gs_operation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['journal:gs_operation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gs_operationList" cell-class-name="table_cell" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="操作前的数据集合" align="center" prop="prevData" show-overflow-tooltip/>
      <el-table-column label="操作后的数据集合" align="center" prop="laterData" show-overflow-tooltip/>
      <el-table-column label="改动前的数据" align="center" prop="prevUpdateData" show-overflow-tooltip/>
      <el-table-column label="改动后的数据" align="center" prop="laterUpdateData" show-overflow-tooltip/>
      <el-table-column label="操作类型" align="center" prop="operationType" />
      <el-table-column label="操作人" align="center" prop="operationUser" />
      <el-table-column label="操作人id" align="center" prop="operationUserId" />
      <el-table-column label="操作人手机号" align="center" prop="operationUserPhone" />
      <el-table-column label="操作时间" align="center" prop="operationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.operationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['journal:gs_operation:edit']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['journal:gs_operation:remove']"
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

    <!-- 添加或修改操作日志对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="80%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="操作前的数据集合" prop="prevData">
          <el-input v-model="form.prevData" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="操作后的数据集合" prop="laterData">
          <el-input v-model="form.laterData" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="改动前的数据" prop="prevUpdateData">
          <el-input v-model="form.prevUpdateData" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="改动后的数据" prop="laterUpdateData">
          <el-input v-model="form.laterUpdateData" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="操作类型" prop="operationType">
          <el-select v-model="form.operationType" placeholder="请选择操作类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="操作人" prop="operationUser">
          <el-input v-model="form.operationUser" placeholder="请输入操作人" />
        </el-form-item>
        <el-form-item label="操作人手机号" prop="operationUserPhone">
          <el-input v-model="form.operationUserPhone" placeholder="请输入操作人手机号" />
        </el-form-item>
        <el-form-item label="操作时间" prop="operationTime">
          <el-date-picker clearable size="small"
                          v-model="form.operationTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择操作时间">
          </el-date-picker>
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
import { listGs_operation, getGs_operation, delGs_operation, addGs_operation, updateGs_operation, exportGs_operation } from "@/api/journal/gs_operation";

export default {
  name: "Gs_operation",
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
      // 操作日志表格数据
      gs_operationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 审核状态字典
      auditStateOptions: [],
      // 提交状态字典
      commitStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        prevData: null,
        laterData: null,
        prevUpdateData: null,
        laterUpdateData: null,
        operationType: null,
        operationUser: null,
        operationUserId: null,
        operationUserPhone: null,
        operationTime: null,
        auditState: null,
        auditUser: null,
        auditUserId: null,
        auditUserPhone: null,
        commitStatus: null
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
    this.getDicts("gs_audit_type").then(response => {
      this.auditStateOptions = response.data;
    });
    this.getDicts("submit_state").then(response => {
      this.commitStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询操作日志列表 */
    getList() {
      this.loading = true;
      listGs_operation(this.queryParams).then(response => {
        this.gs_operationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        prevData: null,
        laterData: null,
        prevUpdateData: null,
        laterUpdateData: null,
        operationType: null,
        operationUser: null,
        operationUserId: null,
        operationUserPhone: null,
        operationTime: null,
        auditState: null,
        auditUser: null,
        auditUserId: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加操作日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGs_operation(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改操作日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGs_operation(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGs_operation(this.form).then(response => {
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
      this.$confirm('是否确认删除操作日志编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delGs_operation(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有操作日志数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportGs_operation(queryParams);
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
