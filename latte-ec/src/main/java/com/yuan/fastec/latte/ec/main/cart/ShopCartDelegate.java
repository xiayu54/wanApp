package com.yuan.fastec.latte.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joanzapata.iconify.widget.IconTextView;
import com.yuan.fastec.latte.delegates.bottom.BottomItemDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.R2;
import com.yuan.fastec.latte.ec.pay.FastPay;
import com.yuan.fastec.latte.net.Rx.RxRestClient;
import com.yuan.fastec.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class ShopCartDelegate extends BottomItemDelegate implements ICartItemListener {
    @BindView(R2.id.Rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_cart_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStub mStubNoItem;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;


    private ShopCartAdapter mCartAdapter;
    private int mIconSelectedCount = 0;
    // 记录当前选中的要删除的 item 数量
    private int mCurrentCount = 0;
    // 总共的 item 的数量
    private int mTotalCount = 0;
    private double mTotalPrice = 0.00;
    // ViewStub 是否已经加载
    private boolean mIsInflated =  false;


    @OnClick(R2.id.icon_cart_select_all)
    void onClickSelectAll(){
        // 点击全选
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0){
            // 没有选中的的话,点击变色
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            mIconSelectAll.setTag(1);
            // 选择了
            mCartAdapter.setIsSelectedAll(true);
            // 更新 RV 的显示状态
            // 使用这个方法，整个 RV 会闪一下
//            mCartAdapter.notifyDataSetChanged();
            mCartAdapter.notifyItemRangeChanged(0, mCartAdapter.getItemCount());
        }else {
            mIconSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_default));
            mIconSelectAll.setTag(0);
            mCartAdapter.setIsSelectedAll(false);
//            mCartAdapter.notifyDataSetChanged();
            mCartAdapter.notifyItemRangeChanged(0, mCartAdapter.getItemCount());
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_delete)
    void onClickRemoveSelectedItem(){
        final List<MultipleItemEntity> data = mCartAdapter.getData();
        // 找到要删除的数据
        List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartFields.IS_SELECTED);
            if (isSelected){
                deleteEntities.add(entity);
            }
        }
        //  取出要删除的数据，要好好看看
        for (MultipleItemEntity deleteEntity : deleteEntities) {
            int removePosition;
            final int entityPosition = deleteEntity.getField(ShopCartFields.POSITION);
            if (entityPosition > mCurrentCount - 1){
                removePosition = entityPosition - (mTotalCount - mCurrentCount);
            }else {
                removePosition = entityPosition;
            }
            if (removePosition <= mCartAdapter.getItemCount()){
                // 移除
                mCartAdapter.remove(removePosition);
                mCurrentCount = mCartAdapter.getItemCount();
                // 更新数据
                mCartAdapter.notifyItemRangeChanged(removePosition, mCartAdapter.getItemCount());
            }
        }
        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear(){
        // 清空所有
        mCartAdapter.getData().clear();
        mCartAdapter.notifyDataSetChanged();
        checkItemCount();
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClick(){
        // 弹出弹框
        FastPay.create(this).beginPayDialog();
    }

    /**
     * 检测是否有 item，显示 空 item 时的 layout
     */
    private void checkItemCount(){
        final int count = mCartAdapter.getItemCount();
        if (count == 0){
            // 购物车内没有商品时，新建一个 layout，这个时候，就用到了 viewStub
            if (!mIsInflated){
                // 只有未加载 ViewStub，才去 inflate
                final View stubView = mStubNoItem.inflate();
                final AppCompatTextView tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
                tvToBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "该购物啦", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            mRecyclerView.setVisibility(View.GONE);
        }else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 初始化 tag
        mIconSelectAll.setTag(0);
        mStubNoItem.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                mIsInflated = true;
            }
        });
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
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
                        final ArrayList<MultipleItemEntity> data = new ShopCartDataConverter()
                                .setJsonData(s)
                                .convert();
                        mCartAdapter = new ShopCartAdapter(data);
                        mCartAdapter.setCartItemListener(ShopCartDelegate.this);
                        mTotalPrice = mCartAdapter.getTotalPrice();
                        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        mRecyclerView.setAdapter(mCartAdapter);
                        checkItemCount();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        // itemTotalPrice 预留出来的接口
        // 取出总价
        final double price = mCartAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }
}
