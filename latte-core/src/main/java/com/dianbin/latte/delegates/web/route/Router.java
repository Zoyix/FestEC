package com.dianbin.latte.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.dianbin.latte.delegates.web.WebDelegate;

/**
 * Created by Administrator on 2017/12/26.
 */

public class Router {
    private Router(){

    }

    //TODO 这里为啥加static？
    private static class Holder{
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance(){
        return  Holder.INSTANCE ;
    }

    /**
     * @return 返回true 表示原生接管了
     */
    public final boolean handleWebUrl(WebDelegate delegate,String url){
        //如果是电话协议
        if (url.contains("tel:")){

        }

        return true;
    }

    private void  callPhone (Context context,String url){
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data =
    }
}
