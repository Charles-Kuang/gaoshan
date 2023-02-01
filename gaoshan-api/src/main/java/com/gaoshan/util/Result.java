package com.gaoshan.util;

import com.gaoshan.enumeration.HTTP_STATUS_CODE_ENUM;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author lumberer
 * @date 2022-11-19
 * 返回的类型集合
 */
@Data
@ApiModel("统一返回类")
public class Result {
    /**
     * 返回的消息提示
     */
    @ApiModelProperty("消息提示")
    private String message;
    /**
     * 返回的状态码
     */
    @ApiModelProperty("返回的状态码")
    private int state;
    /**
     * 返回的数据集合
     */
    @ApiModelProperty("返回的数据集合")
    private Object data;

    public static Result SUCCESS(Object data,String...message){
        Result result=new Result();
        result.setData(data);
        result.setState(HTTP_STATUS_CODE_ENUM.SUCCESS_STATUS.getStatus());
        if(message.length>0){
            result.setMessage(message[0]);
        }else{
            result.setMessage(HTTP_STATUS_CODE_ENUM.SUCCESS_STATUS.getMessage());
        }
        return result;
    }
    public static Result ERROR(String message,Integer state,Object... data){
        Result result=new Result();
        result.setMessage(message);
        result.setState(state);
        if(data.length>0){
            result.setData(data[0]);
        }else{
            result.setData(new ArrayList<>());
        }
        return result;
    }
    public static Result ERROR(HTTP_STATUS_CODE_ENUM emnu,Object... data){
        Result result=new Result();
        result.setMessage(emnu.getMessage());
        result.setState(emnu.getStatus());
        if(data.length>0){
            result.setData(data[0]);
        }else{
            result.setData(new ArrayList<>());
        }
        return result;
    }
}
