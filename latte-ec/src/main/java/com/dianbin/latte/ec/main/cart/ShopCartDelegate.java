package com.dianbin.latte.ec.main.cart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.dianbin.latte.app.Latte;
import com.dianbin.latte.delegates.bottom.BottomItemDelegate;
import com.dianbin.latte.ec.R;
import com.dianbin.latte.ec.R2;
import com.dianbin.latte.net.RestClient;
import com.dianbin.latte.net.callBack.ISuccess;
import com.dianbin.latte.ui.recycle.MultipleItemEntity;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhouyixin on 2017/12/31.
 */

public class ShopCartDelegate extends BottomItemDelegate implements ISuccess, ICartItemListener {

    private ShopCartAdapter mAdapter = null;
    private double mTotalPrive = 0.00;

    //TODO 底层的封装再看一遍，尤其是ButtonKnife的封装
    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;

    /**
     * 这部分和视频里的不一样，视频里的代码虽然效率稍微高一点（不需要for循环），但是它容易出错。
     * 视频中是在Adapter中设个是否被全选的参数，通过public方法传入，在convert方法里置入IS_SELECTED
     * 这样如果当其它地方有重新调用adapter的notify系列的方法，会出错！即假如这个“是否被全选的参数”
     * 为false，并且已有一个被选了，此时如果其它地方有调用adapter的notify系列的方法，会出错！
     */
    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            for (MultipleItemEntity entity : data) {
                entity.setField(ShopCartItemFields.IS_SELECTED, true);
            }
            mIconSelectAll.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
            mIconSelectAll.setTag(1);
        } else {
            for (MultipleItemEntity entity : data) {
                entity.setField(ShopCartItemFields.IS_SELECTED, false);
            }
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
        }
        mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
    }

    /**
     * 原视频上的方法是错的，优化了群上的解决方法，更易懂，并且效率更高
     */
    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        //要删除的数据
        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                deleteEntities.add(entity);
            }
        }

        //从List中最后一个开始删除，防止引起下标的变动
        for (int i = deleteEntities.size() - 1; i >= 0; i--) {
            int removePosition = deleteEntities.get(i).getField(ShopCartItemFields.POSITION);
            mAdapter.remove(removePosition);
        }

        //不用再取一遍，因为list只是引用，只有一个对象
        final int size = data.size();
        for (int j = 0; j < size; j++) {
            data.get(j).setField(ShopCartItemFields.POSITION, j);
        }
        checkItemCount();
    }

    /**
     * 清除点击事件
     */
    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        checkItemCount();
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay() {

    }

    //创建订单，注意：和支付是没有关系的
    private void createOrder() {
        final String orderUrl = "http://app.api.zanzuanshi.com/api/v1/peyment";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        orderParams.put("userid", 264392);
        orderParams.put("amount", 0.01);
        orderParams.put("comment", "测试支付");
        orderParams.put("type", 1);
        orderParams.put("ordertype", 0);
        orderParams.put("isanonymous", true);
        orderParams.put("followduser", 0);
        RestClient.builder()
                .url(orderUrl)
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付
                    }
                })
                .build()
                .post();
    }

    /**
     * 检查是否需要显示stubView里的内容
     */
    //TODO 有个问题，点两次清空会崩溃
    @SuppressLint("RestrictedApi")
    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            //stubView 用法特性：https://www.jianshu.com/p/5f64bacbd759
            final View stubView = mStubNoItem.inflate();
            final AppCompatTextView tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO _mActivity和getContext()有什么区别？
                    Toast.makeText(_mActivity, "你该购物了！", Toast.LENGTH_SHORT).show();
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mIconSelectAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart.php")
                .loader(getContext())
                .success(this)
                .build()
                .get();

    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data = new ShopCartDataConverter().setJsonData(response).convert();
        mAdapter = new ShopCartAdapter(data);
        mAdapter.setCartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        //取出初始总价并设置
        mTotalPrive = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrive));
        checkItemCount();
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }
}
