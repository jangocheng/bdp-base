package com.platform.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.FlatMessage;
import com.platform.bean.factory.SalaryCommon;
import com.platform.bean.factory.SalaryDailySumCommon;
import com.platform.canal.constant.DmlConstant;
import com.platform.hbase.api.HbaseTemplate;
import com.platform.hbase.util.HBytesUtil;
import com.platform.hbase.util.HPutUtil;
import com.platform.spring.log.CustomLogger;
import com.platform.spring.util.DatetimeUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wlhbdp
 * @ClassName: HbaseSalaryHandle
 * @Description: HbaseSalaryHandle
 *
 */
@Log4j2
public class HbaseSalaryHandle {

    CustomLogger logger = CustomLogger.getLogger();

    public  void addSalaryCommon(List<FlatMessage> messageList, String dataType, byte [] family, String table,HbaseTemplate hbaseTemplate){
        List<Put> putList = new ArrayList<>();
        Put put;
        StringBuffer rowKey;
        Long millis;
        SalaryCommon salary;
        String dataJson;
        String empNo;
        for (FlatMessage flatMessage:messageList) {
            if (DmlConstant.DML_INSERT.equals(flatMessage.getType())){
                List<Map<String,String>> dataList = flatMessage.getData();
                for (Map<String,String> data: dataList) {
                    data.put("dataType",dataType);
                    try{
                        dataJson = JSON.toJSONString(data);
                        salary = JSON.parseObject(dataJson,SalaryCommon.class);
                        empNo = salary.getEmpNo().trim();
                        salary.setEmpNo(empNo);
                        millis = DatetimeUtil.strToDate(salary.getWorkDate()).getTime();
                        salary.setCreateTime(DatetimeUtil.nowToString());
                        rowKey = new StringBuffer(MD5Hash.getMD5AsHex(Bytes.toBytes(salary.getEmpNo()))).append("-").append(millis);
                        put = new Put(HBytesUtil.toBytes(rowKey.toString()), millis);
                        put = HPutUtil.getPut(salary,put, family);
                        putList.add(put);
                        logger.data(JSON.toJSONString(salary));
                    }catch (Exception e){
                        log.error(dataType+" get hbase put error:"+JSON.toJSONString(data),e);
                    }
                }
            }
        }
        try{
            hbaseTemplate.addPuts(table, putList);
        }catch (Exception e){
            log.error(table+" save hbase error:"+JSON.toJSONString(messageList),e);
        }
    }


    public  void addSalaryDailySumCommon(List<FlatMessage> messageList, String dataType,byte [] family,String table,HbaseTemplate hbaseTemplate){
        List<Put> putList = new ArrayList<>();
        Put put;
        StringBuffer rowKey;
        Long millis;
        SalaryDailySumCommon salary;
        String dataJson;
        String empNo;
        for (FlatMessage flatMessage:messageList) {
            if (DmlConstant.DML_INSERT.equals(flatMessage.getType())){
                List<Map<String,String>> dataList = flatMessage.getData();
                for (Map<String,String> data: dataList) {
                    data.put("dataType",dataType);
                    try{
                        dataJson = JSON.toJSONString(data);
                        salary = JSON.parseObject(dataJson,SalaryDailySumCommon.class);
                        empNo = salary.getEmpNo().trim();
                        salary.setEmpNo(empNo);
                        millis = DatetimeUtil.strToDate(salary.getWorkDate()).getTime();
                        salary.setCreateTime(DatetimeUtil.nowToString());
                        rowKey = new StringBuffer(MD5Hash.getMD5AsHex(Bytes.toBytes(salary.getEmpNo()))).append("-").append(millis);
                        put = new Put(HBytesUtil.toBytes(rowKey.toString()), millis);
                        put = HPutUtil.getPut(salary,put, family);
                        putList.add(put);
                        logger.data(JSON.toJSONString(salary));
                    }catch (Exception e){
                        log.error(dataType+" get hbase put error:"+JSON.toJSONString(data),e);
                    }
                }
            }
        }
        try{
            hbaseTemplate.addPuts(table, putList);
        }catch (Exception e){
            log.error(table+" save hbase error:"+JSON.toJSONString(messageList),e);
        }
    }


}
