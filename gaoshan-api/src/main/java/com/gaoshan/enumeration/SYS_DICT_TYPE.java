package com.gaoshan.enumeration;

import lombok.Getter;

/**
 * @author lumerer
 * @date 2022-11-25
 * 字典值
 */
@Getter
public enum SYS_DICT_TYPE {
    SYS_DICT("sys_dict:");
    private String value;
    private SYS_DICT_TYPE(String value){
        this.value=value;
    }
}
