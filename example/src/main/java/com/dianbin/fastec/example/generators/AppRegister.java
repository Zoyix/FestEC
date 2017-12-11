package com.dianbin.fastec.example.generators;

import com.dianbin.latte.annotations.AppRegisterGenerator;
import com.dianbin.latte.wechat.templates.AppRegisterTemplate;

/**
 * Created by Administrator on 2017/12/8.
 */
@AppRegisterGenerator(
        packageName = "com.dianbin.festec.example",
        registerTemplete = AppRegisterTemplate.class
)
public interface AppRegister {
}
