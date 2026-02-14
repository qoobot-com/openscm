package com.qoobot.openscm.common.annotation;

import com.qoobot.openscm.common.constant.OperLogType;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /**
     * 模块名称
     */
    String module() default "";

    /**
     * 操作类型
     */
    OperLogType businessType() default OperLogType.OTHER;

    /**
     * 操作说明
     */
    String description() default "";

    /**
     * 是否保存请求参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应参数
     */
    boolean isSaveResponseData() default true;
}
