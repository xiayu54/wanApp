package com.yuan.fastec.latte.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.ContentFrameLayout;

import com.yuan.fastec.latte.R;
import com.yuan.fastec.latte.delegates.LatteDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public abstract class ProxyActivity extends SupportActivity {

    /**
     * 设置 Delegate
     * @return
     */
    public abstract LatteDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState){
        @SuppressLint("RestrictedApi")
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null){
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出的时候，进行垃圾回收
        System.gc();
        System.runFinalization();
    }
}
