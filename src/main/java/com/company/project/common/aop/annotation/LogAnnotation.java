package com.company.project.common.aop.annotation;

import java.lang.annotation.*;

/**
* @author machao
* @version V1.1
* @date 2021/2/4
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */
    String action() default "";
}
