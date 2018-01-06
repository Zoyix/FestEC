package com.dianbin.latte.net.rx;

import android.content.Context;

import com.dianbin.latte.net.RestClient;
import com.dianbin.latte.net.RestCreator;
import com.dianbin.latte.net.callBack.IError;
import com.dianbin.latte.net.callBack.IFailure;
import com.dianbin.latte.net.callBack.IRequest;
import com.dianbin.latte.net.callBack.ISuccess;
import com.dianbin.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/11/15.
 */

public class RxRestClientBuilder {
    private String mUrl = null;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private RequestBody mIBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;

    public RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotateIndicator;
        return this;
    }


    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS, mIBody, mFile, mContext, mLoaderStyle);
    }


}
