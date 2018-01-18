package com.dianbin.latte.ec.detail;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dianbin.latte.ec.R;
import com.dianbin.latte.ui.recycle.ItemType;
import com.dianbin.latte.ui.recycle.MultipleFields;
import com.dianbin.latte.ui.recycle.MultipleItemEntity;
import com.dianbin.latte.ui.recycle.MultipleRecyclerAdapter;
import com.dianbin.latte.ui.recycle.MultipleViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

/**
 * 初始化商品详情分类栏的每一个分页的fragment的适配器
 */
public class RecycleImageAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .dontAnimate();

    protected RecycleImageAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.SINGLE_BIG_IMAGE, R.layout.item_image);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        final int type = holder.getItemViewType();
        switch (type) {
            case ItemType.SINGLE_BIG_IMAGE:
                final AppCompatImageView imageView = holder.getView(R.id.image_rv_item);
                final String url = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(url)
                        .apply(OPTIONS)
                        .into(imageView);
                break;
            default:
                break;
        }
    }
}
