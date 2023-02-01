package com.gaoshan.modules.wechat.common.domain;

import com.gaoshan.util.TokenUtil;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共参数
 * @author lumberer
 * @date 2022-11-25
 */
@Data
public class Common implements Serializable {
    /**
     * 创建人编号
     */
    @ApiModelProperty(hidden = true)
    private Integer createUserId;
    /**
     * 创建人用户名
     */
    @ApiModelProperty(hidden = true)
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;
    /**
     * 创建人联系电话
     */
    @ApiModelProperty(hidden = true)
    private String createUserPhone;
    /**
     * 最后更新人用户id
     */
    @ApiModelProperty(hidden = true)
    private Integer lastUpdateUserId;

    public Integer getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(Integer lastUpdateUserId) {
        if(null==lastUpdateUserId){
            lastUpdateUserId=TokenUtil.getTokenUser().getUserId();
        }
        this.lastUpdateUserId = lastUpdateUserId;
    }

    /**
     * 最后更新人用户
     */
    @ApiModelProperty(hidden = true)
    private String lastUpdateUser;
    public void setLastUpdateUser(String lastUpdateUser) {
        if(null==lastUpdateUser){
            lastUpdateUser= StringUtils.isNotEmpty(TokenUtil.getTokenUser().getRealName())?TokenUtil.getTokenUser().getRealName():TokenUtil.getTokenUser().getNickName();
        }
        this.lastUpdateUser = lastUpdateUser;
    }
    /**
     * 最后更新人联系电话
     */
    @ApiModelProperty(hidden = true)
    private String lastUpdateUserPhone;

    public void setLastUpdateUserPhone(String lastUpdateUserPhone) {
        if(null==lastUpdateUserPhone){
            lastUpdateUserPhone= TokenUtil.getTokenUser().getPhone()==null?this.getLastUpdateUserPhone():TokenUtil.getTokenUser().getPhone();
        }
        this.lastUpdateUserPhone = lastUpdateUserPhone;
    }
    /**
     * 最后更新时间
     */
    @ApiModelProperty(hidden = true)
    private Date lastUpdateTime=new Date();
    /**
     * 审核人
     */
    @ApiModelProperty(hidden = true)
    private String auditUser;
    /**
     * 审核人编号
     */
    @ApiModelProperty(hidden = true)
    private Integer auditUserId;
    /**
     * 审核状态
     */
    @ApiModelProperty("审核状态")
    private Integer auditState;
    /**
     * 审核人联系电话
     */
    @ApiModelProperty(hidden = true)
    private String auditUserPhone;
    /**
     * 提交的数据状态
     */
    @ApiModelProperty("提交状态")
    private Integer commitStatus;

}
