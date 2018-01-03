package com.dianbin.latte_ui.recycle;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2017/12/20.
 */

public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view) {
        super(view);
    }

    public static MultipleViewHolder create(View view) {
        return new MultipleViewHolder(view);
    }

}
