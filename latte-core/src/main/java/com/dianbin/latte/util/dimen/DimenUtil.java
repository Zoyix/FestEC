package com.dianbin.latte.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.dianbin.latte.app.Latte;

/**
 * Created by Administrator on 2017/11/17.
 */

public class DimenUtil {

    public static int getScreenWidth() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

}
