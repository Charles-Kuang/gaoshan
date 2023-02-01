<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="区域" prop="regionId">
        <el-input
          v-model="queryParams.regionId"
          placeholder="请输入区域"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经度" prop="lng">
        <el-input
          v-model="queryParams.lng"
          placeholder="请输入经度"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="纬度" prop="lat">
        <el-input
          v-model="queryParams.lat"
          placeholder="请输入纬度"
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
          v-hasPermi="['applet:address:add']"
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
          v-hasPermi="['applet:address:edit']"
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
          v-hasPermi="['applet:address:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['applet:address:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="addressList" cell-class-name="table_cell" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="提交状态" align="center" prop="addressId" />
      <el-table-column label="区域" align="center" prop="regionId" />
      <el-table-column label="完整地址" align="center" prop="addressDetail" />
      <el-table-column label="经度" align="center" prop="lng" />
      <el-table-column label="纬度" align="center" prop="lat" />
      <el-table-column label="创建人编号" align="center" prop="createUserId" />
      <el-table-column label="创建用户名称" align="center" prop="createUser" />
      <el-table-column label="创建用户联系电话" align="center" prop="createUserPhone" />
      <el-table-column label="最后更新时间" align="center" prop="lastUpdateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后更新人" align="center" prop="lastUpdateUser" />
      <el-table-column label="最后更新人编号" align="center" prop="lastUpdateUserId" />
      <el-table-column label="最后更新人联系电话" align="center" prop="lastUpdateUserPhone" />
      <el-table-column label="审核人" align="center" prop="auditUser" />
      <el-table-column label="审核人编号" align="center" prop="auditUserId" />
      <el-table-column label="审核状态" align="center" prop="auditState" />
      <el-table-column label="审核人电话" align="center" prop="auditUserPhone" />
      <el-table-column label="提交状态" align="center" prop="commitStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['applet:address:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['applet:address:remove']"
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

    <!-- 添加或修改标准详址对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="区域" prop="regionId">
          <el-input v-model="form.regionId" placeholder="请输入区域" />
        </el-form-item>
        <el-form-item label="详细完整地址，多级联动用-分割" prop="addressDetail">
          <el-input v-model="form.addressDetail" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="经度" prop="lng">
          <el-input v-model="form.lng" placeholder="请输入经度" />
        </el-form-item>
        <el-form-item label="纬度" prop="lat">
          <el-input v-model="form.lat" placeholder="请输入纬度" />
        </el-form-item>
        <el-form-item label="创建人编号" prop="createUserId">
          <el-input v-model="form.createUserId" placeholder="请输入创建人编号" />
        </el-form-item>
        <el-form-item label="创建用户名称" prop="createUser">
          <el-input v-model="form.createUser" placeholder="请输入创建用户名称" />
        </el-form-item>
        <el-form-item label="创建用户联系电话" prop="createUserPhone">
          <el-input v-model="form.createUserPhone" placeholder="请输入创建用户联系电话" />
        </el-form-item>
        <el-form-item label="最后更新时间" prop="lastUpdateTime">
          <el-date-picker clearable size="small"
            v-model="form.lastUpdateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择最后更新时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最后更新人" prop="lastUpdateUser">
          <el-input v-model="form.lastUpdateUser" placeholder="请输入最后更新人" />
        </el-form-item>
        <el-form-item label="最后更新人编号" prop="lastUpdateUserId">
          <el-input v-model="form.lastUpdateUserId" placeholder="请输入最后更新人编号" />
        </el-form-item>
        <el-form-item label="最后更新人联系电话" prop="lastUpdateUserPhone">
          <el-input v-model="form.lastUpdateUserPhone" placeholder="请输入最后更新人联系电话" />
        </el-form-item>
        <el-form-item label="审核人" prop="auditUser">
          <el-input v-model="form.auditUser" placeholder="请输入审核人" />
        </el-form-item>
        <el-form-item label="审核人编号" prop="auditUserId">
          <el-input v-model="form.auditUserId" placeholder="请输入审核人编号" />
        </el-form-item>
        <el-form-item label="审核状态" prop="auditState">
          <el-input v-model="form.auditState" placeholder="请输入审核状态" />
        </el-form-item>
        <el-form-item label="审核人电话" prop="auditUserPhone">
          <el-input v-model="form.auditUserPhone" placeholder="请输入审核人电话" />
        </el-form-item>
        <el-form-item label="提交状态">
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAddress, getAddress, delAddress, addAddress, updateAddress, exportAddress } from "@/api/applet/address";

export default {
  name: "Address",
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
      // 标准详址表格数据
      addressList: [],
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
        regionId: null,
        addressDetail: null,
        lng: null,
        lat: null,
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
    /** 查询标准详址列表 */
    getList() {
      this.loading = true;
      listAddress(this.queryParams).then(response => {
        this.addressList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        addressId: null,
        regionId: null,
        addressDetail: null,
        lng: null,
        lat: null,
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
      this.ids = selection.map(item => item.addressId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加标准详址";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const addressId = row.addressId || this.ids
      getAddress(addressId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改标准详址";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.addressId != null) {
            updateAddress(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAddress(this.form).then(response => {
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
      const addressIds = row.addressId || this.ids;
      this.$confirm('是否确认删除标准详址编号为"' + addressIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAddress(addressIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有标准详址数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportAddress(queryParams);
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
