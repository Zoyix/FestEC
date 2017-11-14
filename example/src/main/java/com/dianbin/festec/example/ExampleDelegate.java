package com.dianbin.festec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dianbin.latte.delegates.LatteDelegate;

/**
 * Created by Administrator on 2017/11/14.
 */

public class ExampleDelegate extends LatteDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
