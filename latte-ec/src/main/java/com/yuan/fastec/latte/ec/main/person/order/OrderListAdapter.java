package com.yuan.fastec.latte.ec.main.person.order;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ui.recycler.MultipleFields;
import com.yuan.fastec.latte.ui.recycler.MultipleItemEntity;
import com.yuan.fastec.latte.ui.recycler.adpter.MultipleRecyclerAdapter;
import com.yuan.fastec.latte.ui.recycler.adpter.MultipleViewHolder;

import java.util.List;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class OrderListAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();

    protected OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITME_ORDER_LIST, R.layout.item_order_list);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case OrderListItemType.ITME_ORDER_LIST:
                final AppCompatImageView imageView = holder.getView(R.id.image_order_list);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_order_list_title);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_order_list_price);
                final AppCompatTextView tvTimer = holder.getView(R.id.tv_order_list_time);

                // 取值
                final String title = entity.getField(MultipleFields.TITLE);
                final double price = entity.getField(OrderItemFields.PRICE);
                final String time = entity.getField(OrderItemFields.TIME);
                final String imageUrl = entity.getField(MultipleFields.IMAGER_URL);

                tvTitle.setText(title);
                tvPrice.setText("价格: " + String.valueOf(price));
                tvTimer.setText("时间: " + time);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into(imageView);
                break;
            default:
                break;
        }
    }
}
