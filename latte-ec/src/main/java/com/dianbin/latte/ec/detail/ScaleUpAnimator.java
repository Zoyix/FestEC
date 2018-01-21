package com.dianbin.latte.ec.detail;

/**
 * Created by zhouyixin on 2018/1/21.
 */

import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 这是加入购物车后，购物车角标的放大缩小的动画
 */
public class ScaleUpAnimator  extends BaseViewAnimator{
    @Override
    protected void prepare(View target) {
        //TODO 以后解决： 动画问题不会
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target,"scaleX",0.8f,1f,1.4f,1.2f,1),
                ObjectAnimator.ofFloat(target,"scaleY",0.8f,1f,1.4f,1.2f,1)
        );
    }
}
