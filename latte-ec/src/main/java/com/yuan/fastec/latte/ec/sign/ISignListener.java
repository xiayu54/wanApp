package com.yuan.fastec.latte.ec.sign;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *
 */
public interface ISignListener {
    /**
     * 登录成功的回调
     */
    void onSignInSuccess();

    /**
     * 注册成功的回调
     */
    void onSignUpSuccess();
}
