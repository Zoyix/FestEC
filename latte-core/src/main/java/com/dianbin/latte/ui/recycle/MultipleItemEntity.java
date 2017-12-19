package com.dianbin.latte.ui.recycle;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/12/19.
 */

public class MultipleItemEntity implements MultiItemEntity {

//    ReferenceQueue中保存的对象是已经失去了它所软引用的对象的Reference对象  http://blog.51cto.com/alinazh/1276173

    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUENE = new ReferenceQueue<>();
    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE =
            new SoftReference<LinkedHashMap<Object, Object>>(MULTIPLE_FIELDS, ITEM_QUENE);

    MultipleItemEntity(LinkedHashMap<Object,Object> fields) {
        FIELDS_REFERENCE.get().putAll(fields);
    }

    @Override
    public int getItemType() {
        //TODO 返回这个做什么？ITEM_TYPE是什么
        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    //10-4 09:20 关于T

    @SuppressWarnings("unchecked")
    public final <T> T getField(Object key){
        return (T) FIELDS_REFERENCE.get().get(key);
    }

    //TODO ?代表啥
    public final  LinkedHashMap<?,?> getFields(){
        return FIELDS_REFERENCE.get();
    }

    public final MultiItemEntity setFields(Object key,Object value){
        FIELDS_REFERENCE.get().put(key,value);
        return this;
    }

}
