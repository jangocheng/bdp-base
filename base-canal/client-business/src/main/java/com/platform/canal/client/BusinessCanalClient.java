package com.platform.canal.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.FlatMessage;
import com.platform.spring.log.CustomLogger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wlhbdp
 * @ClassName: BusinessCanalClient
 * @Description: canal kafka client
 *
 */
@Service
public class BusinessCanalClient extends AbstractCanalKafkaClient{



    CustomLogger logger = CustomLogger.getLogger();


    @Override
    public void processMessage(Map<String,List<FlatMessage>> dbTableListMap){
        String dmlType;
        List<FlatMessage> messageList;
        List<Map<String,String>> dataList;
        String customDbTable;
        for (String dataType : dbTableListMap.keySet()) {
             messageList = dbTableListMap.get(dataType);
            for (FlatMessage message:messageList) {
                dataList = message.getData();
                dmlType = message.getType();
                customDbTable = new StringBuffer("mysql").append(".").append(dataType).toString();
                for (Map<String,String> data : dataList) {
                    data.put("dataType",customDbTable);
                    data.put("dmlType",dmlType);
                    data.put("logTs",message.getTs().toString());
                    logger.data(JSON.toJSONString(data));
                }
            }
        }
    }
}
