package com.egg.ih.log.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {
    /**
     * 方法所在的服务
     * @return
     */
    String service();

    /**
     * 方法所在的模块
     * @return
     */
    String module() default "";

    /**
     * 方法描述
     * @return
     */
    String description() default "";
}
