package com.yuan.fastec.latte.ec.main.person.profile;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.R2;
import com.yuan.fastec.latte.ec.main.person.list.ListAdapter;
import com.yuan.fastec.latte.ec.main.person.list.ListBean;
import com.yuan.fastec.latte.ec.main.person.list.ListItemType;
import com.yuan.fastec.latte.ec.main.person.settings.NameDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class UserProfileDelegate extends LatteDelegate {

    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final ListBean image = new ListBean.Buidler()
                .setItemType(ListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl("https://img.zcool.cn/community/01bba8564aefde32f87512f6a47ea3.jpg")
                .build();

        final ListBean name = new ListBean.Buidler()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("姓名")
                .setValue("未设置姓名")
                .setDelegate(new NameDelegate())
                .build();

        final ListBean gen = new ListBean.Buidler()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("性别")
                .setValue("未设置性别")
                .build();

        final ListBean birth = new ListBean.Buidler()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("生日")
                .setValue("未设置生日")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(image);
        data.add(name);
        data.add(gen);
        data.add(birth);


        //设置 RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }

    @Override
    public void post(Runnable runnable) {

    }
}
