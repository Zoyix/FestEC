package com.dianbin.latte_ui.camera;

/**
 * Created by zhouyixin on 2018/1/7.
 */

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.dianbin.latte.delegates.PermissionCheckerDelegate;
import com.dianbin.latte_ui.R;

/**
 * 照片处理类
 */
public class CameraHanlder {
    private final AlertDialog DIALOG;
    private final PermissionCheckerDelegate DELEGATE;

    public CameraHanlder(AlertDialog dialog, PermissionCheckerDelegate delegate) {
        DIALOG = dialog;
        DELEGATE = delegate;
    }

    final void beginCameraDialog(){
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window != null) {
//            window.setContentView(R.layout.dialog_pay_panel);
            //TODO 改成TOP,把动画放慢10倍，发现上来还是从底部上来的，消失时没回到底部，所以100%p中的父布局到底指什么？13-8 12：00
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

//            window.findViewById(R.id.btn_dialog_pay_alpay).setOnClickListener(this);
//            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
//            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
        }
    }

}
