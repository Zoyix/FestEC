package com.dianbin.latte.util.callback;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2018/1/8.
 */


public interface IGlobalCallback<T> {

    void executeCallback(@NonNull T args);

}
