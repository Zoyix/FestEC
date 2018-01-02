package com.dianbin.latte.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dianbin.latte.app.ConfigKeys;
import com.dianbin.latte.app.Latte;
import com.dianbin.latte.delegates.IPageLoadListener;
import com.dianbin.latte.delegates.web.WebDelegate;
import com.dianbin.latte.delegates.web.route.Router;
import com.dianbin.latte.ui.loader.LatteLoader;
import com.dianbin.latte.util.log.LatteLogger;
import com.dianbin.latte.util.storage.LattePreference;


/**
 * Created by Administrator on 2017/12/26.
 */

/**
 * 用来原生和webView交接的，使原生的一些方法可以干预webView
 */
public class WebViewClientImpl extends WebViewClient {
    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;
    //TODO 看看Handle？
    private static final Handler HANDLER = Latte.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    /**
     * 每次有新的跳转时调用
     *
     * @return 如果是false 就由webView接管事件，为true 由原生接管事件
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    /**
     * 请求开始前调用
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    //TODO 为什么拦截器里每次API请求添加的cookie要以webView里的为准，即为什么要从webView里取？比如用户的登录信息，就不能自己组个cookie然后加到API请求中么？为什么一定要从webView里取？
    //获取浏览器cookie
    private void syncCookie() {
        final CookieManager manager = CookieManager.getInstance();

        //TODO 既然放在onPageFinished方法里，为什么是网页的WEB_HOST而不是每次web请求的url
        /**
         * 注意，这里的Cookie和API请求的Cookie是不一样的，这个在网页不可见
         */
        final String webHost = Latte.getConfiguration(ConfigKeys.WEB_HOST);
        if (webHost != null) {
            if (manager.hasCookies()) {
                final String cookieStr = manager.getCookie(webHost);
                if (cookieStr!=null&&!cookieStr.equals("")){
                    LattePreference.addCustomAppProfile("cookie",cookieStr);
                }
            }
        }
    }

    /**
     * 请求结束后调用
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        syncCookie();
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();
            }
        }, 1000);
    }
}
