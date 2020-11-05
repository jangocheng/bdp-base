package com.platform.handle;

import com.alibaba.otter.canal.protocol.FlatMessage;

import java.util.List;

/**
 * @author wlhbdp
 * @ClassName: IConsumeHandle
 * @Description: 消费处理类接口
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
