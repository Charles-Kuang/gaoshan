<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件号码" prop="identityNumber">
        <el-input
          v-model="queryParams.identityNumber"
          placeholder="请输入证件号码"
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
          v-hasPermi="['applet:person:add']"
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
          v-hasPermi="['applet:person:edit']"
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
          v-hasPermi="['applet:person:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="personList" cell-class-name="table_cell"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="曾用名" align="center" prop="usedName"/>
      <el-table-column label="英文名" align="center" prop="englishName"/>
      <el-table-column label="性别" align="center" prop="sex" :formatter="sexFormat"/>
      <el-table-column label="年龄" align="center" prop="age"/>
      <el-table-column label="出生日期" align="center" prop="birthday" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="联系方式" align="center" prop="phoneNumber"/>
      <el-table-column label="民族" align="center" prop="nation" :formatter="nationFormat"/>
      <el-table-column label="人员类别" align="center" prop="personType" :formatter="personTypeFormat"/>
      <el-table-column label="人员照片" align="center" prop="personImg"/>
      <el-table-column label="证件类型" align="center" prop="identityType" :formatter="identityTypeFormat"/>
      <el-table-column label="证件号码" align="center" prop="identityNumber"/>
      <el-table-column label="身份证正面照" align="center" prop="identityFront"/>
      <el-table-column label="身份证反面照" align="center" prop="identityReverse"/>
      <el-table-column label="籍贯完整地址" align="center" prop="nativePlaceAddressDetail"/>
      <el-table-column label="现居完整地址" align="center" prop="addressDetail"/>
      <el-table-column label="暂住事由" align="center" prop="reason"/>
      <el-table-column label="文化程度" align="center" prop="education" :formatter="educationFormat"/>
      <el-table-column label="从业情况" align="center" prop="employment" :formatter="employmentFormat"/>
      <el-table-column label="职业" align="center" prop="job" :formatter="jobFormat"/>
      <el-table-column label="工作单位" align="center" prop="nowUnitId"/>
      <el-table-column label="婚姻状况" align="center" prop="marital" :formatter="maritalFormat"/>
      <el-table-column label="重点人员类别" align="center" prop="attentionLevel" :formatter="attentionFormat"/>
      <el-table-column label="知名人士类型" align="center" prop="celebrity" :formatter="celebrityFormat"/>
      <el-table-column label="是否帮扶救助对象" align="center" prop="supportObject" :formatter="helpFormat"/>
      <el-table-column label="交通工具" align="center" prop="vehicle"/>
      <el-table-column label="特长备注" align="center" prop="specialty"/>
      <el-table-column label="提交状态" align="center" prop="commitStatus" :formatter="commitStatusFormat"/>
      <el-table-column label="创建人" align="center" prop="createUser"/>
      <el-table-column label="最后更新人" align="center" prop="lastUpdateUser"/>
      <el-table-column label="审核状态" align="center" prop="auditState" :formatter="auditStateFormat"/>
      <el-table-column label="审核人" align="center" prop="auditUser"/>
      <el-table-column
        label="操作"
        align="center"
        width="180"
        fixed="right"
        class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:person:edit']"
          >修改
          </el-button>
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
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:person:remove']"
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

    <!-- 添加或修改实有人口登记对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="曾用名" prop="usedName">
              <el-input v-model="form.usedName" placeholder="请输入曾用名" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="英文名" prop="englishName">
              <el-input v-model="form.englishName" placeholder="请输入英文名" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="phoneNumber">
              <el-input v-model="form.phoneNumber" placeholder="请输入联系方式" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="人员类别" prop="personType">
              <el-select v-model="form.personType" style="width: 250px" placeholder="请选择人员类别" :disabled="isShow">
                <el-option
                  v-for="dict in personTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="民族" prop="nation">
              <el-select v-model="form.nation" style="width: 250px" placeholder="请选择民族" :disabled="isShow">
                <el-option
                  v-for="dict in nationOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="婚姻状况" prop="marital">
              <el-select v-model="form.marital" style="width: 250px" placeholder="请选择婚姻状况" :disabled="isShow">
                <el-option
                  v-for="dict in maritalOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="人员照片" :disabled="isShow">
              <imageUpload v-model="form.personImg"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="证件类型" prop="identityType">
              <el-select v-model="form.identityType" placeholder="请选择证件类型" style="width: 250px" :disabled="isShow">
                <el-option
                  v-for="dict in identityTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证件号码" prop="identityNumber">
              <el-input v-model="form.identityNumber" placeholder="请输入证件号码" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="身份证正面照" label-width="110px" v-show="!isShow">
              <imageUpload v-model="form.identityFront"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证反面照" label-width="110px" v-show="!isShow">
              <imageUpload v-model="form.identityReverse"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="籍贯地址" prop="nativePlaceAddressId">
              <el-cascader
                style="width: 250px"
                :options="regionOptionOnes"
                v-model="form.nativePlaceAddressId"
                :props="{checkStrictly: false,emitPath: false}"
                :disabled="isShow"
                clearable></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="籍贯详址" prop="nativePlaceAddressDetail">
              <el-input v-model="form.nativePlaceAddressDetail" placeholder="请输入籍贯完整地址" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="现居地址" prop="addressId">
              <el-cascader
                style="width: 250px"
                :options="regionOptions"
                v-model="form.addressId"
                :props="{checkStrictly: false,emitPath: false}"
                :disabled="isShow"
                clearable></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="现居详址" prop="addressDetail">
              <el-input v-model="form.addressDetail" placeholder="请输入详细完整地址" :disabled="isShow"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="暂住事由" prop="reason" v-show="this.form.addressId!==this.form.nativePlaceAddressId">
              <el-input v-model="form.reason" placeholder="请输入暂住事由" :disabled="isShow"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="文化程度" prop="education">
              <el-select v-model="form.education" style="width: 250px" placeholder="请选择文化程度" :disabled="isShow">
                <el-option
                  v-for="dict in educationOptions"
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
            <el-form-item label="从业情况" prop="employment">
              <el-select v-model="form.employment" style="width: 250px" placeholder="请输入从业情况" :disabled="isShow">
                <el-option
                  v-for="dict in employmentOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="重点人员类别" prop="attentionLevel" label-width="110px" v-show="!isShow">
              <el-select v-model="form.attentionLevel" clearable="clearable" style="width: 220px"
                         placeholder="请选择重点人员类别">
                <el-option
                  v-for="dict in attentionOptions"
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
            <el-form-item label="职业" prop="job" v-show="this.form.employment==='0'">
              <el-select v-model="form.job" style="width: 250px" placeholder="请选择职业" clearable="clearable"
                         :disabled="isShow">
                <el-option
                  v-for="dict in jobOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作单位" prop="nowUnitId" v-show="this.form.employment==='0'">
              <el-select v-model="form.nowUnitId" style="width: 250px" placeholder="请输入现工作单位" clearable="clearable"
                         :disabled="isShow">
                <el-option
                  v-for="dict in unitOptions"
                  :key="dict.unitId"
                  :label="dict.name"
                  :value="dict.unitId.toString()"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="知名人士" prop="celebrity" v-show="!isShow">
              <el-select v-model="form.celebrity" style="width: 250px" clearable="clearable" placeholder="请输入知名人士类型">
                <el-option
                  v-for="dict in celebrityOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否帮扶对象" prop="supportObject" v-show="!isShow" label-width="110px">
              <el-radio-group v-model="form.supportObject" clearable="clearable">
                <el-radio
                  v-for="dict in helpOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="交通工具" prop="vehicle" v-show="!isShow">
              <el-input v-model="form.vehicle" placeholder="请输入交通工具"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="特长备注" prop="specialty" v-show="!isShow">
              <el-input v-model="form.specialty" placeholder="请输入特长备注"/>
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
import {
  listPerson,
  getPerson,
  delPerson,
  addPerson,
  updatePerson,
  exportPerson,
  updatePersonOne
} from "@/api/applet/person";
import ImageUpload from '@/components/ImageUpload';
import {listRegionOne} from "@/api/applet/region";
import {listUnit} from "@/api/applet/unit";

export default {
  name: "Person",
  components: {
    ImageUpload,
  },
  data() {
    // 身份证号校验
    const checkIdNum = (rule, value, callback) => {
      const reg =
        /(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)/
      if (!value) {
        return callback(new Error('证件号码不能为空'))
      } else if (!reg.test(value)) {
        return callback(new Error('证件号码不正确'))
      } else {
        callback()
      }
    };
    return {
      clearable: true,
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
      // 实有人口登记表格数据
      personList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 证件类型字典
      identityTypeOptions: [],
      // 性别字典
      sexOptions: [],
      // 人员类别字典
      personTypeOptions: [],
      // 民族字典
      nationOptions: [],
      // 职业字典
      jobOptions: [],
      unitOptions: [],
      //从业情况
      employmentOptions: [],
      // 文化程度字典
      educationOptions: [],
      // 婚姻状况字典
      maritalOptions: [],
      // 审核状态字典
      auditStateOptions: [],
      // 提交状态字典
      commitStatusOptions: [],
      // 提交状态字典
      commitStatusOptionsOne: [],
      // 重点人员类别字典
      attentionOptions: [],
      //知名人士类型字典
      celebrityOptions: [],
      // 重点人员类别字典
      helpOptions: [],
      // 是否显示弹出层
      isShow: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        addressId: null,
        addressDetail: null,
        identityType: null,
        identityReverse: null,
        identityFront: null,
        identityNumber: null,
        name: null,
        usedName: null,
        sex: null,
        personImg: null,
        englishName: null,
        personType: null,
        age: null,
        nation: null,
        birthday: null,
        nativePlaceAddressId: null,
        nativePlaceAddressDetail: null,
        job: null,
        education: null,
        nowUnitId: null,
        marital: null,
        phoneNumber: null,
        reason: null,
        employment: null,
        createUserId: null,
        createUserPhone: null,
        createUser: null,
        lastUpdateUser: null,
        lastUpdateUserId: null,
        lastUpdateUserPhone: null,
        lastUpdateTime: null,
        auditState: null,
        auditUser: null,
        auditUserId: null,
        auditUserPhone: null,
        commitStatus: null,
        attentionLevel: null,
        celebrity: null,
        supportObject: null,
        specialty: null,
        vehicle: null
      },
      queryParamOnes: {
        level: 3
      },
      queryParamTwos: {
        addressId: null,
        forefathers: "0,520000"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        identityNumber:
          [{required: true, message: '请输入正确的身份证号', trigger: 'blur'},
            {required: true, validator: checkIdNum, trigger: 'blur'},],
        phoneNumber: [
          {required: true, message: "联系方式不能为空", trigger: "blur"},
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        personType: [
          {required: true, message: "人员类别不能为空", trigger: "blur"}
        ],
        identityType: [
          {required: true, message: "证件类型不能为空", trigger: "blur"}
        ],
        name: [
          {required: true, message: "姓名不能为空", trigger: "blur"}
        ],
      },
      // 行政区树选项
      regionOptions: [],
      // 行政区树选项
      regionOptionOnes: [],
    };
  },
  created() {
    this.getList();
    this.handleRegionOptions();
    this.getDicts("gs_type_of_certificate").then(response => {
      this.identityTypeOptions = response.data;
    });
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getDicts("gs_person_type").then(response => {
      this.personTypeOptions = response.data;
    });
    this.getDicts("gs_nation").then(response => {
      this.nationOptions = response.data;
    });
    this.getDicts("gs_occupation_detail").then(response => {
      this.jobOptions = response.data;
    });
    this.getDicts("gs_employment_fisheries").then(response => {
      this.employmentOptions = response.data;
    });
    this.getDicts("gs_education_degree").then(response => {
      this.educationOptions = response.data;
    });
    this.getDicts("gs_marital_status").then(response => {
      this.maritalOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.helpOptions = response.data;
    });
    this.getDicts("gs_audit_type").then(response => {
      this.auditStateOptions = response.data;
    });
    this.getDicts("gs_commit_status").then(response => {
      this.commitStatusOptions = response.data;
    });
    this.getDicts("gs_attention_level").then(response => {
      this.attentionOptions = response.data;
    });
    this.getDicts("gs_celebrity_type").then(response => {
      this.celebrityOptions = response.data;
    });
  },
  methods: {
    /** 查询实有人口登记列表 */
    getList() {
      this.loading = true;
      listPerson(this.queryParams).then(response => {
        this.personList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 证件类型字典翻译
    identityTypeFormat(row, column) {
      return this.selectDictLabel(this.identityTypeOptions, row.identityType);
    },
    // 性别字典翻译
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex);
    },
    // 人员类别字典翻译
    personTypeFormat(row, column) {
      return this.selectDictLabel(this.personTypeOptions, row.personType);
    },
    // 民族字典翻译
    nationFormat(row, column) {
      return this.selectDictLabel(this.nationOptions, row.nation);
    },
    // 职业字典翻译
    jobFormat(row, column) {
      return this.selectDictLabel(this.jobOptions, row.job);
    },
    // 职业字典翻译
    employmentFormat(row, column) {
      return this.selectDictLabel(this.employmentOptions, row.employment);
    },
    // 文化程度字典翻译
    educationFormat(row, column) {
      return this.selectDictLabel(this.educationOptions, row.education);
    },
    // 婚姻状况字典翻译
    maritalFormat(row, column) {
      return this.selectDictLabel(this.maritalOptions, row.marital);
    },
    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState);
    },
    // 提交状态字典翻译
    commitStatusFormat(row, column) {
      return this.selectDictLabel(this.commitStatusOptions, row.commitStatus);
    },
    // 提交状态字典翻译
    attentionFormat(row, column) {
      return this.selectDictLabel(this.attentionOptions, row.attentionLevel);
    },
    // 提交状态字典翻译
    celebrityFormat(row, column) {
      return this.selectDictLabel(this.celebrityOptions, row.attentionLevel);
    },
    // 提交状态字典翻译
    helpFormat(row, column) {
      return this.selectDictLabel(this.helpOptions, row.supportObject);
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
        personId: null,
        addressId: null,
        addressDetail: null,
        identityType: '0',
        identityReverse: null,
        identityFront: null,
        identityNumber: null,
        name: null,
        usedName: null,
        sex: '0',
        personImg: null,
        englishName: null,
        personType: '0',
        age: null,
        nation: '0',
        birthday: null,
        nativePlaceAddressId: null,
        nativePlaceAddressDetail: null,
        job: null,
        education: '0',
        nowUnitId: null,
        marital: '0',
        phoneNumber: null,
        reason: null,
        employment: '0',
        remark: null,
        createUserId: null,
        createUserPhone: null,
        createTime: null,
        createUser: null,
        lastUpdateUser: null,
        lastUpdateUserId: null,
        lastUpdateUserPhone: null,
        lastUpdateTime: null,
        auditState: 0,
        auditUser: null,
        auditUserId: null,
        auditUserPhone: null,
        commitStatus: 0,
        attentionLevel: null,
        celebrity: null,
        supportObject: 'N',
        specialty: null,
        vehicle: null
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
      this.ids = selection.map(item => item.personId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加实有人口登记";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const personId = row.personId || this.ids
      getPerson(personId).then(response => {
        this.form = response.data;
        this.handleUnitOptions();
        this.isShow = false;
        this.open = true;
        this.title = "修改实有人口登记";
      });
    },
    /** 审核按钮操作 */
    handleAudit(row) {
      this.reset();
      const personOneId = row.personId || this.ids
      getPerson(personOneId).then(response => {
        this.form = response.data;
        this.handleUnitOptions();
        this.open = true;
        this.isShow = true;
        this.title = "审核实有人口";
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
    handleUnitOptions() {
      if(this.form.addressId!==null){
        this.queryParamTwos.addressId=this.form.addressId.toString().substring(0,4);
      }
      listUnit(this.queryParamTwos).then(response => {
        console.log(response)
        this.unitOptions = response.rows;
      });
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
          if (this.form.personId != null) {
            updatePerson(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPerson(this.form).then(response => {
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
      const personIds = row.personId || this.ids;
      this.$confirm('是否确认删除实有人口登记编号为"' + personIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delPerson(personIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 提交按钮 */
    submitFormOne() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.auditState != 3) {
            if (this.form.personId != null) {
              updatePersonOne(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.isShow = false;
                this.getList();
              });
            }
          } else {
            this.msgSuccess("该数据已审核过");
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有实有人口登记数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportPerson(queryParams);
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

