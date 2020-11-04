package com.platform.handle.impl.factory;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.FlatMessage;
import com.platform.bean.bbe.WokeAttendance;
import com.platform.canal.constant.DmlConstant;
import com.platform.constant.DataTypeConstant;
import com.platform.constant.HBaseConstant;
import com.platform.handle.AbstractConsumeHandle;
import com.platform.hbase.api.HbaseTemplate;
import com.platform.hbase.util.HBytesUtil;
import com.platform.hbase.util.HPutUtil;
import com.platform.kafka.annotation.FoxKafkaProducer;
import com.platform.kafka.producer.impl.KafkaMessageProducer;
import com.platform.spring.log.CustomLogger;
import com.platform.spring.util.DatetimeUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wulinhao
 * @ClassName: BbeWokeAttendanceHandleImpl
 * @Description: BbeWokeAttendanceHandleImpl
 * @date 2020-08-3014:46
 *
 */
@Component
@Log4j2
public class BbeWokeAttendanceHandleImpl extends AbstractConsumeHandle {

    @Resource
    private HbaseTemplate hbaseTemplate;

    @FoxKafkaProducer(topic = "topic-bbe")
    private KafkaMessageProducer producer;

    CustomLogger logger = CustomLogger.getLogger();

    @Override
    protected String getDataType() {
        return DataTypeConstant.BBE_WOKE_ATTENDANCE;
    }

    @Override
    public void handle(List<FlatMessage> messageList, String dataType) {
        List<Put> putList = new ArrayList<>();
        List<String> bbeJsonArray = new ArrayList<>();
        Put put;
        StringBuffer rowKey;
        Long millis;
        WokeAttendance attendance;
        String dataJson;
        String empNo;
        String bbeJson;
        for (FlatMessage flatMessage:messageList) {
            if (DmlConstant.DML_INSERT.equals(flatMessage.getType())){
                List<Map<String,String>> dataList = flatMessage.getData();
                for (Map<String,String> data: dataList) {
                    data.put("dataType",dataType);
                    try{
                        dataJson = JSON.toJSONString(data);
                        attendance = JSON.parseObject(dataJson,WokeAttendance.class);
                        empNo = attendance.getEmpNo().trim();
                        attendance.setEmpNo(empNo);
                        millis = DatetimeUtil.strToDate(attendance.getWorkDate()).getTime();
                        attendance.setCreateTime(DatetimeUtil.nowToString());
                        rowKey = new StringBuffer(MD5Hash.getMD5AsHex(Bytes.toBytes(attendance.getEmpNo()))).append("-").append(millis);
                        put = new Put(HBytesUtil.toBytes(rowKey.toString()), millis);
                        put = HPutUtil.getPut(attendance,put, HBaseConstant.FAMILY_BBE_WOKE_ATTENDANCE_INFO);
                        putList.add(put);
                        bbeJson = JSON.toJSONString(attendance);
                        logger.data(bbeJson);
                        bbeJsonArray.add(bbeJson);
                    }catch (Exception e){
                        log.error(dataType+" get hbase put error:"+JSON.toJSONString(data),e);
                    }
                }
            }
        }
        try{
            hbaseTemplate.addPuts(HBaseConstant.TABLE_BBE_WOKE_ATTENDANCE, putList);
            producer.sendText(bbeJsonArray);
        }catch (Exception e){
            log.error(HBaseConstant.TABLE_BBE_WOKE_ATTENDANCE+" save hbase error:"+JSON.toJSONString(messageList),e);
        }
    }
}
