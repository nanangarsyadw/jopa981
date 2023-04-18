package com.zftlive.android.library.annotation;

/**
 * Created by zengfantian on 2017/8/23.
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 模板配置信息注解
 *
 * @author 曾繁添
 * @version 1.0
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Config {

    int layout();
}
