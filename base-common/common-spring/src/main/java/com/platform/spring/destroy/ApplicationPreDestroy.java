package com.platform.spring.destroy;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author wulinhao
 * @ClassName: ApplicationPreDestroy
 * @Description: 应用退出调用方法
 * @date 2020/11/285:11 PM
 *
 */

@Component
@Log4j2
public class ApplicationPreDestroy {


    @PreDestroy
    public void destroy(){
        log.info("application pre destroy...");
        LogManager.shutdown();
    }
}
