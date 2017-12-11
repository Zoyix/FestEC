package com.dianbin.festec.example.generators;

import com.dianbin.latte.annotations.EntryGenerator;
import com.dianbin.latte.wechat.templates.WXEntryTemplate;

/**
 * Created by Administrator on 2017/12/8.
 */

@EntryGenerator(
        packageName = "com.dianbin.festec.example",
        entryTemplete = WXEntryTemplate.class
)
public interface WeChatEntry {
}
