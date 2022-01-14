package com.study.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.mall.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

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

    public static <T> ResponseVo<T> success(){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getMsg());
    }

    public static <T> ResponseVo<T> success(String msg){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),msg);
    }

    public static <T> ResponseVo<T> error (ResponseEnum responseEnum){
        return new ResponseVo<T>(responseEnum.getCode(), responseEnum.getMsg());
    }

    public static <T> ResponseVo<T> error (ResponseEnum responseEnum,String msg){
        return new ResponseVo<T>(responseEnum.getCode(), msg);
    }

    public static <T> ResponseVo<T> error (ResponseEnum responseEnum, BindingResult bindingResult){
        return new ResponseVo<T>(responseEnum.getCode(), bindingResult.getFieldError().getField()+" "+ bindingResult.getFieldError().getDefaultMessage());
    }
}