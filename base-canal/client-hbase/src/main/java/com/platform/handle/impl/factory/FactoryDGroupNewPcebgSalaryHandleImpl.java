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
 * @author wulinhao
 * @ClassName: FactoryBGroupSalaryHandleImpl
 * @Description: B次集团薪资数据
 * @date 2020/11/162:37 PM
 *
 */
@Component
@Log4j2
public class FactoryDGroupNewPcebgSalaryHandleImpl extends AbstractConsumeHandle {

    @Resource
    private HbaseTemplate hbaseTemplate;

    @Override
    protected String getDataType() {
        return DataTypeConstant.FACTORY_D_GROUP_NEW_PCEBG_SALARY;
    }

    @Override
    public void handle(List<FlatMessage> messageList,String dataType) {
        new HbaseSalaryHandle().addSalaryCommon(messageList,dataType,HBaseConstant.FAMILY_FACTORY_D_GROUP_NEW_PCEBG_SALARY,HBaseConstant.TABLE_FACTORY_D_GROUP_NEW_PCEBG_SALARY,hbaseTemplate);
    }

}
