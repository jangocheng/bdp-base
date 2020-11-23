package com.platform.config;

import com.platform.common.util.ModelHolder;
import com.platform.service.ModelSelectionService;
import lombok.extern.slf4j.Slf4j;
import org.dmg.pmml.PMML;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 模型初始化，app启动时自动从DB加载模型
 * author: wlhbdp
 * create: 2020-05-20 16:19
 */
@Component
@Slf4j
public class ModelInitialization implements CommandLineRunner {
    @Resource
    private ModelSelectionService modelSelectionService;

    /**
     * 从数据加载模型，初始化模型
     * @param args params
     * @throws Exception Exception
     */
    @Override
    public void run(String... args) throws Exception {

        try {
            log.info("Begin to initialize model...");
            Map<String, Object> models = modelSelectionService.loadAllModelsFromDB();
            ModelHolder.setModel(models);
            String msg = "Load " + ModelHolder.getModelSize() +
                    " models totally. Model initialization end.";
            log.info(msg);
        } catch (Exception e) {
            log.error("Load model from database failure", e);
            throw e;
        }
    }
}
