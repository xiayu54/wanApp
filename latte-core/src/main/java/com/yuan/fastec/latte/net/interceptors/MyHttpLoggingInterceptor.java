package com.yuan.fastec.latte.net.interceptors;

import com.yuan.fastec.latte.util.log.LatteLogger;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class MyHttpLoggingInterceptor implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        LatteLogger.d("HttpLogging", message);
    }
}
