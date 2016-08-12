package com.af.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by deng on 16-7-12.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Task {
    /**redis key*/
    public String redisKey();
    /**key超时时间， 单位s*/
    public long expireSecondTime();

}
