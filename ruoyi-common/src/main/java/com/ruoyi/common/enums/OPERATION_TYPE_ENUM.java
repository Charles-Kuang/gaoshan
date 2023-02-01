package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * 操作类型
 */
@Getter
public enum OPERATION_TYPE_ENUM {
    ADDRESS(1),PERSON(2),HOUSE(3),UNIT(4);
    private Integer value;
    OPERATION_TYPE_ENUM(Integer value){
        this.value=value;
    }
    public static OPERATION_TYPE_ENUM getByValue(Integer value){
        for(OPERATION_TYPE_ENUM x:values()){
            if(value==x.getValue()){
                return x;
            }
        }
        return null;
    }
}
