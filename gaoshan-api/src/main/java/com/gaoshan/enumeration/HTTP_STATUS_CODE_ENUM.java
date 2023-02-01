package com.gaoshan.enumeration;

import lombok.Data;
import lombok.Getter;

/**
 * @author lumberer
 * @date 2022-11-19
 * 通讯状态码集合枚举
 */
@Getter
public enum HTTP_STATUS_CODE_ENUM {
    SUCCESS_STATUS(200,"成功"),
    ERROR_STATUS(500,"失败"),
    NOT_FIND_STATUS(404,"未找到相关资源!"),
    NOT_FIND_DATA_STATUS(404,"查询条件不能为空!"),
    NO_PERMISSION(403,"没有权限进行操作"),
    TOKEN_NOT_FIND(404,"TOKEN已过期或者已失效，请重新登陆！");
    private Integer status;
    private String message;


    private HTTP_STATUS_CODE_ENUM(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
