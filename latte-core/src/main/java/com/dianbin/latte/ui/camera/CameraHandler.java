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

    void beginCameraDialog() {
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_camera_panel);
            //TODO 改成TOP,把动画放慢10倍，发现上来还是从底部上来的，消失时没回到底部，所以100%p中的父布局到底指什么？13-8 12：00
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

    private void takePhoto() {
        final String currentPhoneName = getPhoneName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //传入目录和名字
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR, currentPhoneName);

        //兼容7.0及以上的写法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //TODO 不是说从android 7.0开始，直接使用本地真实路径的Uri被认为是不安全的么？这种方式出来的uri也是封装过的？
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getPath());
            //MediaStore.Images.Media.EXTERNAL_CONTENT_URI : 存储在外部存储器（SD卡）上的图片文件的内容
            //TODO 为什么要往内容提供器里加？
            final Uri uri = DELEGATE.getContext().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            //TODO 程哥，真实路径一直有个疑问，难道目录加名字不是真实路径？和tempFile.getPath()有什么不同？
            //TODO tempFile和realUri有什么区别
            //需要将Uri路径转化为真实路径
            final File realFile = FileUtils.getFileByPath(FileUtil.getRealFilePath(DELEGATE.getContext(), uri));
            final Uri realUri = Uri.fromFile(realFile);
            CameraImageBean.getInstance().setPath(realUri);
            //TODO 为什么是uri？不是realUri？
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }
        DELEGATE.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }

    private void pickPhoto() {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        DELEGATE.startActivityForResult(Intent.createChooser(intent, "选择获取图片的方式"), RequestCodes.PICK_PHOTO);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.photodialog_btn_cancel) {
            pickPhoto();
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_take) {
            takePhoto();
            DIALOG.cancel();
        } else if (id == R.id.photodialog_btn_native) {
            DIALOG.cancel();
        }

    }
}
