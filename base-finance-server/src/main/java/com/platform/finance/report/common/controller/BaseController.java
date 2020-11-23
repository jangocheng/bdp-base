package com.platform.finance.report.common.controller;

import com.platform.finance.report.common.mo.ResponseMO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public abstract class BaseController {

    public <T> ResponseMO<T> success(T resMo, String msg) {
        ResponseMO<T> responseMO = new ResponseMO<T>();
        responseMO.setMsg(msg);
        responseMO.setData(resMo);
        return responseMO;
    }

    public <T> ResponseMO<T> successWithData(T resMo) {
        ResponseMO<T> responseMO = new ResponseMO<T>();
        responseMO.setData(resMo);
        return responseMO;
    }

    public <T> ResponseMO<T> success() {
        return new ResponseMO<>();
    }

    public <T> ResponseMO<T> success(String msg) {
        ResponseMO<T> responseMO = new ResponseMO<T>();
        responseMO.setMsg(msg);
        return responseMO;
    }

    public <T> ResponseMO<T> error(T resMo, String msg) {
        ResponseMO<T> responseMO = new ResponseMO<T>();
        responseMO.setResponseCodeFailure();
        responseMO.setMsg(msg);
        responseMO.setData(resMo);
        return responseMO;
    }

    public <T> ResponseMO<T> error(String msg) {
        ResponseMO<T> responseMO = new ResponseMO<T>();
        responseMO.setResponseCodeFailure();
        responseMO.setMsg(msg);
        return responseMO;
    }

    public <T> ResponseMO<T> custom(int code, String msg) {
        ResponseMO<T> responseMO = new ResponseMO<T>();
        responseMO.setCode(code);
        responseMO.setMsg(msg);
        return responseMO;
    }

    public <T> ResponseMO<T> custom(T resMo, int code, String msg) {
        ResponseMO<T> responseMO = new ResponseMO<T>();
        responseMO.setCode(code);
        responseMO.setMsg(msg);
        responseMO.setData(resMo);
        return responseMO;
    }

    /**
     * 获取用户id
     *
     * @return
     */
    protected String getCurrentUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("mobile");
    }
}
