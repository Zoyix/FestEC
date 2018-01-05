package com.dianbin.latte.ec.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.dianbin.latte.delegates.LatteDelegate;
import com.dianbin.latte.ec.R;
import com.dianbin.latte.net.RestClient;
import com.dianbin.latte.net.callBack.ISuccess;
import com.dianbin.latte.util.log.LatteLogger;

/**
 * Created by Administrator on 2018/1/4.
 */

public class FastPay implements View.OnClickListener {

    //设置支付回调监听
    private IAlpayResultListener mIAlpayResultListener;
    private Activity mActivity;

    private AlertDialog mDialog = null;
    private int mOrderID = -1;

    private FastPay(LatteDelegate delegate) {
        this.mActivity = delegate.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    public static FastPay create(LatteDelegate delegate) {
        return new FastPay(delegate);
    }

    /**
     * 选择支付方式的对话框
     */
    public void beginPayDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_pay_panel);
            //TODO 改成TOP,把动画放慢10倍，发现上来还是从底部上来的，消失时没回到底部，所以100%p中的父布局到底指什么？13-8 12：00
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_dialog_pay_alpay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
        }
    }

    public FastPay setPayResultListener(IAlpayResultListener listener) {
        this.mIAlpayResultListener = listener;
        return this;
    }

    public FastPay setOrderId(int orderId){
        this.mOrderID = orderId;
        return this;
    }

    public final void alpay(int orderId) {
        final String singUrl = "http://app.api.zanzuanshi.com/api/v1/alipay/a=" + orderId;
        //向服务器获取签名字符串
        RestClient.builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String paySign = JSON.parseObject(response).getString("result");
                        LatteLogger.d("PAY_SIGN", paySign);
                        //必须是异步调用客户端支付接口
                        final PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity, mIAlpayResultListener);
                        //TODO 这种方式和execute区别
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign);

                    }
                })
                .build()
                .post();//TODO 这里为什么用post

    }

    @Override
    public void onClick(View v) {
        //TODO 为什么liblers不能用switch？没有常量这一说？
        int id = v.getId();
        if (id == R.id.btn_dialog_pay_alpay) {
            alpay(mOrderID);
        } else if (id == R.id.btn_dialog_pay_wechat) {
            mDialog.cancel();
        } else if (id == R.id.btn_dialog_pay_cancel) {
            mDialog.cancel();
        }

    }
}
