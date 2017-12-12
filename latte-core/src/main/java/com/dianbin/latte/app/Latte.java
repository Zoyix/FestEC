package com.dianbin.latte.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/13.
 */

public final class Latte {
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigKeys.APPLICATION_CONFEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    //TODO 应该是已经被废弃
    public static HashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }


    public static Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONFEXT);
    }

}
