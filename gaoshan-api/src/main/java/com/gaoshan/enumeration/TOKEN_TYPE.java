package com.gaoshan.enumeration;

import lombok.Getter;

/**
 * @author
 * @date
 */
@Getter
public enum TOKEN_TYPE {
    TOKEN("token:TOKEN");
    private String value;
    private TOKEN_TYPE(String value){
        this.value=value;
    }
}
