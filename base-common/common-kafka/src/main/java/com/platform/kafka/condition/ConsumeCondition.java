package com.platform.kafka.condition;

import com.platform.kafka.constant.KafkaConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author wulinhao
 * @ClassName: ConsumeCondition
 * @Description: 用作kafka消费者创建相关bean使用
 * @date 2020/7/9下午1:50
 *
 */
public class ConsumeCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String connectionType = conditionContext.getEnvironment().getProperty("fox.kafka.bootstrap.connection-type");

        //没有配置返回false
        if (StringUtils.isBlank(connectionType)){
            return false;
        }

        if (connectionType.equals(KafkaConstant.CONNECTION_ALL)){
            return true;
        }

        return connectionType.contains(KafkaConstant.CONNECTION_CONSUME);
    }
}
