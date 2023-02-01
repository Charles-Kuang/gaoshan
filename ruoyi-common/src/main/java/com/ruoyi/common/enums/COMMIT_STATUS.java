package com.ruoyi.common.enums;

import lombok.Getter;

@Getter
public enum COMMIT_STATUS {
    AWAIT(1,"待审核"),PASS(3,"已通过"),REJECT(2,"驳回");
    private Integer status;
    private String name;
    private COMMIT_STATUS(Integer status,String name){
        this.status=status;
        this.name=name;
    }
}
