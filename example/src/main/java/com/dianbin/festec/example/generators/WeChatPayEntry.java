package com.dianbin.festec.example.generators;

import com.dianbin.latte.annotations.PayEntryGenerator;
import com.dianbin.latte.wechat.templates.WXPayEntryTemplate;

/**
 * Created by Administrator on 2017/12/8.
 */
@PayEntryGenerator(
        packageName = "com.dianbin.festec.example",
        payEntryTemplete = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
