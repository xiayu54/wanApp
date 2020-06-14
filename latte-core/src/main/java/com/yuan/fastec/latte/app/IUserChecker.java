package com.yuan.fastec.latte.app;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  检查用户状态的接口
 */
public interface IUserChecker {
    /**
     * 有用户信息
     */
    void onSignIn();

    /**
     * 没有用户信息
     */
    void onNotSignIn();
}
