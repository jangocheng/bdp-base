package com.platform.finance.report;

import com.platform.finance.report.common.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wlhbdp
 * @date 2020/9/25 11:37
 * @description
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${spring.profiles.active:}")
    private String active;

    @Bean
    public Docket createRestApi() {
        if (CommonConstants.PORD.equals(active)) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.platform.finance.report.web.controller"))
                    .paths(PathSelectors.none())
                    .build();
        } else {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.platform.finance.report.web.controller"))
                    .paths(PathSelectors.regex("(?!/health).+"))
                    .build()
                    .apiInfo(apiInfo());
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("FINANCE数据接口")
                .description("API说明文档")
                .version("1.0")
                .build();
    }
}
