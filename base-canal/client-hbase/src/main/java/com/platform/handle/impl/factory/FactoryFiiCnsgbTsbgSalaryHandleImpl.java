package com.platform.handle.impl.factory;

import com.alibaba.otter.canal.protocol.FlatMessage;
import com.platform.constant.DataTypeConstant;
import com.platform.constant.HBaseConstant;
import com.platform.handle.AbstractConsumeHandle;
import com.platform.handle.HbaseSalaryHandle;
import com.platform.hbase.api.HbaseTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wlhbdp
 * @ClassName: FactoryBGroupSalaryHandleImpl
 *
 */
@Component
@Log4j2
public class FactoryFiiCnsgbTsbgSalaryHandleImpl extends AbstractConsumeHandle {

    @Resource
    private HbaseTemplate hbaseTemplate;

    @Override
    protected String getDataType() {
        return DataTypeConstant.FACTORY_FII_CNSGB_TSBG_SALARY;
    }

    @Override
    public void handle(List<FlatMessage> messageList, String dataType) {
        new HbaseSalaryHandle().addSalaryDailySumCommon(messageList,dataType,HBaseConstant.FAMILY_FACTORY_FII_CNSGB_TSBG_SALARY,HBaseConstant.TABLE_FACTORY_FII_CNSGB_TSBG_SALARY,hbaseTemplate);
    }


}
