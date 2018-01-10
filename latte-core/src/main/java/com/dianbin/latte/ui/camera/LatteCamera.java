package com.dianbin.latte.ui.camera;

/**
 * Created by zhouyixin on 2018/1/7.
 */

import android.net.Uri;

import com.dianbin.latte.delegates.PermissionCheckerDelegate;
import com.dianbin.latte.util.file.FileUtil;

/**
 * 照相机调用类
 */
public class LatteCamera {
    /**
     * 创建剪裁后图片存放的uri
     * @return
     */
    public static Uri createCropFile() {
        return Uri.parse(FileUtil.createFile("crop_image",
                FileUtil.getFileNameByTime("IMG", "Jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }

}
