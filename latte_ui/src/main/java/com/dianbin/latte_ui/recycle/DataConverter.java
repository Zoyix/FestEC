package com.dianbin.latte_ui.recycle;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/19.
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    /**
     * 处理传进来的json数据转化为ArrayList<MultipleItemEntity>返回出来
     * @return
     */
    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }

}
