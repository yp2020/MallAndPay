package com.study.mall.exception;

import com.study.mall.enums.ResponseEnum;
import com.study.mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yang
 * @date 2022/01/14 23:03
 **/
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseVo handle(RuntimeException e){
        return ResponseVo.error(ResponseEnum.ERROR,e.getMessage());
    }
}