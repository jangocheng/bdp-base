package com.platform.factory.impl;

import com.platform.factory.IHandleFactory;
import com.platform.handle.IConsumeHandle;
import com.platform.handle.impl.NoopHandleImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wulinhao
 * @ClassName: HanderFactoryImpl
 * @Description: 定义消息处理工厂类实现接口
 * @date 2020/7/11下午4:45
 *
 */

@Component
public class HanderFactoryImpl implements IHandleFactory {

    // 空操作
    private static final IConsumeHandle NOOP = new NoopHandleImpl();

    // 存放DataType与处理器类的对应关系
    private Map<String,IConsumeHandle> handleMap = new HashMap<>();

    @Override
    public IConsumeHandle getHandle(String dataType) {

        if (StringUtils.isEmpty(dataType)) {
            return NOOP;
        }

        IConsumeHandle handle = handleMap.get(dataType);

        if (handle == null) {
            handle = NOOP;
        }

        return handle;
    }

    /**
     * 向工厂类注册不同对应的处理器
     */
    @Override
    public void registerHandle(String dataType, IConsumeHandle handle) {
        handleMap.put(dataType, handle);
    }
}
