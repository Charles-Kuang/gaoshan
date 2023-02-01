<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属行政区" prop="addressId" label-width="100px">
        <el-cascader
          :options="regionOptions"
          v-model="queryParams.addressId"
          :props="{checkStrictly: false,emitPath: false}"
          style="width: 250px"
          clearable></el-cascader>
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
          v-hasPermi="['applet:house:add']"
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
          v-hasPermi="['applet:house:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="houseList" cell-class-name="table_cell"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="房屋面积" align="center" prop="houseArea"/>
      <el-table-column label="房屋用途" align="center" prop="houseUse" :formatter="houseUseFormat"/>
      <el-table-column label="房屋地址" align="center" prop="addressDetail"/>
      <el-table-column label="房间数" align="center" prop="houseNumber"/>
      <el-table-column label="其他名称" align="center" prop="anotherName"/>
      <el-table-column label="居住人数" align="center" prop="dwellPersonNumber"/>
      <el-table-column label="房屋类型" align="center" prop="houseType" :formatter="houseTypeFormat"/>
      <el-table-column label="关注级别" align="center" prop="attentionLevel"/>
      <el-table-column label="门头照" align="center" prop="doorImage"/>
      <el-table-column label="产权人/负责人" align="center" prop="propertyPerson"/>
      <el-table-column label="产权人/负责人身份证号" align="center" prop="propertyPersonIdentityNumber"/>
      <el-table-column label="产权人居住地址" align="center" prop="propertyPersonDwellAddress"/>
      <el-table-column label="产权人联系电话" align="center" prop="propertyPersonPhone"/>
      <el-table-column label="创建人" align="center" prop="createUser"/>
      <el-table-column label="最后更新人" align="center" prop="lastUpdateUser"/>
      <el-table-column label="审核人" align="center" prop="auditUser"/>
      <el-table-column label="审核状态" align="center" prop="auditState" :formatter="auditStateFormat"/>
      <el-table-column label="提交状态" align="center" prop="commitStatus" :formatter="commitStatusFormat"/>
      <el-table-column label="操作" align="center" width="180" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['applet:house:edit']"
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
            v-hasPermi="['applet:house:remove']"
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

    <!-- 添加或修改实有房屋对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="房屋面积" prop="houseArea">
              <el-input v-model="form.houseArea" placeholder="请输入房屋面积" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房屋用途" prop="houseUse">
              <el-select v-model="form.houseUse" placeholder="请选择房屋用途" style="width: 250px" :disabled="isShow">
                <el-option
                  v-for="dict in houseUseOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="房屋地址" prop="addressId">
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
            <el-form-item label="房屋详址" prop="detailAddress">
              <el-input v-model="form.detailAddress" placeholder="请输入详址" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="房间数" prop="houseNumber">
              <el-input v-model="form.houseNumber" placeholder="请输入房间数" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="其他名称" prop="anotherName">
              <el-input v-model="form.anotherName" placeholder="请输入其他名称/别名" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="居住人数" prop="dwellPersonNumber">
              <el-input v-model="form.dwellPersonNumber" placeholder="请输入居住人数" :disabled="isShow"/>
            </el-form-item>
            <el-form-item label="房屋类型" prop="houseType">
              <el-select v-model="form.houseType" style="width: 250px" placeholder="请选择房屋类型" :disabled="isShow">
                <el-option
                  v-for="dict in houseTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                  :disabled="isShow"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="关注级别" prop="attentionLevel">
              <el-input v-model="form.attentionLevel" placeholder="请输入关注级别" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="门头照">
              <imageUpload v-model="form.doorImage" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="产权人/负责人" prop="propertyPerson" label-width="110px">
              <el-input v-model="form.propertyPerson" placeholder="请输入产权人/负责人" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产权人身份证号" prop="propertyPersonIdentityNumber" label-width="120px">
              <el-input v-model="form.propertyPersonIdentityNumber" placeholder="请输入产权人身份证号" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="产权人居住地址" prop="propertyPersonDwellAddress" label-width="120px">
              <el-cascader
                :options="regionOptionOnes"
                v-model="form.propertyPersonDwellAddress"
                :props="{checkStrictly: false,emitPath: false}"
                :disabled="isShow"
                clearable></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产权人联系电话" prop="propertyPersonPhone" label-width="120px">
              <el-input v-model="form.propertyPersonPhone" placeholder="请输入产权人联系电话" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
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
import {listHouse, getHouse, delHouse, addHouse, updateHouse, exportHouse, updateHouseOne} from "@/api/applet/house";
import {getChild, getRegion, listRegionOne} from "@/api/applet/region";
import ImageUpload from '@/components/ImageUpload';

export default {
  name: "House",
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
      // 实有房屋表格数据
      houseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      isShow: false,
      // 是否核实字典
      verifyOptions: [],
      // 房屋用途字典
      houseUseOptions: [],
      // 房屋类型字典
      houseTypeOptions: [],
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
        detailAddress: null,
        houseNumber: null,
        dwellPersonNumber: null,
        anotherName: null,
        propertyPerson: null,
        propertyPersonIdentityNumber: null,
        propertyPersonDwellAddress: null,
        propertyPersonPhone: null,
        houseType: null,
        attentionLevel: null,
        houseArea: null,
        houseUse: null,
        doorImage: null,
        auditUserPhone: null,
        auditUser: null,
        auditState: null,
        auditUserId: null,
        createUserPhone: null,
        createUser: null,
        updateUserPhone: null,
      },
      queryParamOnes: {
        forefathers: "0,520000",
        level: 3
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 行政区树选项
      regionOptions: [],
      regionOptionOnes: []
    };
  },
  created() {
    this.getList();
    this.handleRegionOptions();
    this.getDicts("sys_yes_no").then(response => {
      this.verifyOptions = response.data;
    });
    this.getDicts("gs_address_element_type").then(response => {
      this.elementTypeOptions = response.data;
    });
    this.getDicts("gs_audit_type").then(response => {
      this.auditStateOptions = response.data;
    });
    this.getDicts("gs_house_use").then(response => {
      this.houseUseOptions = response.data;
    });
    this.getDicts("gs_commit_status").then(response => {
      this.commitStatusOptions = response.data;
      let commitStatusOptions = this.commitStatusOptions
      commitStatusOptions.forEach(function (val, index) {
        val.dictValue = Number(val.dictValue)
      })
      this.commitStatusOptions = commitStatusOptions
    });
    this.getDicts("gs_house_category").then(response => {
      this.houseTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询实有房屋列表 */
    getList() {
      this.loading = true;
      listHouse(this.queryParams).then(response => {
        this.houseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否核实字典翻译
    verifyFormat(row, column) {
      return this.selectDictLabel(this.verifyOptions, row.verify);
    },
    // 地址元素类型字典翻译
    elementTypeFormat(row, column) {
      return this.selectDictLabel(this.elementTypeOptions, row.elementType);
    },
    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState);
    },
    // 提交状态字典翻译
    commitStatusFormat(row, column) {
      return this.selectDictLabel(this.commitStatusOptions, row.commitStatus);
    },
    // 房屋用途字典翻译
    houseUseFormat(row, column) {
      return this.selectDictLabel(this.houseUseOptions, row.houseUse);
    },
    // 房屋类型字典翻译
    houseTypeFormat(row, column) {
      return this.selectDictLabel(this.houseTypeOptions, row.houseType);
    },
    // 取消按钮
    cancel() {
      this.isShow = false;
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        houseId: null,
        addressId: null,
        detailAddress: null,
        addressDetail: null,
        houseNumber: null,
        dwellPersonNumber: null,
        anotherName: null,
        propertyPerson: null,
        propertyPersonIdentityNumber: null,
        propertyPersonDwellAddress: null,
        propertyPersonPhone: null,
        houseType: null,
        attentionLevel: null,
        houseArea: null,
        houseUse: null,
        doorImage: null,
        remark: null,
        auditUserPhone: null,
        auditUser: null,
        auditState: 1,
        auditUserId: null,
        createUserPhone: null,
        createUser: null,
        createTime: null,
        lastUpdateUserPhone: null,
        lastUpdateUser: null,
        lastUpdateTime: null,
        lastUpdateUserId: null,
        createUserId: null,
        commitStatus: 1
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
      this.ids = selection.map(item => item.houseId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加实有房屋";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const houseId = row.houseId || this.ids
      getHouse(houseId).then(response => {
        this.form = response.data;
        this.isShow = false;
        this.open = true;
        this.title = "修改实有房屋";
      });
    },
    /** 审核按钮操作 */
    handleAudit(row) {
      this.reset();
      const houseId = row.houseId || this.ids
      getHouse(houseId).then(response => {
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
        regionOptions.forEach(function (val, index) {
          val.value = JSON.stringify(val.code)
          val.label = val.name
        })
        this.regionOptionOnes = this.getTreeData(this.handleTree(regionOptions, "code", "parentCode"));
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
          if (this.form.houseId != null) {
            console.log(this.form)
            updateHouse(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            console.log(this.form)
            addHouse(this.form).then(response => {
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
            if (this.form.houseId != null) {
              updateHouseOne(this.form).then(response => {
                this.msgSuccess("修改成功");
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
      const houseIds = row.houseId || this.ids;
      this.$confirm('是否确认删除实有房屋编号为"' + houseIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delHouse(houseIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有实有房屋数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportHouse(queryParams);
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

