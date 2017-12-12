package com.dianbin.latte.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/12/7.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EntryGenerator {
    //TODO 理解Android APT？？
    //TODO 8-1 8-2 都不太懂 理解注解开发
    //TODO 是当属性传入的为啥会就括号？（）
    String packageName();

    //TODO Class<?>是什么意思？"?"有代表啥？
    Class<?> entryTemplete();

}
