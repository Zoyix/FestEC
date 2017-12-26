package com.dianbin.latte.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dianbin.latte.delegates.web.route.RouteKeys;

/**
 * Created by Administrator on 2017/12/26.
 */

public class WebDelegateImpl extends WebDelegate {

    public static WebDelegateImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getUrl()!=null){
            //用原生的方式模拟Web跳转
        }
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return null;
    }
}
