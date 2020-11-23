package com.platform.base.adapter.service.impl;

import cn.snowheart.dingtalk.robot.starter.client.DingTalkRobotClient;
import com.platform.base.adapter.service.DingTalkRobotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * author: wlhbdp
 * create: 2020-07-03 11:42
 */
@Service
@Slf4j
public class DingTalkRobotServiceImpl implements DingTalkRobotService {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.profiles.active}")
    private String activeEnv;

    @Resource
    @Qualifier("dingTalkRobotClient")
    private DingTalkRobotClient client;

    @Override
    public void sendWarningMessage(String msg) {
        String projectName = appName;
        String env = activeEnv;
        String ip = null;
        InetAddress localHost;
        try {
            localHost = Inet4Address.getLocalHost();
            ip = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(),e);
        }
        String content = "### 应用服务告警" +
                "\n- **服务**：" + projectName +
                "\n- **环境**：" + env +
                "\n- **IP** : " + ip +
                "\n- **消息**：" + msg;
        client.sendMarkdownMessage("告警信息", content, false);
    }
}
