package com.dianbin.latte.wechat.templates;

import com.dianbin.latte.wechat.BaseWXEntryActivity;
import com.dianbin.latte.wechat.LatteWeChat;

/**
 * Created by zhouyixin on 2017/12/9.
 */

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        //消失不要动画
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
