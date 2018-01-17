package com.dianbin.latte.ec.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.dianbin.latte.delegates.LatteDelegate;
import com.dianbin.latte.ec.R;
import com.dianbin.latte.ec.R2;
import com.dianbin.latte.net.RestClient;
import com.dianbin.latte.net.callBack.ISuccess;
import com.dianbin.latte.ui.recycle.MultipleItemEntity;
import com.dianbin.latte.util.storage.LattePreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17.
 */

/**
 * 搜索商品页面
 */
public class SearchDelegate extends LatteDelegate {

    @BindView(R2.id.rv_search)
    RecyclerView mRecycleView = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchEdit = null;

    @OnClick(R2.id.tv_top_search)
    void onClickSearch() {
        RestClient.builder()
                .url("search.php?key=")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String searchItemText = mSearchEdit.getText().toString();
                        saveItem(searchItemText);
                        mSearchEdit.setText("");
                        //展示一些东西
                        //弹出一段话
                    }
                })
                .build()
                .get();

    }

    @OnClick(R2.id.icon_top_search_back)
    void onClickBack(){
        getSupportDelegate().pop();
    }

    /**
     * 存入当前搜索的值
     * @param item
     */
    private void saveItem(String item) {
        if (!StringUtils.isEmpty(item) && !StringUtils.isSpace(item)) {
            List<String> history;
            final String historyStr = LattePreference.getCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY);
            if (StringUtils.isEmpty(historyStr)) {
                history = new ArrayList<>();
            } else {
                history = JSON.parseObject(historyStr, ArrayList.class);
            }
            history.add(item);
            final String json = JSON.toJSONString(history);

            LattePreference.addCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY,json);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        //TODO 问老师  不知道为什么不设置的话indexDelegate中的edit会有字打进去？
        //设置焦点，最后一行本来是打开软键盘，不过好像自己会打开
        mSearchEdit.setFocusable(true);
        mSearchEdit.setFocusableInTouchMode(true);
        mSearchEdit.requestFocus();
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycleView.setLayoutManager(manager);

        final List<MultipleItemEntity> data = new SearchDataConverter().convert();
        final SearchAdapter adapter = new SearchAdapter(data);
        mRecycleView.setAdapter(adapter);

        //设置分割线
        final DividerItemDecoration itemDecoration = new DividerItemDecoration();
        itemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .margin(20,20)
                        .color(Color.GRAY)
                        .build();
            }
        });
        mRecycleView.addItemDecoration(itemDecoration);

    }
}
