package com.study.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author yang
 * @date 2022/01/14 22:05
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {
    private  Integer status;
    private String msg;
    private T data;

    public ResponseVo(Integer status,String msg){
        this.status=status;
        this.msg=msg;
    }

    public static <T> ResponseVo<T> success(String msg){
        return new ResponseVo<T>(0,msg);
    }
}