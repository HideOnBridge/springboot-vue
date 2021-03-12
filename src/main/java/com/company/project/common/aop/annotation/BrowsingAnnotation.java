package com.company.project.common.aop.annotation;

import java.lang.annotation.*;

/**
 * @author mc
 * @version V1.0
 * @date 2021/3/12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BrowsingAnnotation {
    /**
     * 标记user/file
     * @return
     */
    String sign() default "";
}
