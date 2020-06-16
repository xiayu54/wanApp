package com.yuan.fastec.latte.ui.recycler.adpter;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class MultipleViewHolder extends BaseViewHolder {

    public MultipleViewHolder(View view) {
        super(view);
    }

    // 简单工厂稍微包装一下
    public static MultipleViewHolder create(View view){
        return new MultipleViewHolder(view);
    }
}
