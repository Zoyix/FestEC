package com.dianbin.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.dianbin.latte.delegates.LatteDelegate;
import com.dianbin.latte.ec.R;
import com.dianbin.latte.ec.R2;
import com.dianbin.latte.net.RestClient;
import com.dianbin.latte.net.callBack.ISuccess;
import com.dianbin.latte.util.log.LatteLogger;
import com.dianbin.latte.wechat.LatteWeChat;
import com.dianbin.latte.wechat.callBacks.IWeChatSignInCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SignInDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkFrom()) {
            RestClient.builder()
                    .url("http://10.41.69.60:8080/RestServer/api/user_profile.php")
                    .params("email", mEmail.toString())
                    .params("password", mPassword.toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            SignHandler.onSignIn(response,mISignListener);
                        }
                    })
                    .build()
                    .post();
        }
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat() {
        LatteWeChat.getInstance().onSignSuccess(new IWeChatSignInCallback() {
            @Override
            public void onSignInSuccess(String userInfo) {
                Toast.makeText(getContext(), userInfo, Toast.LENGTH_LONG).show();
            }
        }).signIn();
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    private boolean checkFrom() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
