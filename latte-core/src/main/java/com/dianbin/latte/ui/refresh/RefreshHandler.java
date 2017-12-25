package com.dianbin.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dianbin.latte.app.Latte;
import com.dianbin.latte.net.RestClient;
import com.dianbin.latte.net.callBack.ISuccess;
import com.dianbin.latte.ui.recycle.DataConverter;
import com.dianbin.latte.ui.recycle.MultipleRecyclerAdapter;

/**
 * Created by zhouyixin on 2017/12/17.
 */

/**
 * 刷新助手，实现下拉刷新的方法，和具体页面的请求方法
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLEVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    /**
     *
     * @param swipeRefreshLayout 下拉刷新的布局
     * @param recyclerView 要被设置适配器的RecyclerView
     * @param converter
     * @param bean  //TODO 这个类用来干嘛？？没有被使用？
     */
    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView, DataConverter converter, PagingBean bean) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLEVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView, DataConverter converter) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, converter, new PagingBean());
    }

    /**
     * 下拉刷新方法
     */
    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        //TODO 这个handler是什么？
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行一些网络请求
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    public void firstPage(String url) {
        //TODO 设置了有什么用？
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        //设置上拉加载
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLEVIEW);
                        RECYCLEVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
