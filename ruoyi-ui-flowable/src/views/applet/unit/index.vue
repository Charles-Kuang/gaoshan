<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单位名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入单位名称"
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
          v-hasPermi="['applet:unit:add']"
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
          v-hasPermi="['applet:house:edit']"
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
          v-hasPermi="['applet:unit:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="unitList" cell-class-name="table_cell"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="单位名称" align="center" prop="name"/>
      <el-table-column label="组织机构代码" align="center" prop="organizeCode"/>
      <el-table-column label="公司联系电话" align="center" prop="companyPhone"/>
      <el-table-column label="公司门头照" align="center" prop="doorImage"/>
      <el-table-column label="营业执照图" align="center" prop="businessLicense"/>
      <el-table-column label="经营范围" align="center" prop="businessScope"/>
      <el-table-column label="单位法人" align="center" prop="legalPerson"/>
      <el-table-column label="法人联系电话" align="center" prop="legalPersonPhone"/>
      <el-table-column label="法人身份证号" align="center" prop="corporateIdentityCard"/>
      <el-table-column label="单位类别" align="center" prop="type" :formatter="typeFormat"/>
      <el-table-column label="单位性质" align="center" prop="nature" :formatter="natureFormat"/>
      <el-table-column label="负责人联系电话" align="center" prop="principalMobile"/>
      <el-table-column label="单位成立日期" align="center" prop="establishDay" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.establishDay, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完整地址" align="center" prop="addressDetail"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="审核状态" align="center" prop="auditState" :formatter="auditStateFormat"/>
      <el-table-column label="审核人" align="center" prop="auditUser"/>
      <el-table-column label="创建人" align="center" prop="createUser"/>
      <el-table-column label="最后更新人" align="center" prop="lastUpdateUser"/>
      <el-table-column label="提交状态" align="center" prop="commitStatus" :formatter="commitStatusFormat"/>
      <el-table-column label="操作" align="center" width="180" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['applet:unit:edit']"
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
            v-hasPermi="['applet:unit:remove']"
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

    <!-- 添加或修改实有单位列对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="单位名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入单位名称" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位代码" prop="organizeCode">
              <el-input v-model="form.organizeCode" placeholder="请输入组织机构代码" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单位类别" prop="type">
              <el-select v-model="form.type" placeholder="请选择单位类别" style="width: 250px" :disabled="isShow">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位性质" prop="nature">
              <el-select v-model="form.nature" placeholder="请选择单位性质" style="width: 250px" :disabled="isShow">
                <el-option
                  v-for="dict in natureOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单位地址" prop="addressId">
              <el-cascader
                :options="regionOptions"
                v-model="form.addressId"
                :props="{checkStrictly: false,emitPath: false}"
                :disabled="isShow"
                style="width: 250px"
                clearable></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位详址" prop="addressDetail">
              <el-input v-model="form.addressDetail" placeholder="请输入完整详址" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="成立日期" prop="establishDay">
              <el-date-picker clearable size="small"
                              style="width: 250px"
                              v-model="form.establishDay"
                              type="date"
                              value-format="yyyy-MM-dd"
                              :disabled="isShow"
                              placeholder="选择单位成立日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经营范围" prop="businessScope">
              <el-input v-model="form.businessScope" placeholder="请输入内容" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="单位电话" prop="companyPhone">
              <el-input v-model="form.companyPhone" placeholder="请输入公司联系电话" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位法人" prop="legalPerson">
              <el-input v-model="form.legalPerson" placeholder="请输入单位法人" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="法人身份证号" prop="corporateIdentityCard" label-width="105px">
              <el-input v-model="form.corporateIdentityCard" placeholder="请输入法人身份证号" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="法人联系电话" prop="legalPersonPhone" label-width="110px">
              <el-input v-model="form.legalPersonPhone" placeholder="请输入法人联系电话" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="负责人联系电话" prop="principalMobile" label-width="120px">
              <el-input v-model="form.principalMobile" placeholder="请输入负责人联系电话" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司门头照" label-width="105px">
              <imageUpload v-model="form.doorImage" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="营业执照图" label-width="105px">
              <imageUpload v-model="form.businessLicense" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="审核" v-show="isShow">
          <el-radio-group v-model="form.commitStatus">
            <el-radio
              v-for="dict in commitStatusOptionsOne"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
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
import {listUnit, getUnit, delUnit, addUnit, updateUnit, exportUnit, updateUnitOne} from "@/api/applet/unit";
import ImageUpload from '@/components/ImageUpload';
import {getRegion, listRegion, listRegionOne} from "@/api/applet/region";
import {getHouse, updateHouseOne} from "@/api/applet/house";

export default {
  name: "Unit",
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
      // 实有单位列表格数据
      unitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      isShow: false,
      // 单位类别字典
      typeOptions: [],
      // 单位性质字典
      natureOptions: [],
      // 审核状态字典
      auditStateOptions: [],
      // 提交状态字典
      commitStatusOptions: [],
      // 提交状态字典
      commitStatusOptionsOne: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        addressId: null,
        addressDetail: null,
        name: null,
        organizeCode: null,
        legalPerson: null,
        legalPersonPhone: null,
        corporateIdentityCard: null,
        type: null,
        nature: null,
        principalMobile: null,
        establishDay: null,
        auditUserPhone: null,
        auditState: null,
        auditUser: null,
        auditUserId: null,
        createUserId: null,
        createUser: null,
        createUserPhone: null,
        lastUpdateUserId: null,
        lastUpdateUser: null,
        lastUpdateUserPhone: null,
        lastUpdateTime: null,
        commitStatus: null
      },
      queryParamOnes: {
        forefathers: "0,520000",
        level: 3
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 数据状态字典
      dataStateOptions: [],
      // 行政区树选项
      regionOptions: [],
    };
  },
  created() {
    this.getList();
    this.handleRegionOptions();
    this.getDicts("gs_company_type").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("gs_nature_of_the_unit").then(response => {
      this.natureOptions = response.data;
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
      this.dataStateOptions = response.data;
    });
  },
  methods: {
    /** 查询实有单位列列表 */
    getList() {
      this.loading = true;
      listUnit(this.queryParams).then(response => {
        this.unitList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 单位类别字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 单位性质字典翻译
    natureFormat(row, column) {
      return this.selectDictLabel(this.natureOptions, row.nature);
    },
    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState);
    },
    // 提交状态字典翻译
    commitStatusFormat(row, column) {
      return this.selectDictLabel(this.commitStatusOptions, row.commitStatus);
    },
    // 数据状态字典翻译
    dataStateFormat(row, column) {
      return this.selectDictLabel(this.dataStateOptions, row.dataState);
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
        unitId: null,
        addressId: null,
        addressDetail: null,
        name: null,
        organizeCode: null,
        companyPhone: null,
        doorImage: null,
        businessLicense: null,
        businessScope: null,
        legalPerson: null,
        legalPersonPhone: null,
        corporateIdentityCard: null,
        type: null,
        nature: null,
        principalMobile: null,
        establishDay: null,
        remark: null,
        auditUserPhone: null,
        auditState: null,
        auditUser: null,
        auditUserId: null,
        createUserId: null,
        createUser: null,
        createTime: null,
        createUserPhone: null,
        lastUpdateUserId: null,
        lastUpdateUser: null,
        lastUpdateUserPhone: null,
        lastUpdateTime: null,
        commitStatus: "1",
        dataState: "0"
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
      this.ids = selection.map(item => item.unitId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加实有单位列";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const unitId = row.unitId || this.ids
      getUnit(unitId).then(response => {
        this.form = response.data;
        this.isShow = false;
        this.open = true;
        this.title = "修改实有单位列";
      });
    },
    /** 审核按钮操作 */
    handleAudit(row) {
      this.reset();
      const unitId = row.unitId || this.ids
      getUnit(unitId).then(response => {
        this.form = response.data;
        this.open = true;
        this.isShow = true;
        this.title = "审核实有房屋";
        this.commitStatusOptionsOne = this.commitStatusOptions.slice(1);
      });
    },
    handleRegionOptions() {
      listRegionOne(this.queryParamOnes).then(response => {
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
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log(this.form)
          if (this.form.unitId != null) {
            updateUnit(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUnit(this.form).then(response => {
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
          if (this.form.auditState != 3) {
            if (this.form.unitId != null) {
              updateUnitOne(this.form).then(response => {
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
      const unitIds = row.unitId || this.ids;
      this.$confirm('是否确认删除实有单位列编号为"' + unitIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delUnit(unitIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有实有单位列数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportUnit(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
  }
};
</script>
<style>

.table_cell .cell {
  white-space: nowrap;
}

</style>

