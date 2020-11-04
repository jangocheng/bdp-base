package com.platform.kafka.producer;

import java.util.List;

import com.platform.spring.exception.JmsException;


/**
 * 队列消息发送接口
 * @author wulinhao
 *
 */
public interface IMessageProducer {


	/**
	 * 发送字符串
	 * @param message
	 */
	public void sendText(String message) throws JmsException;


    /**
	 * 发送字符串
	 * @param message value
	 * @param key key
	 */
	public void sendText(String message, String key) throws JmsException;


    /**
	 * 发送字符串集合
	 * @param message
	 * @throws JmsException
	 */
	public void sendText(List<String> message) throws JmsException;



    /**
	 * 发送字符串集合
	 * @param message
	 * @param key key
	 * @throws JmsException
	 */
	public void sendText(List<String> message, String key) throws JmsException;

}