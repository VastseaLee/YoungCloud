package com.young.common.config;

import com.young.common.bean.WebResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WebResult handleMANVException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        int errorSize = fieldErrorList.size();
        StringBuilder errMsg = new StringBuilder(errorSize * 16);
        fieldErrorList.forEach(fieldError -> {
            errMsg.append("[").append(fieldError.getField()).append(":")
                    .append(fieldError.getDefaultMessage()).append("]");
        });
        return WebResult.fail(5,errMsg.toString());
    }
}
