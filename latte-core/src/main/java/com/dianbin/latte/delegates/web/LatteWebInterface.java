package com.dianbin.latte.delegates.web;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2017/12/26.
 */

public class LatteWebInterface {
    private final WebDelegate DELEGATE ;

    public LatteWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    static LatteWebInterface create(WebDelegate delegate){
        return new LatteWebInterface(delegate);
    }

    public String event(String params){
        final String action = JSON.parseObject(params).getString("action");
        return null;
    }
}
