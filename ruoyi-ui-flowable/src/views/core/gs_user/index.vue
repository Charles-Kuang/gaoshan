<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="真实姓名" prop="realName">
        <el-input
          v-model="queryParams.realName"
          placeholder="请输入真实姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号" prop="idNumber">
        <el-input
          v-model="queryParams.idNumber"
          placeholder="请输入身份证号"
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['core:gs_user:edit']"
        >修改
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
          v-hasPermi="['core:gs_user:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gs_userList" cell-class-name="table_cell"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="用户编号" align="center" prop="userId"/>
      <el-table-column label="昵称" align="center" prop="nickName"/>
      <el-table-column label="真实姓名" align="center" prop="realName"/>
      <el-table-column label="性别" align="center" prop="gender" :formatter="genderFormat"/>
      <el-table-column label="电话" align="center" prop="phone"/>
      <el-table-column label="身份证号" align="center" prop="idNumber"/>
      <el-table-column label="积分余额" align="center" prop="integral"/>
      <el-table-column label="头像地址" align="center" prop="avatar"/>
      <el-table-column label="小程序openid" align="center" prop="miniOpenId"/>
      <el-table-column label="来源地区" align="center" prop="sourceId"/>
      <el-table-column label="来源区域地址" align="center" prop="sourceAddress"/>
      <el-table-column label="现居住房屋id(门牌号）" align="center" prop="houseId"/>
      <el-table-column label="现居住地址完整地址" align="center" prop="houseAddress"/>
      <el-table-column label="注册时间" align="center" prop="registerTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.registerTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后一次登录时间" align="center" prop="lastLoginTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastLoginTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否入库" align="center" prop="entering" :formatter="enteringFormat"/>
      <el-table-column label="操作" align="center" width="120" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['core:gs_user:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['core:gs_user:remove']"
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

    <!-- 添加或修改小程序用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="form.realName" placeholder="请输入真实姓名"/>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话"/>
        </el-form-item>
        <el-form-item label="身份证号" prop="idNumber">
          <el-input v-model="form.idNumber" placeholder="请输入身份证号"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别">
              <el-select v-model="form.gender">
                <el-option
                  v-for="dict in genderOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色">
              <el-select v-model="form.roleIds" multiple placeholder="请选择">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listGs_user, getGs_user, delGs_user, addGs_user, updateGs_user, exportGs_user} from "@/api/core/gs_user";

export default {
  name: "Gs_user",
  components: {},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedGsUserIntegralLog: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 小程序用户表格数据
      gs_userList: [],
      // 积分记录表格数据
      gsUserIntegralLogList: [],
      // 角色列表
      roleOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 性别字典
      genderOptions: [],
      // 是否入库字典
      enteringOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        phone: null,
        integral: null,
        gender: null,
        nickName: null,
        realName: null,
        avatar: null,
        miniOpenId: null,
        sourceId: null,
        sourceAddress: null,
        houseId: null,
        houseAddress: null,
        idNumber: null,
        registerTime: null,
        lastLoginTime: null,
        entering: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_user_sex").then(response => {
      this.genderOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.enteringOptions = response.data;
    });
  },
  methods: {
    /** 查询小程序用户列表 */
    getList() {
      this.loading = true;
      listGs_user(this.queryParams).then(response => {
        this.gs_userList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 性别字典翻译
    genderFormat(row, column) {
      return this.selectDictLabel(this.genderOptions, row.gender);
    },
    // 是否入库字典翻译
    enteringFormat(row, column) {
      return this.selectDictLabel(this.enteringOptions, row.entering);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        userId: null,
        phone: null,
        integral: null,
        gender: 0,
        nickName: null,
        realName: null,
        avatar: null,
        miniOpenId: null,
        sourceId: null,
        sourceAddress: null,
        houseId: null,
        houseAddress: null,
        idNumber: null,
        registerTime: null,
        lastLoginTime: null,
        entering: 0,
        roleIds: []
      };
      this.roleOptions = [];
      this.gsUserIntegralLogList = [];
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
      this.ids = selection.map(item => item.userId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加小程序用户";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.userId || this.ids
      getGs_user(userId).then(response => {
        this.form = response.data;
        this.gsUserIntegralLogList = response.data.gsUserIntegralLogList;
        this.roleOptions = response.data.roles;
        this.form.roleIds = response.data.roleIds;
        this.open = true;
        this.title = "修改小程序用户";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.gsUserIntegralLogList = this.gsUserIntegralLogList;
          if (this.form.userId != null) {
            updateGs_user(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGs_user(this.form).then(response => {
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
      const userIds = row.userId || this.ids;
      this.$confirm('是否确认删除小程序用户编号为"' + userIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delGs_user(userIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 积分记录序号 */
    rowGsUserIntegralLogIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 积分记录添加按钮操作 */
    handleAddGsUserIntegralLog() {
      let obj = {};
      obj.name = "";
      obj.tradTime = "";
      obj.credits = "";
      obj.type = "";
      obj.remark = "";
      this.gsUserIntegralLogList.push(obj);
    },
    /** 积分记录删除按钮操作 */
    handleDeleteGsUserIntegralLog() {
      if (this.checkedGsUserIntegralLog.length == 0) {
        this.$alert("请先选择要删除的积分记录数据", "提示", {confirmButtonText: "确定",});
      } else {
        this.gsUserIntegralLogList.splice(this.checkedGsUserIntegralLog[0].index - 1, 1);
      }
    },
    /** 单选框选中数据 */
    handleGsUserIntegralLogSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.gsUserIntegralLog.clearSelection();
        this.$refs.gsUserIntegralLog.toggleRowSelection(selection.pop());
      } else {
        this.checkedGsUserIntegralLog = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有小程序用户数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportGs_user(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
<style>

.table_cell .cell {
  white-space: nowrap;
}

</style>
