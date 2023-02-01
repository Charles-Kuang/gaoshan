package com.gaoshan.enumeration;

import lombok.Getter;

/**
 * @author
 * @date
 */
@Getter
public enum HTTP_REQUEST_TYPE {
    PUT("PUT"),
    POST("POST"),
    DELETE("DELETE"),
    GET("GET");
    String value;
    private HTTP_REQUEST_TYPE(String value){
        this.value=value;
    }
}
