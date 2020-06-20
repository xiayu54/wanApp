package com.yuan.fastec.latte.ec.pay;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public interface IAliPayResultListener {

    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
