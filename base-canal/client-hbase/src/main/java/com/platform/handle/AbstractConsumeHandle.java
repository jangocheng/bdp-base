package com.platform.handle;


import com.platform.factory.IHandleFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author wulinhao
 * @ClassName: AbstractConsumeHandle
 * @Description: 向工厂类注册自己本身
 * @date 2020/7/11下午4:42
 *
 */
public abstract class AbstractConsumeHandle implements IConsumeHandle{

    @Resource
    private IHandleFactory handleFactory;

    @PostConstruct
    protected void registerHandle() {
        handleFactory.registerHandle(getDataType(), this);
    }

    protected abstract String getDataType();
}
