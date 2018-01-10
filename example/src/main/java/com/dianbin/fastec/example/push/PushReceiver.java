package com.dianbin.fastec.example.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.dianbin.fastec.example.ExampleActivity;
import com.dianbin.latte.util.log.LatteLogger;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/1/9.
 */

/**
 * 极光推送广播接收器
 */
public class PushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        //TODO 学习：这种方式值得学习
        //TODO 有时间看：.keySet()用法
        final Set<String> keys = bundle.keySet();
        final JSONObject json = new JSONObject();
        for (String key : keys) {
            final Object val = bundle.get(key);
            json.put(key, val);
        }

        LatteLogger.json("PushReceiver", json.toJSONString());

        final String pushAction = intent.getAction();
        if (pushAction.equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
            //处理接收到的消息
            onReceivedMessage(bundle);
        } else if (pushAction.equals(JPushInterface.ACTION_NOTIFICATION_OPENED)) {
            //打开相应的Notification
            onOpenNotification(context,bundle);
        }
    }

    /**
     * 在应用内收到通知后调用的方法
     * @param bundle
     */
    private void onReceivedMessage(Bundle bundle) {
        final String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        final String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        final int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        final String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
    }

    /**
     * 点击通知后调用的方法
     * @param context
     * @param bundle
     */
    private void onOpenNotification(Context context, Bundle bundle) {
        final String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        final Bundle openActivityBundle = new Bundle();
        final Intent intent = new Intent(context, ExampleActivity.class);
        intent.putExtras(openActivityBundle);
        //TODO 以后解决：好好看看，感觉大有学问
        //如果一个外部的Activity Context调用startActivity方法，那么，Intent对象必须包含 FLAG_ACTIVITY_NEW_TASK标志，
        // 这是因为，待创建的Activity并没有从一个已经存在的Activity启动（任务栈中并没有此Activity），
        // 它并没有已经存在的任务，因此它需要被放置在自己独立的任务中（也就是在任务栈中新建一个任务）。
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ContextCompat.startActivity(context,intent,null);
    }

}
