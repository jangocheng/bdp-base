package com.platform.spring.controlleradvice;

import com.alibaba.fastjson.JSONException;
import com.platform.spring.bean.MessageTO;
import com.platform.spring.constant.ResultCode;
import com.platform.spring.exception.AuthException;
import com.platform.spring.exception.CommonException;
import com.platform.spring.exception.PollingException;
import com.platform.spring.util.ResponseUtil;
import lombok.extern.log4j.Log4j2;
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



/**
 * @author wulinhao
 * @ClassName: ExceptionHandlerAdvice
 * @ProjectName fox-spring-common
 * @Description: Controller Exception拦截
 * @date 2020/6/29下午3:16
 *
 */

@ControllerAdvice
@Log4j2
public class ExceptionHandlerAdvice {

    public ExceptionHandlerAdvice() {
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO exceptionResponse(Exception e) {
        log.error("exceptionResponse",e);
        return ResponseUtil.genResult(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误");
    }

    @ExceptionHandler({PollingException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO pollingResponse(PollingException pe) {
        log.error("pollingResponse",pe);
        return ResponseUtil.genResult(false, HttpStatus.RESET_CONTENT.value(), pe.getMessage());
    }

    @ExceptionHandler({AuthException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO authResponse(AuthException ae) {
        log.error("authResponse",ae);
        return ResponseUtil.genResult(false, HttpStatus.FORBIDDEN.value(), ae.getMessage());
    }

    @ExceptionHandler({CommonException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO commonResponse(CommonException ce) {
        log.error("commonResponse",ce);
        return ResponseUtil.genResult(false,  HttpStatus.BAD_REQUEST.value(), ce.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO httpRequestMethodResponse(HttpRequestMethodNotSupportedException hrmse) {
        log.error("httpRequestMethodResponse",hrmse);
        return ResponseUtil.genResult(false,  HttpStatus.METHOD_NOT_ALLOWED.value(), hrmse.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO httpMessageNotReadableResponse(HttpMessageNotReadableException hmnre) {
        log.error("httpMessageNotReadableResponse",hmnre);
        return ResponseUtil.genResult(false,  HttpStatus.BAD_REQUEST.value(), hmnre.getMessage());
    }

    @ExceptionHandler({JSONException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MessageTO httpMessageNotReadableResponse(JSONException je) {
        log.error("httpMessageNotReadableResponse",je);
        return ResponseUtil.genResult(false, ResultCode.FAIL, je.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageTO bindExceptionResponse(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("参数错误：");
        for (FieldError fieldError: bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getDefaultMessage()).append(", ");
        }
        log.error("bindExceptionResponse",e);
        return ResponseUtil.genResult(false, HttpStatus.BAD_REQUEST.value(), errorMessage.toString());
    }

}