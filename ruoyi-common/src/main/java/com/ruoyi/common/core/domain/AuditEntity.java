package com.ruoyi.common.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuditEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建人编号 */
    private Long createUserId;

    /** 创建人 */
    private String createUser;

    /** 创建人联系电话 */
    private String createUserPhone;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastUpdateTime;

    /** 最后更新人 */
    private String lastUpdateUser;

    /** 最后更新人编号 */
    private Long lastUpdateUserId;

    /** 最后更新人手机 */
    private String lastUpdateUserPhone;

    /** 审核状态 1待审核 */
    private Long auditState;

    /** 审核人 */
    private String auditUser;

    /** 审核人用户id */
    private Long auditUserId;

    /** 审核人联系电话 */
    private String auditUserPhone;

    /** 提交状态 */
    private Long commitStatus;

    /** 备注 */
    private String remark;

    /** 请求参数 */
    private Map<String, Object> params;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserPhone() {
        return createUserPhone;
    }

    public void setCreateUserPhone(String createUserPhone) {
        this.createUserPhone = createUserPhone;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Long getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(Long lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public String getLastUpdateUserPhone() {
        return lastUpdateUserPhone;
    }

    public void setLastUpdateUserPhone(String lastUpdateUserPhone) {
        this.lastUpdateUserPhone = lastUpdateUserPhone;
    }

    public Long getAuditState() {
        return auditState;
    }

    public void setAuditState(Long auditState) {
        this.auditState = auditState;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public Long getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditUserPhone() {
        return auditUserPhone;
    }

    public void setAuditUserPhone(String auditUserPhone) {
        this.auditUserPhone = auditUserPhone;
    }

    public Long getCommitStatus() {
        return commitStatus;
    }

    public void setCommitStatus(Long commitStatus) {
        this.commitStatus = commitStatus;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }
}
