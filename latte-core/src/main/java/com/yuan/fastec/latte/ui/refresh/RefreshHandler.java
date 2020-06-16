package com.yuan.fastec.latte.ui.refresh;

import android.os.Handler;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yuan.fastec.latte.app.latte;
import com.yuan.fastec.latte.net.Rx.RxRestClient;
import com.yuan.fastec.latte.ui.recycler.DataConverter;
import com.yuan.fastec.latte.ui.recycler.adpter.MultipleRecyclerAdapter;
import com.yuan.fastec.latte.util.log.LatteLogger;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  刷新的辅助类
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean PAGING_BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    private Handler mHandler = new Handler();

    public RefreshHandler(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView,
                          DataConverter converter, PagingBean pagingBean) {
        this.REFRESH_LAYOUT = refreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.PAGING_BEAN = pagingBean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    /**
     * 简单工厂包装
     * @param refreshLayout
     * @param recyclerView
     * @param converter
     * @return
     */
    public static RefreshHandler create(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView,
                                        DataConverter converter){
        return new RefreshHandler(refreshLayout, recyclerView, converter, new PagingBean());
    }

    private void  refresh(){
        // 刷新开始
        REFRESH_LAYOUT.setRefreshing(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 进行一些网络请求
                // 停止刷新
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    public void firstPage(String url){
        PAGING_BEAN.setDelayed(1000);
        RxRestClient.Builder()
                .url(url)
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
                        final JSONObject object = JSON.parseObject(s);
                        PAGING_BEAN.setTotal(Integer.parseInt(object.getString("total")))
                                .setPageSize(Integer.parseInt(object.getString("paage_size")));
                        // 设置 Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(s));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        PAGING_BEAN.addIndex();
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
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        // 上拉加载
    }
}
