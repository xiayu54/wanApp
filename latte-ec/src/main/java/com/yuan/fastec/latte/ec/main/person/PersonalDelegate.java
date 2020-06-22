package com.yuan.fastec.latte.ec.main.person;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.delegates.bottom.BottomItemDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.R2;
import com.yuan.fastec.latte.ec.main.person.list.ListAdapter;
import com.yuan.fastec.latte.ec.main.person.list.ListBean;
import com.yuan.fastec.latte.ec.main.person.list.ListItemType;
import com.yuan.fastec.latte.ec.main.person.order.OrderDelegate;
import com.yuan.fastec.latte.ec.main.person.profile.UserProfileDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class PersonalDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRecyclerView = null;

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;

    @OnClick(R2.id.tv_all_order)
    void onClickAllOrder(){
        mArgs.putString(ORDER_TYPE, "all");
        startOrderListByType();
    }

    @OnClick(R2.id.img_user_avatar)
    void onClickAvatar(){
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }

    private void startOrderListByType(){
        final OrderDelegate delegate = new OrderDelegate();
        delegate.setArguments(mArgs);
        getParentDelegate().getSupportDelegate().start(delegate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_person;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        final ListBean address = new ListBean.Buidler()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setText("收货地址")
                .build();

        final ListBean system = new ListBean.Buidler()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("系统设置")
                .build();
        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);

        //设置 RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void post(Runnable runnable) {

    }
}
