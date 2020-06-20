package com.yuan.fastec.example;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.yuan.fastec.latte.app.latte;
import com.yuan.fastec.latte.ec.database.DatabaseManager;
import com.yuan.fastec.latte.ec.icon.FontEcModule;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
//                .withApiHost("https://www.wanandroid.com/")
                .withApiHost("http://192.168.43.231:8080/") // 本机
//                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
        // log 初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
        // 初始化数据库
        DatabaseManager.getInstance().init(this);

    }

}
