package com.dianbin.latte.net.rx;

import com.dianbin.latte.util.storage.LattePreference;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/29.
 */

public class AddCookieInterceptor implements Interceptor {

    @Override
    public Response  intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();
        //TODO 这里不需要改变线程，为什么要用Rxjava2？
        //使用just( )，将创建一个Observable并自动调用onNext( )发射数据：
        Observable
                .just(LattePreference.getCustomAppProfile("cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String cookie) throws Exception {
                        //给原生API请求附带上WebView拦截下来的Cookie
                        builder.addHeader("Cookie",cookie);
                    }
                });

        return chain.proceed(builder.build());
    }
}
