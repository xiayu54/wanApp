package com.yuan.fastec.latte.app;

import android.content.Context;

import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  对外的工具类
 */
public final class latte {

    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<Object, Object> getConfigurations(){
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Context getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());

    }
}
