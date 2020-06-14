package com.yuan.fastec.latte.ui.launcher;



import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class LauncherHolder implements Holder<Integer> {

    private AppCompatImageView mImageView = null;

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        // 每次滑动需要更新的东西
        mImageView.setBackgroundResource(data);
    }
}
