package com.dianbin.latte.ui.camera;

/**
 * Created by zhouyixin on 2018/1/7.
 */

import android.net.Uri;


/**
 * 存储一些中间值
 */
public final class CameraImageBean {
    private Uri mPath = null;

    //TODO 程哥，和静态内部类有什么不同
//    private static class Holder {
//        private static final CameraImageBean INSTANCE = new CameraImageBean();
//    }
    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance() {
//        return Holder.INSTANCE;
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri path) {
        this.mPath = path;
    }
}
