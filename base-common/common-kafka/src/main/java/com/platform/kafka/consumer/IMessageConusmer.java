package com.platform.kafka.consumer;

import java.util.List;
import java.util.Map;

/**
 * @author wulinhao
 * 
 */
public abstract class IMessageConusmer {
	
	public abstract void processMessage(Map<String, List<String>> dataTypeListMap);

}
