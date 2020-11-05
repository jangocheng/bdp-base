package com.platform.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author wlhbdp
 * @ClassName: KafkaProperties
 * @Description: kafka参数
 *
 */

@ConfigurationProperties(prefix = "fox.kafka")
public class KafkaProperties {




    @Getter
    private  Bootstrap bootstrap = new Bootstrap();


    public static class Bootstrap{
        /**
         * 用于初始化时建立链接到kafka集群，以host:port形式，多个以逗号分隔host1:port1,host2:port2；
         */
        @Setter
        @Getter
        private String servers;

        /**
         * 连接类型
         */
        @Getter
        @Setter
        private String connectionType;

    }

    @Getter
    private final Producer producer = new Producer();
    @Getter
    private final Consumer consumer = new Consumer();


    public static class Producer{
        /**
         * 生产者需要server端在接收到消息后，进行反馈确认的尺度，
         * 主要用于消息的可靠性传输；acks=0表示生产者不需要来自server的确认；
         * acks=1表示server端将消息保存后即可发送ack，
         * 而不必等到其他follower角色的都收到了该消息；
         * acks=all(or acks=-1)意味着server端将等待所有的副本都被接收后才发送确认。
         */
        @Setter
        @Getter
        private String acks;

        /**
         * 生产者发送失败后，重试的次数
         */
        @Setter
        @Getter
        private  String retries;


        /**
         *
         * 默认情况下缓冲区的消息会被立即发送到服务端，
         * 即使缓冲区的空间并没有被用完。可以将该值设置为大于0的值，
         * 这样发送者将等待一段时间后，再向服务端发送请求，以实现每次请求可以尽可能多的发送批量消息。
         *
         */
        @Getter
        @Setter
        private String lingerMs;

        /**
         * 生产者缓冲区的大小，保存的是还未来得及发送到server端的消息，
         * 如果生产者的发送速度大于消息被提交到server端的速度，该缓冲区将被耗尽。
         */
        @Setter
        @Getter
        private String bufferMemory;


        /**
         * 说明了使用何种序列化方式将用户提供的key值序列化成字节
         */
        @Setter
        @Getter
        private String keySerializer;

        /**
         * 说明了使用何种序列化方式将用户提供的value值序列化成字节
         */
        @Setter
        @Getter
        private String valueSerializer;


        /**
         * 当多条消息发送到同一个partition时，该值控制生产者批量发送消息的大小，
         * 批量发送可以减少生产者到服务端的请求数，有助于提高客户端和服务端的性能。
         */
        @Setter
        @Getter
        private String batchSize;


        /**
         * 用户特定的字符串，用来在每次请求中帮助跟踪调用。
         * 它应该可以逻辑上确认产生这个请求的应用
         */
        @Setter
        @Getter
        private String clientId;

        /**
         * 是否开启幂等，默认false
         */
        @Setter
        @Getter
        private Boolean enableIdempotence;
    }


    public static class Consumer{

        /**
         * 消费组ID
         */
        @Getter
        @Setter
        private String groupId;

        /**
         * 自动提交offset的间隔毫秒数，默认5000。
         */
        @Getter
        @Setter
        private String autoCommitInterValMs;

        /**
         * consumer session过期时间
         */
        @Setter
        @Getter
        private String sessionTimeoutMs;

        /**
         * 当前consumer给出的offset不存在将执行的动作，
         * earliest:设置offset到0
         * latest:设置offset到最后
         */
        @Setter
        @Getter
        private String autoOffsetReset;

        /**
         * 自动提交offset
         */
        @Setter
        @Getter
        private boolean enableAutoCommit;

        /**
         * 限制每回返回的最大数据条数.
         */
        @Setter
        @Getter
        private Integer maxPollRecords;


        /**
         * 消费者每次poll的数据业务处理时间不能超过kafka的max.poll.interval.ms
         * 超过即触发rebalance，默认300000毫秒
         */
        @Getter
        @Setter
        private Integer maxPollIntervalMs;

        /**
         * key.serializer,value.serializer说明了使用何种序列化方式将用户提供的key和vaule值序列化成字节。
         */
        @Setter
        @Getter
        private String keySerializer;

        @Setter
        @Getter
        private String valueSerializer;


        /**
         * consumer 向 coordinator 发送心跳包的时间间隔，
         * 一般为session.timeout.ms的1/3
         */
        @Setter
        @Getter
        private Integer heartbeatIntervalMs;


        /**
         * 用户特定的字符串，用来在每次请求中帮助跟踪调用。
         * 它应该可以逻辑上确认产生这个请求的应用
         */
        @Setter
        @Getter
        private String clientId;


        /**
         * 是否开启批量监听
         */
        @Setter
        @Getter
        private Boolean batchListener;

        /**
         * 消费线程数量，低于等于topic分区数量
         */
        @Setter
        @Getter
        private Integer concurrency;


        /**
         * 消费者等待最大时间 默认5000L
         */
        @Setter
        @Getter
        private Long poolTimeOut;

    }


}
