package com.dianbin.latte.net;

import android.content.Context;

import com.dianbin.latte.net.callBack.IError;
import com.dianbin.latte.net.callBack.IFailure;
import com.dianbin.latte.net.callBack.IRequest;
import com.dianbin.latte.net.callBack.ISuccess;
import com.dianbin.latte.net.callBack.RequestCallbacks;
import com.dianbin.latte.net.download.DownloadHandler;
import com.dianbin.latte.ui.loader.LatteLoader;
import com.dianbin.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 2017/11/14.
 */

public class RestClient {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;


    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      String downloadDir,
                      String extension,
                      String name,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      File file,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestServise servise = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                call = servise.get(URL, PARAMS);
                break;
            case POST:
                call = servise.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = servise.postRaw(URL, BODY);
                break;
            case PUT:
                call = servise.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = servise.putRaw(URL, BODY);
                break;
            case DELETE:
                call = servise.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                //为什么要重新取一次不用service
                call = RestCreator.getRestService().upload(URL, body);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback());
        }

    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR, LOADER_STYLE);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,FAILURE,ERROR)
                .handleDownload();
    }


}
