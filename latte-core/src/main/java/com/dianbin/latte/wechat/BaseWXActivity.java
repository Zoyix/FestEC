package com.dianbin.latte.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by zhouyixin on 2017/12/9.
 */

public abstract class BaseWXActivity extends AppCompatActivity implements IWXAPIEventHandler {
   //TODO 跟public 的有什么区别
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这个必须写在onCreate
        LatteWeChat.getInstance().getWXAPI().handleIntent(getIntent(),this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); //TODO 这个是啥？这个类是啥？为什么再写一遍能适配机型？
        LatteWeChat.getInstance().getWXAPI().handleIntent(getIntent(),this);
    }
}
