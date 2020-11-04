package com.platform.handle.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.FlatMessage;
import com.platform.handle.IConsumeHandle;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class NoopHandleImpl implements IConsumeHandle {

	@Override
	public void handle(List<FlatMessage> messageList,String dateType) {
		log.error("No handle "+dateType+":" + JSON.toJSONString(messageList));
	}

}
