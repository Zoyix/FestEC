package com.dianbin.latte.ui.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dianbin.latte.delegates.LatteDelegate;
import com.dianbin.latte.util.callback.CallbackManager;
import com.dianbin.latte.util.callback.CallbackType;
import com.dianbin.latte.util.callback.IGlobalCallback;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by Administrator on 2018/1/15.
 */

/**
 * 二维码扫描页
 */
public class ScannerDelegate extends LatteDelegate implements ZBarScannerView.ResultHandler {

    private ScanView mScanView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScanView == null){
            mScanView = new ScanView(getContext());
        }
        //自动对焦
        mScanView.setAutoFocus(true);
        //设置回调监听
        mScanView.setResultHandler(this);

    }

    @Override
    public Object setLayout() {
        return mScanView;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScanView!=null){
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScanView!=null){
            //先停止预览
            mScanView.stopCameraPreview();
            mScanView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        //TODO 以后解决 16-1有个setFragmentResult()，干什么的？视频里开始为啥要那样
        @SuppressWarnings("unchecked")
        final IGlobalCallback<String> callback = CallbackManager
                .getInstance()
                .getCallback(CallbackType.ON_SCAN);
        if (callback != null){
            callback.executeCallback(result.getContents());
        }
        //出栈
        getSupportDelegate().pop();
    }
}
