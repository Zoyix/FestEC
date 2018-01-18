package com.dianbin.latte.ec.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/18.
 */

/**
 * /**
 * 初始化商品详情分类栏的内容的适配器
 * 用FragmentStatePagerAdapter 不会保留每一个Pager的状态，当页面销毁后数据状态也会随之销毁
 * 17-4 02:11
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<String> TAB_TITLES = new ArrayList<>();
    private final ArrayList<ArrayList<String>> PICTURES = new ArrayList<>();


    public TabPagerAdapter(FragmentManager fm, JSONObject data) {
        super(fm);
        //获取tabs信息，主要，这里的tabs是一条信息
        final JSONArray tabs = data.getJSONArray("tabs");
        final int size = tabs.size();
        for (int i = 0; i < size; i++) {
            final JSONObject eachTab = tabs.getJSONObject(i);
            final String name = eachTab.getString("name");
            final JSONArray pictureUrls = eachTab.getJSONArray("pictures");
            final ArrayList<String> eachTabPicturesArray = new ArrayList<>();
            //存储每个图片
            final int pictureSize = pictureUrls.size();
            for (int j = 0; j < pictureSize; j++) {
                eachTabPicturesArray.add(pictureUrls.getString(j));
            }
            TAB_TITLES.add(name);
            PICTURES.add(eachTabPicturesArray);
        }

    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0){
            return ImageDelegate.create(PICTURES.get(0));
        }else if (position == 1){
            return ImageDelegate.create(PICTURES.get(1));
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_TITLES.size();
    }

    /**
     * 给tabLayout设置标题的
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position);
    }
}
