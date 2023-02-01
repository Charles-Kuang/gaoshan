package com.ruoyi.journal.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 实有房屋操作记录
 */
@Data
@TableName("gs_operation_house_record")
public class GsOperationHouseRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 数据主键id
     */
    private Long dataId;
    /**
     * 操作前数据
     */
    private String prevData;
    /**
     * 操作后数据
     */
    private String laterData;
    /**
     * 进行操作前的数据
     */
    private String prevUpdateData;
    /**
     * 进行操作后的数据
     */
    private String laterUpdateData;
    /**
     * 操作类型
     */
    private Integer operationType;
    /**
     * 操作人
     */
    private String operationUser;
    /**
     * 操作人用户id
     */
    private Long operationUserId;
    /**
     * 操作人联系电话
     */
    private String operationUserPhone;
    /**
     * 操作时间
     */
    private Date operationTime;
}
