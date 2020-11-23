package com.platform.base.adapter.aop;

import com.alibaba.fastjson.JSONException;
import com.platform.base.adapter.common.util.ResponseUtils;
import com.platform.base.adapter.entity.MessageTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    public ExceptionHandlerAdvice() {
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO exceptionResponse(Exception e) {
        log.error("exceptionResponse", e);
        return ResponseUtils.responseFailure("服务器错误");
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO httpRequestMethodResponse(HttpRequestMethodNotSupportedException hrmse) {
        log.error("httpRequestMethodResponse", hrmse);
        return ResponseUtils.responseFailure(hrmse.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO httpMessageNotReadableResponse(HttpMessageNotReadableException hmnre) {
        log.error("httpMessageNotReadableResponse", hmnre);
        return ResponseUtils.responseFailure(false, hmnre.getMessage());
    }

    @ExceptionHandler({JSONException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO httpMessageNotReadableResponse(JSONException je) {
        log.error("httpMessageNotReadableResponse", je);
        return ResponseUtils.responseFailure(je.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageTO bindExceptionResponse(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("参数错误：");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getDefaultMessage()).append(", ");
        }
        log.error("bindExceptionResponse", e);
        return ResponseUtils.responseFailure(errorMessage.toString());
    }

}