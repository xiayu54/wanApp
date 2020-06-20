package com.yuan.fastec.latte.ec.main;

import android.graphics.Color;

import com.yuan.fastec.latte.delegates.bottom.BaseBottomDelegate;
import com.yuan.fastec.latte.delegates.bottom.BottomItemDelegate;
import com.yuan.fastec.latte.delegates.bottom.BottomTabBean;
import com.yuan.fastec.latte.delegates.bottom.ItemBuilder;
import com.yuan.fastec.latte.ec.main.cart.ShopCartDelegate;
import com.yuan.fastec.latte.ec.main.index.IndexDelegate;
import com.yuan.fastec.latte.ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  底部导航的具体实现
 */
public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "首页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
//        items.put(new BottomTabBean("{fa-compass}", "发现"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-plus-circle}", "发布"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new ShopCartDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new IndexDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        // 默认主页
        return 0;
    }

    @Override
    public int setClickColor() {
        return Color.parseColor("#ffff8800");
    }

    @Override
    public void post(Runnable runnable) {

    }
}
