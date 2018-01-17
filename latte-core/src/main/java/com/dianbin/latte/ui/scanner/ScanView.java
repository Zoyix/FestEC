package com.dianbin.latte.ui.scanner;

import android.content.Context;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by Administrator on 2018/1/15.
 */

/**
 * 二维码view
 */
public class ScanView extends ZBarScannerView {
    public ScanView(Context context) {
        this(context,null);
    }

    public ScanView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /**
     *  返回打开二维码后中间的扫描框
     * @param context
     * @return
     */
    @Override
    protected IViewFinder createViewFinderView(Context context) {
        //TODO 试试，用原来的  看看这个库的高级用法，看看能不能改变样式，比如像中间有个激光一样的扫来扫去
//        return super.createViewFinderView(context);
        return new LatteViewFinderView(context);
    }
}
