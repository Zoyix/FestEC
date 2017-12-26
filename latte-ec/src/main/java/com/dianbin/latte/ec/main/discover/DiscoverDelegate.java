package com.dianbin.latte.ec.main.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dianbin.latte.delegates.bottom.BottomItemDelegate;
import com.dianbin.latte.ec.R;

/**
 * Created by Administrator on 2017/12/26.
 */

public class DiscoverDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
