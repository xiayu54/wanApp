package com.yuan.fastec.latte.ui.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yuan.fastec.latte.R;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class ImageHolder implements Holder<String> {

    private AppCompatImageView mImageView = null;
    private static final RequestOptions BANNER_OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        // Banner 每转动一次，更新的 UI
        Glide.with(context).load(data)
                .apply(BANNER_OPTIONS)
                .into(mImageView);
    }
}
