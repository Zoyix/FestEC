package com.dianbin.latte.delegates.web.event;

import com.dianbin.latte.util.log.LatteLogger;

/**
 * Created by Administrator on 2017/12/27.
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        LatteLogger.e("UndefineEvent", params);
        return null;
    }
}
