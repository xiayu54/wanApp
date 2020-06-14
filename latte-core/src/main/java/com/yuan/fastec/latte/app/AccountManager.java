package com.yuan.fastec.latte.app;

import com.yuan.fastec.latte.util.storage.LattePreference;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  管理用户信息的
 */
public class AccountManager {

    private enum SignTag{
        SIGN_TAG
    }

    /**
     * 设置登录状态，登录后调用的方法
     * @param state
     */
    public static void setSignState(boolean state){
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    /**
     * 判断是否已经登录
     * @return
     */
    private static boolean isSignIn(){
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    /**
     * 检查用户状态
     * @param checker
     */
    public static void checkAccount(IUserChecker checker){
        if (isSignIn()){    // 是否登录
            checker.onSignIn();
        }else {
            checker.onNotSignIn();
        }
    }
}
