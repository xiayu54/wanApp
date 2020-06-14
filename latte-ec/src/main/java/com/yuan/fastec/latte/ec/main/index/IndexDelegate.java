package com.yuan.fastec.latte.ec.main.index;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.yuan.fastec.latte.delegates.bottom.BottomItemDelegate;
import com.yuan.fastec.latte.ec.R;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class IndexDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
