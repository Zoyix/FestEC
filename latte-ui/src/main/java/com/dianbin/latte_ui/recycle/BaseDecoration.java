package com.dianbin.latte_ui.recycle;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * BaseDecoration class
 * 分割线基类
 *
 * @author zoyix
 * @date 2017/12/21
 */
public class BaseDecoration extends DividerItemDecoration {
    private BaseDecoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookupImpl(color, size));
    }

    public static BaseDecoration create(@ColorInt int color, int size) {
        return new BaseDecoration(color, size);
    }
}
