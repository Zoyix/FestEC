package com.dianbin.festec.example.generators;

import com.dianbin.latte.annotations.AppRegisterGenerator;
import com.dianbin.latte.wechat.templates.AppRegisterTemplate;

/**
 * Created by zhouyixin on 2017/12/9.
 */
@AppRegisterGenerator(
        packageName = "com.dianbin.festec.example",
        registerTemplete = AppRegisterTemplate.class
)
public interface AppRegister {
}
