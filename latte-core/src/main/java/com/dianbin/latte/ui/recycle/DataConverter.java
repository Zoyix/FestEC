package com.dianbin.latte.ui.recycle;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/19.
 */

public abstract class DataConverter {

    //TODO 这个类有什么用？
    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

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
