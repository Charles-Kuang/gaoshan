package com.gaoshan.aspect.auth;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lumberer
 * @date 2022-11-19
 * 权限授权认证标识注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
//@Documented //生成文档
//@Mapping
public @interface LoginAuth {
    /**
     * 登录授权开关
     * @return
     */
    boolean flag() default true;
    /**
     * 需要的权限角色等级
     * 默认全部角色都能进行访问
     */
    boolean roleFlag() default false;
    /**
     * 描述
     * @return
     */
    String remark() default "";
}
