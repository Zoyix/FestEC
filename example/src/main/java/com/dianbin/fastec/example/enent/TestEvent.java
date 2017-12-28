package com.dianbin.fastec.example.enent;

import android.webkit.WebView;
import android.widget.Toast;

import com.dianbin.latte.delegates.web.event.Event;

/**
 * Created by Administrator on 2017/12/27.
 */

public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), getAction(), Toast.LENGTH_LONG).show();
        if (getAction().equals("test")) {
            //TODO 为什么能保证代码运行在主线程里了？
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript("nativeCall();",null);
                }
            });
        }
        return null;
    }
}
