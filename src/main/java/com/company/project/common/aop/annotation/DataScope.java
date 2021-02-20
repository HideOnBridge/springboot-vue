package com.company.project.common.aop.annotation;

import java.lang.annotation.*;

/**
 * LogAnnotation
 * @author mc
 * @version V1.0
 * @date 2021/2/18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
}
