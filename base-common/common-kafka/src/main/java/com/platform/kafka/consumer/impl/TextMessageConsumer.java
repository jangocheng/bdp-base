package com.platform.kafka.consumer.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import com.alibaba.fastjson.JSONObject;
import com.platform.kafka.consumer.IMessageConusmer;


public abstract class TextMessageConsumer extends IMessageConusmer {

	/**
	 * 数据处理
	 * @param dataTypeListMap
	 */
	@Override
	public abstract void processMessage(Map<String, List<String>> dataTypeListMap);


	/**
	 * 不同数据类型，存储到不同集合中
	 * @param dataTypeListMap
	 * @param message
	 */
	public void processMessageToList(Map<String, List<String>> dataTypeListMap, String message) {
		JSONObject obj = JSONObject.parseObject(message.trim());
		String dataType = obj.getString("dataType");
		if (dataTypeListMap.containsKey(dataType)) {
			List<String> messageList = dataTypeListMap.get(dataType);
			messageList.add(message);
		} else {
			List<String> messageList = new ArrayList<String>();
			messageList.add(message);
			dataTypeListMap.put(dataType, messageList);
		}
	}


}
