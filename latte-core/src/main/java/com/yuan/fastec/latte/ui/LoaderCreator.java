package com.yuan.fastec.latte.ui;

import android.content.Context;
import android.graphics.Color;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  动画加载创建
 */
public class LoaderCreator {

    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView creator(String type, Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null){
            final Indicator indicator = getIndicator(type);
            indicator.setColor(Color.RED);

            LOADING_MAP.put(type, indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    /**
     * 根据 name 生成一个具体的动画View
     * @param name
     * @return
     */
    private static Indicator getIndicator(String name){
        if (name == null || name.isEmpty()){
            return null;
        }
        final StringBuilder drawableClassName = new StringBuilder();
        if (name.contains(".")){
            // 是整个的类名
            // 反射获取包名
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);
        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
