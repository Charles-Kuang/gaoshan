package com.ruoyi.journal.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 操作日志对象 gs_operation_record
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@Data
@TableName("gs_operation_record")
public class GsOperationRecord{

    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 数据id
     */
    private Long dataId;
    /** 操作前的数据集合 */
    @Excel(name = "操作前的数据集合")
    private String prevData;

    /** 操作后的数据集合 */
    @Excel(name = "操作后的数据集合")
    private String laterData;

    /** 改动前的数据 */
    @Excel(name = "改动前的数据")
    private String prevUpdateData;

    /** 改动后的数据 */
    @Excel(name = "改动后的数据")
    private String laterUpdateData;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private Integer operationType;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operationUser;

    /** 操作人id */
    @Excel(name = "操作人id")
    private Long operationUserId;

    /** 操作人手机号 */
    @Excel(name = "操作人手机号")
    private String operationUserPhone;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operationTime;
}
