package com.dianbin.latte.ec.main.personal.List;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dianbin.latte.ec.R;

import java.util.List;

/**
 * Created by zhouyixin on 2018/1/6.
 */

/**
 * RecycleView 数据适配器
 */
public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {
//和MultipleRecyclerAdapter有什么区别？ MultipleRecyclerAdapter继承的就是BaseMultiItemQuickAdapter。

    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.ITEM_NORMAL, R.layout.arrow_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (helper.getItemViewType()) {
            case ListItemType.ITEM_NORMAL:
                helper.setText(R.id.tv_arrow_text, item.getText());
                helper.setText(R.id.tv_arrow_value, item.getValue());
                break;
            default:
                break;
        }
    }
}
