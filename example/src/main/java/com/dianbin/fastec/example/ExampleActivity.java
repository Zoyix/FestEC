package com.dianbin.fastec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.dianbin.latte.activities.ProxyActivity;
import com.dianbin.latte.app.Latte;
import com.dianbin.latte.delegates.LatteDelegate;
import com.dianbin.latte.ec.launcher.LauncherDelegate;
import com.dianbin.latte.ec.launcher.LauncherScrollDelegate;
import com.dianbin.latte.ec.main.EcBottomDelegate;
import com.dianbin.latte.ec.sign.ISignListener;
import com.dianbin.latte.ec.sign.SignInDelegate;
import com.dianbin.latte.ui.launcher.ILauncherListener;
import com.dianbin.latte.ui.launcher.OnLauncherFinishTag;
import com.dianbin.latte.R;

import qiu.niorgai.StatusBarCompat;

//TODO okHttp是不是本身就有这样的机制，网络请求在新线程里做，返回的回调方法在主线程里做？
// 如果他本身就是这样的机制，那rxJava岂不是作用不大？？

public class ExampleActivity extends ProxyActivity implements ISignListener, ILauncherListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Latte.getConfigurator().withActivity(this);
        //让状态栏隐藏
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        //TODO 为什么要用Activity的start启动？
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
//                Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show();
                //startWithPop是在start的同时，把上一个元素彻底清除掉
                //TODO 之前好像也没清除掉
//                getSupportDelegate().startWithPop(new EcBottomDelegate());
                getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
//                Toast.makeText(this, "启动结束，没登录", Toast.LENGTH_LONG).show();
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }

    }
}

