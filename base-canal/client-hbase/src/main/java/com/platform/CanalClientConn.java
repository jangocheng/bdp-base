package com.platform;

import com.platform.client.FactoryCanalClient;
import com.platform.hbase.api.HbaseTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.Resource;

/**
 * @author wulinhao
 * @ClassName: CanalClientFoxConn
 * @Description: TODO
 * @date 2020-03-0615:50
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@Log4j2
public class CanalClientFoxConn implements CommandLineRunner {

    @Resource
    private HbaseTemplate hbaseTemplate;

    @Resource
    private FactoryCanalClient factoryCanalClient;


    public static void main(String[] args) {
        SpringApplication.run(CanalClientFoxConn.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("start ");
        factoryCanalClient.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                log.info("stop the canal client");
                factoryCanalClient.stop();
            } catch (Throwable e) {
                log.warn("something goes wrong when stopping canal:", e);
            } finally {
                log.info("canal client is down.");
            }
        }));
    }
}
