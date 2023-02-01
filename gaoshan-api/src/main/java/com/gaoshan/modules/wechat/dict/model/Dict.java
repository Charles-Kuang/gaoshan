package com.gaoshan.modules.wechat.dict.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * @date
 */
@ApiModel("查询字典集")
@Data
public class Dict {
    @ApiModelProperty("字典描述")
    private String remark;
    @ApiModelProperty("字典编码")
    private String dictCode;
    @ApiModelProperty("字典编码")
    private String dictSort;
    @ApiModelProperty("字典名")
    private String dictLabel;
    @ApiModelProperty("字典值")
    private String dictValue;
    @ApiModelProperty("字典类型")
    private String dictType;
    @ApiModelProperty("是否默认")
    private String defaults;
}
