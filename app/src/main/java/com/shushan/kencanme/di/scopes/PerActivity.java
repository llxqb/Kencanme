package com.shushan.kencanme.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by niuxiaowei on 16/3/22.
 * Scope      在PerActivity中    都有必要用自定义的Scope注解标注这些Component
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
