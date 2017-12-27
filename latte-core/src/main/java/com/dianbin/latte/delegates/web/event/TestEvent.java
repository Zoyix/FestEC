package com.dianbin.latte.delegates.web.event;

import android.widget.Toast;

/**
 * Created by Administrator on 2017/12/27.
 */

public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), params, Toast.LENGTH_LONG).show();
        return null;
    }
}
