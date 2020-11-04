package com.platform.kafka.producer.impl;

import com.platform.spring.exception.JmsException;
import com.platform.spring.exception.MQException;
import com.platform.kafka.producer.IMessageProducer;
import lombok.Setter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wulinhao
 * 
 */
@Component
public class KafkaMessageProducer implements IMessageProducer {

    @Setter
	private KafkaTemplate<String, String> kafkaTemplate;

	@Setter
	private String topic;

    @Setter
    private boolean flush;




	@Override
	public void sendText(String message) throws JmsException {
		try {
			ProducerRecord<String, String> producerData 
				= new ProducerRecord<String, String>(topic, message);
            kafkaTemplate.send(producerData);
			if (flush){kafkaTemplate.flush();}
		} catch (Exception e) {
			throw new MQException(e);
		}
    }

    @Override
    public void sendText(String message, String key) throws JmsException {
        try {
            ProducerRecord<String, String> producerData
                    = new ProducerRecord<String, String>(topic,key,message);
            kafkaTemplate.send(producerData);
            if (flush){kafkaTemplate.flush();}
        } catch (Exception e) {
            throw new MQException(e);
        }
    }

    @Override
	public void sendText(List<String> message) throws JmsException {
		try {
			ProducerRecord<String,String> producerData = null;
			for (String string : message) {
				producerData = new ProducerRecord<String, String>(topic, string);
                kafkaTemplate.send(producerData);
			}
            if (flush){kafkaTemplate.flush();}
		}catch (Exception e) {
			throw new MQException(e);
		}
    }



    @Override
    public void sendText(List<String> message, String key) throws JmsException {
        try {
            ProducerRecord<String,String> producerData = null;
            for (String string : message) {
                producerData = new ProducerRecord<String, String>(topic, key,string);
                kafkaTemplate.send(producerData);
            }
            if (flush){kafkaTemplate.flush();}
        }catch (Exception e) {
            throw new MQException(e);
        }
    }


}