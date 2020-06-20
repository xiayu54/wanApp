package com.yuan.fastec.latte.ec.main.sort;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.yuan.fastec.latte.delegates.bottom.BottomItemDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.main.sort.content.ContentDelegate;
import com.yuan.fastec.latte.ec.main.sort.list.VerticalListDelegate;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 点击的时候才加载，实现懒加载，放在bindView的话，一进入 app 就会加载
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container, listDelegate);
        // 设置 右侧第一分类显示，默认显示一
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1),false, true);
    }
}
