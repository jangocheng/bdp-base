package com.platform.spring.destroy;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;



@Component
@Log4j2
public class ApplicationPreDestroy {


    @PreDestroy
    public void destroy(){
        log.info("application pre destroy...");
        LogManager.shutdown();
    }
}
