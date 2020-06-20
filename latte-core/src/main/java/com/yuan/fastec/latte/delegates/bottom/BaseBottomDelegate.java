package com.yuan.fastec.latte.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.joanzapata.iconify.widget.IconTextView;
import com.yuan.fastec.latte.R;
import com.yuan.fastec.latte.R2;
import com.yuan.fastec.latte.delegates.LatteDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  底部基类，只一个容器，不直接使用
 */
public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {
    // 组合到容器中
    // 存储所有的子 bean
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    // 存储所有子 fragment
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATE = new ArrayList<>();

    // LinkHashMap 存储我们的映射
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    // 声明变量，记录当前 fragment是哪个
    private int mCurrentDelegate = 0;
    // 第一个展示的是哪一个
    private int mIndexDelegate = 0;
    // 声明点击变色的颜色
    private int mClickColor = Color.RED;

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar = null;

    /**
     * 添加item
     * @param builder
     * @return
     */
    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);

    /**
     * 设置默认展示
     * @return
     */
    public abstract int setIndexDelegate();

    /**
     * 设置点击颜色
     * @return
     */
    @ColorInt
    public abstract int setClickColor();

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setClickColor() != 0){
            mClickColor = setClickColor();
        }

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        // 循环获取所有的item，并添加
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATE.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 在这里对获取到的 item ，进行UI的处理
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++){
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            // 设置每一个 item 的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            // 获取
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemText = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = TAB_BEANS.get(i);
            // 初始化底部的数据
            itemIcon.setText(bean.getICON());
            itemText.setText(bean.getTITLE());
            if (i == mIndexDelegate){
                // 循环的角标正好当前要展示的
                // 设置点击后的颜色
                itemIcon.setTextColor(mClickColor);
                itemText.setTextColor(mClickColor);
            }
        }
        // 把我们加入的 fragment，转化为数组，保证原子性
        final ISupportFragment[] delegateArray = ITEM_DELEGATE.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, delegateArray);
    }

    /**
     * 每点击一次后，颜色重置
     */
    private void  resetColor(){
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++){
            final  RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        final  int tag = (int) v.getTag();
        // 先重置颜色
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        itemIcon.setTextColor(mClickColor);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickColor);
        // 第一个是需要显示的，第二个是需要隐藏的
        // 一直隐藏上一个
        getSupportDelegate().showHideFragment(ITEM_DELEGATE.get(tag), ITEM_DELEGATE.get(mCurrentDelegate));
        // 一定要注意先后顺序
        mCurrentDelegate = tag;
    }
}
