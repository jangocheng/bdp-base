package com.platform.factory;

import com.platform.handle.IConsumeHandle;

/**
 * @author wlhbdp
 * @ClassName: IHandleFactory
 * @Description: 定义消息处理工厂类接口
 *
 */
public interface IHandleFactory {

    /**
     * 根据数据类型获取相应的handle处理类
     * @param dataType 记录类型
     * @return
     * @see
     */
    IConsumeHandle getHandle(String dataType);

    /**
     * 注册消息处理类
     * @param dataType 数据类型
     * @param handle 处理实现类
     * @see
     */
    void registerHandle(String dataType, IConsumeHandle handle);

}
