package com.dianbin.latte.delegates.web.client;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dianbin.latte.delegates.web.WebDelegate;
import com.dianbin.latte.delegates.web.route.Router;
import com.dianbin.latte.util.log.LatteLogger;

/**
 * Created by Administrator on 2017/12/26.
 */

//TODO 这个类干嘛的？
public class WebViewClientImpl extends WebViewClient {
    private final WebDelegate DELEGATE;

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    //TODO 这个方法什么时候用？
    /**
     * @return 如果是false 就由webView接管事件，为true 由原生接管事件
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(DELEGATE,url);
    }
}
