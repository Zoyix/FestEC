package com.dianbin.latte.delegates.web;

import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by zhouyixin on 2017/12/26.
 */

public class WebViewInitializer {

    public WebView creatWebView(WebView webView){
        //设置可以调试，在4.9以上才行
        //TODO 为什么视频中不用 12-2 22分
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        //不能横向滚动
        webView.setHorizontalScrollBarEnabled(false);
        //不能纵向滚动
        //TODO 什么是纵向滚动？为什么不能？
        webView.setVerticalScrollBarEnabled(false);
        //允许截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        //初始化WebSettings
        final WebSettings settings = webView.getSettings();
        //TODO 这个是什么
        final String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua+"Latte");
        //隐藏缩放控件 TODO 什么是缩放控件？为什么隐藏？
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        return webView;
    }
}
