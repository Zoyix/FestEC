package com.dianbin.latte.ec.pay;

/**
 * Created by Administrator on 2018/1/4.
 */

/**
 * 支付结果的回调
 */
public interface IAlpayResultListener {
    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
