package com.dianbin.latte.ec.main.index.search;

import com.alibaba.fastjson.JSONArray;
import com.dianbin.latte.ui.recycle.DataConverter;
import com.dianbin.latte.ui.recycle.MultipleFields;
import com.dianbin.latte.ui.recycle.MultipleItemEntity;
import com.dianbin.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/17.
 */

/**
 * 搜索历史的数据解析器
 */
public class SearchDataConverter extends DataConverter {

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final String jsonStr = LattePreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if (!jsonStr.equals("")){
            final JSONArray array = JSONArray.parseArray(jsonStr);
            final int size = array.size();
            for (int i=0;i<size;i++){
                //size-1-i,表示最近的搜索在上
                final String historyItemText = array.getString(size-1-i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setField(MultipleFields.TEXT,historyItemText)
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
