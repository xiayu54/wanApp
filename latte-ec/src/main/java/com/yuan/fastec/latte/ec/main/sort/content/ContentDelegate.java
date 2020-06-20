package com.yuan.fastec.latte.ec.main.sort.content;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.R2;
import com.yuan.fastec.latte.ec.constant.Constant;
import com.yuan.fastec.latte.net.Rx.RxRestClient;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class ContentDelegate extends LatteDelegate {

    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView;

    // 左侧list 每点击一个 菜单，右侧获取一个 id ，随之更新布局
    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    // 声明一个变量，接收传进来 的 id
    private int mContentId = -1;
    private List<SectionBean> mData = null;


    /**
     * 写在这里面，下次传入 id 的时候，只需要调用即可，不需要记忆 key
     * @param contentId
     * @return
     */
    public static ContentDelegate newInstance(int contentId){
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null){
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //
        final GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(manager);
        initData();
    }

    private void initData(){
        RxRestClient.Builder()
                .url(Constant.PROJECT_LIST + mContentId)
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
                        mData = new SectionDataConverter().conert(s);
                        final SectionAdapter adapter = new SectionAdapter(R.layout.item_section_content, mData);
                        mRecyclerView.setAdapter(adapter);
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
    public void post(Runnable runnable) {

    }
}
