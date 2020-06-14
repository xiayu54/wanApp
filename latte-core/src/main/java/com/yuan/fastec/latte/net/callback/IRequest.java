package com.yuan.fastec.latte.net.callback;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public interface IRequest {

    /**
     * 请求开始
     */
    void onRequestStart();

    /**
     * 请求结束
     */
    void onRequestEnd();
}
