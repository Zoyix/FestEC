package com.dianbin.latte.ui.camera;

/**
 * Created by zhouyixin on 2018/1/7.
 */

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.FileUtils;
import com.dianbin.latte.R;
import com.dianbin.latte.delegates.PermissionCheckerDelegate;
import com.dianbin.latte.util.file.FileUtil;

import java.io.File;

/**
 * 照片处理类
 */
public class CameraHandler implements View.OnClickListener {
    private final AlertDialog DIALOG;
    private final PermissionCheckerDelegate DELEGATE;

    public CameraHandler(PermissionCheckerDelegate delegate) {
        DELEGATE = delegate;
        DIALOG = new AlertDialog.Builder(delegate.getContext()).create();
    }

    /**
     * 启动相机或者相册的选择框，并注册点击事件
     */
    void beginCameraDialog() {
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_camera_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            //让该window后所有的东西都成暗淡（dim）
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗。
            //如果要达到背景全部变暗的效果，需要设置dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            params.dimAmount = 0.5f;
            window.setAttributes(params);

            window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
        }
    }

    private String getPhoneName() {
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }

    /**
     * 打开相机
     */
    private void takePhoto() {
        final String currentPhoneName = getPhoneName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //传入目录和名字
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR, currentPhoneName);

        //兼容7.0及以上的写法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //TODO 以后解决：既然uri可以通过解析得到真实路径，为什么要多此一举，哪里安全了？
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getPath());
            //MediaStore.Images.Media.EXTERNAL_CONTENT_URI : 存储在外部存储器（SD卡）上的图片文件的内容
            //uri:content://media/internal/images/media/229
            //mUri:content://com.dianbin.latte.fireprovider/my_download/DCIM/Camera/IMG_20180108_062515.jpg
            //TODO 以后解决：上面分别是这两种方法得出来的uri，为什么他们都可以用？只要是content：头的都可以么？
            final Uri uri = DELEGATE.getContext().getContentResolver()
                    .insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, contentValues);
//            Uri mUri = FileProvider.getUriForFile(DELEGATE.getContext(),"com.dianbin.latte.fireprovider",tempFile);
            //TODO 可以优化
            //tempFile和realFile有什么区别? 没有区别
            //需要将Uri路径转化为真实路径
            final File realFile = FileUtils.getFileByPath(FileUtil.getRealFilePath(DELEGATE.getContext(), uri));
            final Uri realUri = Uri.fromFile(realFile);
            CameraImageBean.getInstance().setPath(realUri);
            //为什么是uri？不是realUri？ 因为直接使用本地真实路径的Uri被认为是不安全。
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }
        DELEGATE.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }

    /**
     * 打开相册
     */
    private void pickPhoto() {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //Intent.createChooser是每次都要选择不同的activity来处理
        DELEGATE.startActivityForResult(Intent.createChooser(intent, "选择获取图片的方式"), RequestCodes.PICK_PHOTO);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.photodialog_btn_cancel) {

            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_take) {
            takePhoto();
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_native) {
            pickPhoto();
            DIALOG.cancel();
        }

    }
}
