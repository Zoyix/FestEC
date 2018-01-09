package com.dianbin.fastec.example;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.dianbin.latte.app.Latte;
import com.dianbin.fastec.example.enent.TestEvent;
import com.dianbin.latte.ec.database.DatabaseManager;
import com.dianbin.latte.ec.icon.FontEcModule;
import com.dianbin.latte.net.interceptors.DebugIntercepter;
import com.dianbin.latte.net.rx.AddCookieInterceptor;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //TODO 以后解决，问老师，程哥，可以优化，试试, 有啥用, 可以解决，有时间看,


        //我的电脑无线Ip：http://192.168.1.6:8080/RestServer/api/
        //公司ip: http://10.41.69.60:8080/RestServer/api/
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://10.41.69.60:8080/RestServer/api/")
                .withInterceptor(new DebugIntercepter("text", R.raw.test))
                .withWeChatAppId("wxfcdcecd9df8e0faa")
                .withWeChatAppSecret("a0560f75335b06e3ebea70f29ff219bf")
                .withJavascriptInterface("latte")
                .withWebEvent("test",new TestEvent())
                //TODO 为什么加斜杠，它又不拼接
                .withWebHost("https://www.baidu.com/")
                //添加Cookie同步拦截器
                .withInterceptor(new AddCookieInterceptor())
                .configure();

        //AndroidUtilCode初始化
        Utils.init(this);

        initStetho();
        DatabaseManager.getInstance().init(this);

        //开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private  void  initStetho(){
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }
}
