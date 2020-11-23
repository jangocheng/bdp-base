package com.platform.finance.report.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wlhbdp
 * @date 2020/9/4 23:57
 * @description 文件名注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileNameAnnotation {

    String value();
}
