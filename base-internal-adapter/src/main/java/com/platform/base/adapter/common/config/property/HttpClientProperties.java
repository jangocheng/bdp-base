package com.platform.base.adapter.common.config.property;

import lombok.Data;

/**
 * HttpClient连接池配置
 * author: wlhbdp
 * create: 2020-06-22 09:43
 */
@Data
public class HttpClientProperties {
    /**
     * 建立连接的超时时间
     */
    private int connectTimeout = 20000;
    /**
     * 连接不够用的等待时间
     */
    private int requestTimeout = 20000;
    /**
     * 每次请求等待返回的超时时间
     */
    private int socketTimeout = 30000;
    /**
     * 每个主机最大连接数
     */
    private int defaultMaxPerRoute = 100;
    /**
     * 最大连接数
     */
    private int maxTotalConnections = 300;
    /**
     * 默认连接保持活跃的时间
     */
    private int defaultKeepAliveTimeMillis = 20000;
    /**
     * 空闲连接生的存时间
     */
    private int closeIdleConnectionWaitTimeSecs = 30;
}
