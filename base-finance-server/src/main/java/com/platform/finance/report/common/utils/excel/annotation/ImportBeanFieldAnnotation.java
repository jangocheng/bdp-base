package com.platform.finance.report.common.utils.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportBeanFieldAnnotation {
    int sort();  //标注属性的顺序
    String title();  //标题
}
