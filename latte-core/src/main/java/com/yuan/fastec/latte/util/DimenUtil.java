package com.yuan.fastec.latte.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.yuan.fastec.latte.app.latte;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  测量的工具类
 */
public class DimenUtil {

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth(){
        final Resources resources = latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static int getScreenHeight(){
        final Resources resources = latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
