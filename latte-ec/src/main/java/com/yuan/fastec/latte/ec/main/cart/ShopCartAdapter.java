package com.yuan.fastec.latte.ec.main.cart;

import android.content.LocusId;
import android.graphics.Color;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.yuan.fastec.latte.app.latte;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.net.Rx.RxRestClient;
import com.yuan.fastec.latte.ui.recycler.MultipleFields;
import com.yuan.fastec.latte.ui.recycler.MultipleItemEntity;
import com.yuan.fastec.latte.ui.recycler.adpter.MultipleRecyclerAdapter;
import com.yuan.fastec.latte.ui.recycler.adpter.MultipleViewHolder;
import com.yuan.fastec.latte.util.log.LatteLogger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class ShopCartAdapter extends MultipleRecyclerAdapter {
    // 是否全选
    private boolean mIsSelectedAll = false;
    private ICartItemListener mCartItemListener = null;
    private double mTotalPrice = 0.00;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        // 初始化 总价
        for (MultipleItemEntity entity : data){
            final double price = entity.getField(ShopCartFields.PRICE);
            final int count = entity.getField(ShopCartFields.COUNT);
            final double total = price * count;
            // 累加到 total 中
            mTotalPrice = mTotalPrice + total;
        }
        // 添加 购物车 item 布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    public void setIsSelectedAll(boolean isSelectedAll){
        this.mIsSelectedAll = isSelectedAll;
    }

    public void setCartItemListener(ICartItemListener listener){
        this.mCartItemListener = listener;
    }

    /**
     * 返回总价
     * @return
     */
    public double getTotalPrice(){
        return mTotalPrice;
    }


    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ShopCartItemType.SHOP_CART_ITEM:
                // 先取出所有的值
                final int id = entity.getField(MultipleFields.ID);
                final String logo = entity.getField(MultipleFields.IMAGER_URL);
                final String title = entity.getField(ShopCartFields.TITLE);
                final String desc = entity.getField(ShopCartFields.DESC);
                final int count = entity.getField(ShopCartFields.COUNT);
                final double price = entity.getField(ShopCartFields.PRICE);

                // 取出所有的控件
                final AppCompatImageView imgLogo = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart_select);
                // 把得到的值放进UI
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));
                Glide.with(mContext).load(logo)
                        .apply(OPTIONS)
                        .into(imgLogo);
                // 在左侧勾勾渲染之前，改变状态
                entity.setField(ShopCartFields.IS_SELECTED, mIsSelectedAll);
                // 改变后，实时取出这个值
                final boolean isSelected = entity.getField(ShopCartFields.IS_SELECTED);

                // 根据数据状态显示左侧勾勾
                if (isSelected){
                    // 选中设置为 主色
                    iconIsSelected.setTextColor(ContextCompat.getColor(latte.getApplication(), R.color.app_main));
                }else {
                    iconIsSelected.setTextColor(ContextCompat.getColor(latte.getApplication(), R.color.app_default));
                }
                // 添加一个点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = entity.getField(ShopCartFields.IS_SELECTED);
                        if (currentSelected){
                            // 点击后，变为未选中状态
                            iconIsSelected.setTextColor(ContextCompat.getColor(latte.getApplication(), R.color.app_default));
                            entity.setField(ShopCartFields.IS_SELECTED, false);
                        }else {
                            // 选中设置为 主色
                            iconIsSelected.setTextColor(ContextCompat.getColor(latte.getApplication(), R.color.app_main));
                            entity.setField(ShopCartFields.IS_SELECTED, true);
                        }
                    }
                });
                // 添加 加减 事件
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int count = entity.getField(ShopCartFields.COUNT);
                        if (Integer.parseInt(tvCount.getText().toString()) > 1){
                            // 请求服务器，告诉服务器 count - 1 了
                            RxRestClient.Builder()
                                    .url("app/home_data_shop_cart.json")
                                    .build()
                                    .get()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(String s) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum--;
                                            tvCount.setText(String.valueOf(countNum));
                                            if (mCartItemListener != null){
                                                mTotalPrice = mTotalPrice - price;
                                                final double itemTotal = countNum * price;
                                                // 传入每个 item 的 总价
                                                mCartItemListener.onItemClick(itemTotal);
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }
                    }
                });
                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int count = entity.getField(ShopCartFields.COUNT);
                            // 请求服务器，告诉服务器 count + 1 了,并把 count 上传
                            RxRestClient.Builder()
                                    .url("app/home_data_shop_cart.json")
                                    .build()
                                    .get()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(String s) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum++;
                                            tvCount.setText(String.valueOf(countNum));
                                            if (mCartItemListener != null){
                                                mTotalPrice = mTotalPrice + price;
                                                final double itemTotal = countNum * price;
                                                // 传入每个 item 的 总价
                                                mCartItemListener.onItemClick(itemTotal);
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                    }
                });
                break;
            default:
                break;
        }

    }
}
