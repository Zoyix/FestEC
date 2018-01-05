package com.dianbin.latte.ec.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.dianbin.latte.ui.loader.LatteLoader;
import com.dianbin.latte.util.log.LatteLogger;

import java.util.Map;

/**
 * Created by zhouyixin on 2018/1/5.
 */
//TODO 拜读徐一深大神的AsyncTask 慕课网
//TODO 用Rxjava改造
public class PayAsyncTask extends AsyncTask<String, Void, Map<String, String>> {
    private final Activity ACTIVITY;
    private final IAlpayResultListener LISTENER;

    //订单支付成功
    private static final String AL_PAY_STATUS_SUCCESS = "9000";
    //订单处理中
    private static final String AL_PAY_STATUS_PAYING = "8000";
    //订单支付失败
    private static final String AL_PAY_STATUS_FAIL = "4000";
    //用户取消
    private static final String AL_PAY_STATUS_CANCEL = "6001";
    //订单网络错误
    private static final String AL_PAY_STATUS_CONNECT_ERROR = "6002";


    public PayAsyncTask(Activity activity, IAlpayResultListener listener) {
        this.ACTIVITY = activity;
        this.LISTENER = listener;
    }

    @Override
    protected Map<String, String> doInBackground(String... params) {
        final String alPaySign = params[0];
        final PayTask payTask = new PayTask(ACTIVITY);
        return payTask.payV2(alPaySign, true);

    }

    @Override
    protected void onPostExecute(Map<String, String> result) {
        super.onPostExecute(result);
        //TODO 这里为什么要停止？本来就没有开启啊
        LatteLoader.stopLoading();
        final PayResult payResult = new PayResult(result);
        //支付宝返回此次支付结构及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();
        LatteLogger.d("AL_PAY_RESULT", resultInfo);
        LatteLogger.d("AL_PAY_RESULT", resultStatus);

        switch (resultStatus) {
            case AL_PAY_STATUS_SUCCESS:
                if (LISTENER!=null){
                    LISTENER.onPaySuccess();
                }
                break;
            case AL_PAY_STATUS_FAIL:
                if (LISTENER!=null){
                    LISTENER.onPayFail();
                }
                break;
            case AL_PAY_STATUS_PAYING:
                if (LISTENER!=null){
                    LISTENER.onPaying();
                }
                break;
            case AL_PAY_STATUS_CANCEL:
                if (LISTENER!=null){
                    LISTENER.onPayFail();
                }
                break;
            case AL_PAY_STATUS_CONNECT_ERROR:
                if (LISTENER!=null){
                    LISTENER.onPayConnectError();
                }
                break;
            default:
                break;
        }

    }
}
