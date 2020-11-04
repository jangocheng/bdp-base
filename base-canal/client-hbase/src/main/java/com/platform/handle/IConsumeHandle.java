package com.platform.handle;

import com.alibaba.otter.canal.protocol.FlatMessage;

import java.util.List;

/**
 * @author wulinhao
 * @ClassName: IConsumeHandle
 * @Description: 消费处理类接口
 * @date 2020/7/11下午4:40
 *
 */
public interface IConsumeHandle {

    /**
     * 业务处理逻辑
     * @param messageList
     * @see
     */
    public void handle(List<FlatMessage> messageList, String dataType);

}
