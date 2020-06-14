package com.yuan.fastec.latte.delegates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yuan.fastec.latte.activities.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  基础的 Delegate
 *      并不希望使用时，去实例这个类，修改为 abstract
 */
public abstract class BaseDelegate extends SwipeBackFragment {

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder;

    /**
     * 设置布局
     * @return
     */
    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer){
            rootView = inflater.inflate((int) setLayout(), container, false);
        }else if (setLayout() instanceof View){
            rootView = (View) setLayout();
        }else {
            throw new ClassCastException("setLayout() type must be int or View !");
        }
        if (rootView != null){
            mUnbinder = ButterKnife.bind(this, rootView);
            onBindView(savedInstanceState, rootView);
        }
        return rootView;
    }

    public final ProxyActivity getProxyActivity(){
        return (ProxyActivity) _mActivity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
