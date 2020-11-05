package com.platform.hbase.config;

import com.platform.hbase.api.HbaseAsyncTemplate;
import com.platform.hbase.api.HbaseTemplate;
import com.platform.hbase.properties.HbaseProperties;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author wlhbdp
 * @ClassName: HbaseAutoConfiguration
 * @Description: HbaseAutoConfiguration
 *
 */
@EnableConfigurationProperties(HbaseProperties.class)
@org.springframework.context.annotation.Configuration
public class HbaseAutoConfiguration {

    private static final String HBASE_ZK_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_ZK_PORT = "hbase.zookeeper.port";
    private static final String HBASE_CLIENT_POOL_TYPE = "hbase.client.ipc.pool.type";
    private static final String HBASE_CLIENT_POOL_SIZE = "hbase.client.ipc.pool.size";
    private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";


    @Autowired
    private HbaseProperties hbaseProperties;

    private Configuration getConfiguration(){
        Configuration configuration = HBaseConfiguration.create();
        configuration.set(HBASE_ZK_QUORUM, this.hbaseProperties.getZookeeper().getQuorum());
        configuration.set(HBASE_ZK_PORT, hbaseProperties.getZookeeper().getPort());
        configuration.set(HBASE_CLIENT_POOL_TYPE, hbaseProperties.getPool().getType());
        configuration.set(HBASE_CLIENT_POOL_SIZE, hbaseProperties.getPool().getSize());
        configuration.set(HBASE_ZNODE_PARENT,hbaseProperties.getZnodeParent());
        return configuration;
    }

    @Bean
    @ConditionalOnMissingBean(HbaseTemplate.class)
    public HbaseTemplate hbaseTemplate() {
        return new HbaseTemplate(getConfiguration());
    }


    @Bean
    @ConditionalOnMissingBean(HbaseAsyncTemplate.class)
    public HbaseAsyncTemplate hbaseAsyncTemplate(){
        return new HbaseAsyncTemplate(getConfiguration());
    }

}
