package com.gaoshan.enumeration;

import lombok.Getter;

/**
 * TOKEN缓存的时间类型枚举
 */
@Getter
public enum TOKEN_TIME_TYPE {
    MINUTES("MINUTES"),DAYS("DAYS"),HOURS("HOURS"),SECONDS("SECONDS");
    public String value;
    TOKEN_TIME_TYPE(String value){
        this.value=value;
    }

    public static TOKEN_TIME_TYPE getByValue(String value){
        for(TOKEN_TIME_TYPE x:values()){
            if(value.equals(x.getValue())){
                return x;
            }
        }
        return null;
    }
}
