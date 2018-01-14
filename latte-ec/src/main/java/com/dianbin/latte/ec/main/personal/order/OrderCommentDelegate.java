package com.dianbin.latte.ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.dianbin.latte.delegates.LatteDelegate;
import com.dianbin.latte.ec.R;
import com.dianbin.latte.ec.R2;
import com.dianbin.latte.ui.widget.AutoPhotoLayout;
import com.dianbin.latte.ui.widget.StarLayout;
import com.dianbin.latte.util.callback.CallbackManager;
import com.dianbin.latte.util.callback.CallbackType;
import com.dianbin.latte.util.callback.IGlobalCallback;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/10.
 */


/**
 * 星级评价Fragment
 */
public class OrderCommentDelegate extends LatteDelegate {
    @BindView(R2.id.custom_star_layout)
    StarLayout mStarLayout = null;
    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;

    @OnClick(R2.id.top_tv_comment_commit)
    void onClickSubmit() {
        Toast.makeText(_mActivity, "评分：" + mStarLayout.getStarCount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mAutoPhotoLayout.setDelegate(this);
        //之前在设置头像添加的回调用的是同一个tag，不会冲突么？
        // Map集合中的键对象不允许重复,也就是说会覆盖原先有的
        final IGlobalCallback<Uri> callback = CallbackManager.getInstance().getCallback(CallbackType.ON_CROP);

        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@NonNull Uri args) {
                        mAutoPhotoLayout.onCropTarget(args);
                    }
                });
    }
}
