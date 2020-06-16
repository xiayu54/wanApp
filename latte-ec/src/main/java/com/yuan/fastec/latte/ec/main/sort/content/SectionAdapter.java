package com.yuan.fastec.latte.ec.main.sort.content;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuan.fastec.latte.ec.R;

import java.util.List;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  分类页面右侧
 */
public class SectionAdapter extends BaseQuickAdapter<SectionBean, BaseViewHolder> {

    private static  final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public SectionAdapter(int layoutResId, @Nullable List<SectionBean> data) {
        super(layoutResId, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        final String envelopePic = item.getEnvelopePic();
        final String name = item.getTitle();
        final int id = item.getId();
        helper.setText(R.id.tv_section, name);
        final AppCompatImageView envelopeImageView = helper.getView(R.id.image_section);
        Glide.with(mContext)
                .load(envelopePic)
                .apply(OPTIONS)
                .into(envelopeImageView);

    }
}
