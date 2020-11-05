package com.platform.canal;

import com.platform.canal.client.BusinessCanalClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.Resource;

/**
 * @author wlhbdp
 * @ClassName: BusinessCanalClientApplication
 * @Description: 业务数据源mysql canal采集
 *
 */
@Log4j2
@SpringBootApplication
@EnableDiscoveryClient
public class BusinessCanalClientApplication implements CommandLineRunner
{

    public static void main(String[] args) {
        SpringApplication.run(BusinessCanalClientApplication.class, args);
    }

    @Resource
    private BusinessCanalClient businessCanalClient;

    @Override
    public void run(String... args) {
        log.info("start ...");
        businessCanalClient.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                log.info("stop the canal client...");
                businessCanalClient.stop();
            } catch (Throwable e) {
                log.warn("something goes wrong when stopping canal:", e);
            } finally {
                log.info("canal client is down...");
            }
        }));
    }

}
