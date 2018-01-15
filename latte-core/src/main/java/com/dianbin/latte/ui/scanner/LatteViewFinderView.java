package com.dianbin.latte.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * Created by Administrator on 2018/1/15.
 */

/**
 * 打开二维码后中间的扫描框
 */
public class LatteViewFinderView extends ViewFinderView {
    public LatteViewFinderView(Context context) {
        this(context,null);
    }

    public LatteViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        //TODO 试试false
        //设置为true，表示正方形
        mSquareViewFinder = true;
        //设置边框颜色为黄色
        mBorderPaint.setColor(Color.YELLOW);
        //应该是扫描激光
        mLaserPaint.setColor(Color.YELLOW);
    }
}
