package com.dianbin.festec.example;

import android.app.Application;

import com.dianbin.latte.app.Latte;
import com.dianbin.latte.ec.icon.FontEcModule;
import com.dianbin.latte.net.interceptors.DebugIntercepter;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
//                .withInterceptor()
                .withApiHost("http://127.0.0.1/")
                .withInterceptor(new DebugIntercepter("index", R.raw.test))
                .configure();
    }
}
