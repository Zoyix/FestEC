package com.dianbin.latte.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.dianbin.latte.delegates.LatteDelegate;
import com.dianbin.latte.delegates.web.WebDelegate;
import com.dianbin.latte.delegates.web.WebDelegateImpl;

/**
 * Created by Administrator on 2017/12/26.
 */

/**
 * 路由处理类
 */
public class Router {
    private Router() {

    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 每次有新的跳转时被调用
     * @return 返回true 表示原生接管了
     */
    public final boolean handleWebUrl(WebDelegate delegate, String url) {
        //如果是电话协议
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }

        //获取到最底层的Fragment即BaseBottomDelegate，进行跳转。所谓的原生接管即原生获取到请求后进行一定的处理后，新创建个WebDelegateImpl去取代原先的那个
        final LatteDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.getSupportDelegate().start(webDelegate);

        return true;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView is null!");
        }
    }

    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    /**
     * 让WebDelegate(fragment)里的WebView加载页面的方法
     * @param delegate
     * @param url
     */
    public final void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }

    private void callPhone(Context context, String url) {
        //不是直接拨打的，提醒用户的方式
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(url);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);
    }
}
