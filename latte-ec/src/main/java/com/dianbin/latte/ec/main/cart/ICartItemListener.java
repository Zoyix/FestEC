package com.dianbin.latte.ec.main.cart;

/**
 * Created by zhouyixin on 2018/1/1.
 */


public interface ICartItemListener {
    /**
     * 传入一栏得总价
     */
    void onItemClick(double itemTotalPrice);
}
