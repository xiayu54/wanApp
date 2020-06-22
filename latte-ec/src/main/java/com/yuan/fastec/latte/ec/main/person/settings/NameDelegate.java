package com.yuan.fastec.latte.ec.main.person.settings;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class NameDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_name;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void post(Runnable runnable) {

    }
}
