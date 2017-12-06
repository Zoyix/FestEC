package com.dianbin.latte.app;

import com.dianbin.latte.util.storage.LattePreference;

/**
 * Created by Administrator on 2017/12/6.
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    public static void  setSignState(boolean state){
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    public static boolean isSignIn(){
        return  LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker){
        if (isSignIn()){
            checker.onSignIn();
        }else {
            checker.onNotSignIn();
        }
    }
}
