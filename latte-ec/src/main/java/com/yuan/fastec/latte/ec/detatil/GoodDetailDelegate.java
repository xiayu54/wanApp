package com.yuan.fastec.latte.ec.detatil;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.joanzapata.iconify.widget.IconTextView;
import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.R2;
import com.yuan.fastec.latte.net.Rx.RxRestClient;
import com.yuan.fastec.latte.ui.animation.BezierAnimation;
import com.yuan.fastec.latte.ui.animation.BezierUtil;
import com.yuan.fastec.latte.ui.banner.HolderCreator;
import com.yuan.fastec.latte.ui.widget.CircleTextView;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class GoodDetailDelegate extends LatteDelegate
        implements AppBarLayout.OnOffsetChangedListener, BezierUtil.AnimationListener{

    @BindView(R2.id.goods_detail_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout = null;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager = null;
    @BindView(R2.id.detail_banner)
    ConvenientBanner<String> mBanner = null;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppBar = null;

    //底部
    @BindView(R2.id.icon_favor)
    IconTextView mIconFavor = null;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mCircleTextView = null;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRlAddShopCart = null;
    @BindView(R2.id.icon_shop_cart)
    IconTextView mIconShopCart = null;

    private static final String ARG_GOODS_ID = "ARG_GOODS_ID";
    private int mGoodId = -1;
    // 飞入动画的目标 图片Url
    private String mGoodsThumbUrl = null;
    // 加入购车的商品数量
    private int mShopCount = 0;
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100, 100);    // 重写大小

    @OnClick(R2.id.rl_add_shop_cart)
    void onClickAddShopCart(){
        final CircleImageView animImg = new CircleImageView(getContext());
        Glide.with(getContext())
                .load(mGoodsThumbUrl)
                .apply(OPTIONS)
                .into(animImg);
        BezierAnimation.addCart(this, mRlAddShopCart, mIconShopCart, animImg, this);
    }

    private void setShopCartCount(String data){
        mGoodsThumbUrl = JSON.parseObject(data).getString("thumb");
        if (mShopCount == 0){
            mCircleTextView.setVisibility(View.GONE);
        }
    }


    public static GoodDetailDelegate create(@NonNull int goodId){
        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID, goodId);
        final GoodDetailDelegate delegate = new GoodDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取商品 id
        final Bundle args = getArguments();
        if (args != null){
            mGoodId = args.getInt(ARG_GOODS_ID);
            Toast.makeText(_mActivity, "id: " + mGoodId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        mAppBar.addOnOffsetChangedListener(this);
        // 设置购物车上的角标颜色
        mCircleTextView.setCircleBackGround(Color.RED);
        initData();
        initTabLayout();

    }

    private void initPager(JSONObject data){
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), data);
        mViewPager.setAdapter(adapter);
    }


    private void initTabLayout(){
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);    // 设置 TabLayout 的 tab 均分
        // 设置tab 下 指示器的颜色
        mTabLayout.setSelectedTabIndicatorColor(
                ContextCompat.getColor(getContext(), R.color.app_main));
        // 设置字体颜色
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        // 社会背景色
        mTabLayout.setBackgroundColor(Color.WHITE);
        // 设置 viewPager
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData(){
        // 这里其实是要传入 商品 id 的
        RxRestClient.Builder()
                .url("app/home_data_good_detail.json")
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
                        final JSONObject data = JSON.parseObject(s).getJSONObject("data");
//                        Toast.makeText(_mActivity, s, Toast.LENGTH_SHORT).show();
                        initBanner(data);
                        initPager(data);
                        initGoodsInfo(s);
                        setShopCartCount(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    private void initGoodsInfo(String data){
        getSupportDelegate().loadRootFragment(R.id.frame_goods_info, GoodsInfoDelegate.create(data));
    }

    private void initBanner(JSONObject data){
        final JSONArray array = data.getJSONArray("banners");
        final List<String> images = new ArrayList<>();
        int size = array.size();
        for (int i = 0; i < size; i++){
            images.add(array.getString(i));
        }
        mBanner.setPages(new HolderCreator(), images)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(3000)
                .setCanLoop(true);
    }

    @Override
    public void post(Runnable runnable) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 返回水平打开 的一个动画
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

    }

    @Override
    public void onAnimationEnd() {
        // 设置动画
        YoYo.with(new ScaleUpAnimation())
                .duration(500)
                .playOn(mIconShopCart);

        // 同时上传给服务器，通知服务器购物车有商品添加
        RxRestClient.Builder()
                .url("app/home_data_good_cart_add.json")
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
                        // 服务器请求成功以后才显示
//                        // 没点击加入一次，商品数量 + 1
//                        mShopCount++;
//                        mCircleTextView.setVisibility(View.VISIBLE);
//                        mCircleTextView.setText(String.valueOf(mShopCount));

                        final boolean isAddedSuccess = JSON.parseObject(s).getBoolean("data");
                        if (isAddedSuccess){
                            // 请求完，接收到成功的数据，才添加
                            // 没点击加入一次，商品数量 + 1
                            mShopCount++;
                            mCircleTextView.setVisibility(View.VISIBLE);
                            mCircleTextView.setText(String.valueOf(mShopCount));
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
