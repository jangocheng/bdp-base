package com.platform.config;

import com.platform.config.property.HadoopProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;

/**
 * author: wlhbdp
 * create: 2020-05-29 00:24
 */
@org.springframework.context.annotation.Configuration
@Slf4j
public class HDFSConfig {
    @Resource
    private HadoopProperties hadoopProperties;

    @Bean
    public FileSystem fileSystem() {
        FileSystem fileSystem = null;
        String fsUri = hadoopProperties.getFsUri();
        String user = hadoopProperties.getUser();
        try {
            Configuration configuration = new Configuration();
            fileSystem = FileSystem.get(URI.create(fsUri),configuration, user);
        } catch (IOException e) {
            log.error("Can't create FileSystem", e);
        } catch (InterruptedException e) {
            log.error("Can't create FileSystem, user:" + user, e);
        }
        return fileSystem;
    }
}
