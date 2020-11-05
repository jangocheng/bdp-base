package com.platform.client;

import com.alibaba.otter.canal.protocol.FlatMessage;
import com.platform.canal.client.AbstractCanalKafkaClient;
import com.platform.factory.IHandleFactory;
import com.platform.handle.IConsumeHandle;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author wlhbdp
 * @ClassName: AbstractCanalKafkaClient
 * @Description: canal kafka client
 *
 */
@Service
@Log4j2
public class FactoryCanalClient extends AbstractCanalKafkaClient{

    @Resource
    public IHandleFactory handleFactory;

    @Override
    public void processMessage(Map<String,List<FlatMessage>> dbTableListMap){
        for (String dataType : dbTableListMap.keySet()) {
            IConsumeHandle handle = handleFactory.getHandle(dataType);
            List<FlatMessage> messageList = dbTableListMap.get(dataType);
            handle.handle(messageList,dataType);
        }
    }

}
