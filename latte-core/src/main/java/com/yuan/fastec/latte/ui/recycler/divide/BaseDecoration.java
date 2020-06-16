package com.yuan.fastec.latte.ui.recycler.divide;

import androidx.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  RecyclerView 分割线基类
 */
public class BaseDecoration extends DividerItemDecoration {

    /**
     * @param color 分割线颜色
     * @param size  分割线粗细
     */
    private BaseDecoration(@ColorInt int color, int size){
        setDividerLookup(new DividerLookUpImpl(color, size));
    }

    public static BaseDecoration create(@ColorInt int color, int size){
        return new BaseDecoration(color, size);
    }
}
