package com.dianbin.latte.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.dianbin.latte.delegates.web.event.Event;
import com.dianbin.latte.delegates.web.event.EventManage;

/**
 * Created by Administrator on 2017/12/26.
 */

/**
 *  被js调用的类
 */
//TODO 12-4 11:20 为什么通过操作去掉public？不直接删掉？？
final class LatteWebInterface {
    private final WebDelegate DELEGATE;

    public LatteWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    static LatteWebInterface create(WebDelegate delegate) {
        return new LatteWebInterface(delegate);
    }

    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params) {
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManage.getInstance().createEvent(action);
        //TODO 如果没有那返回来的是UndefineEvent，设置下面的一些有什么用？
        if (event != null) {
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
