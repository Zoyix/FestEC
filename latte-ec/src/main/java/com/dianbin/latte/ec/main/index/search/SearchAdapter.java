package com.dianbin.latte.ec.main.index.search;

import android.support.v7.widget.AppCompatTextView;

import com.dianbin.latte.ec.R;
import com.dianbin.latte.ui.recycle.MultipleFields;
import com.dianbin.latte.ui.recycle.MultipleItemEntity;
import com.dianbin.latte.ui.recycle.MultipleRecyclerAdapter;
import com.dianbin.latte.ui.recycle.MultipleViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */

/**
 * 搜索历史记录的适配器
 */
public class SearchAdapter extends MultipleRecyclerAdapter {

    protected SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (entity.getItemType()) {
            case SearchItemType.ITEM_SEARCH:
                final AppCompatTextView tvSearchItem = holder.getView(R.id.tv_search_item);
                final String history = entity.getField(MultipleFields.TEXT);
                tvSearchItem.setText(history);
                break;
            default:
                break;
        }
    }
}
