package com.platform.hbase.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wlhbdp
 * @ClassName: HbaseProperties
 * @Description: hbase相关配置文件
 *
 */
@ConfigurationProperties(prefix = "platform.hbase")
public class HbaseProperties {


    /**
     * zk根目录
     */
    @Getter
    @Setter
    private String znodeParent;

    @Getter
    private Zookeeper zookeeper = new Zookeeper();

    public static class Zookeeper{
        /**
         * zk集群
         */
        @Setter
        @Getter
        private String quorum;

        /**
         * zk端口号
         */
        @Setter
        @Getter
        private String port;

    }

    @Getter
    private Pool pool = new Pool();

    public static class Pool{
        /**
         * 连接资源池类型
         * Reusable,RoundRobin,ThreadLoca
         */
        @Setter
        @Getter
        private String type;

        /**
         * 连接池的大小
         *
         */
        @Setter
        @Getter
        private String size;

    }



}
