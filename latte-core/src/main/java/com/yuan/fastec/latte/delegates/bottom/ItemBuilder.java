package com.yuan.fastec.latte.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *
 */
public final class ItemBuilder {
    // Map 建立映射关系
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    // 简单工厂创建，让参数一目了然
    static ItemBuilder builder(){
        return new ItemBuilder();
    }

    /**
     * 添加每一个 item
     * @param bean
     * @param delegate
     * @return
     */
    public final ItemBuilder addItem(BottomTabBean bean, BottomItemDelegate delegate){
        ITEMS.put(bean, delegate);
        return this;
    }

    /**
     * 一次性添加所有item
     *
     * @return
     */
    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> items){
        ITEMS.putAll(items);
        return this;
    }

    /**
     * 返回集合
     * @return
     */
    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build(){
        return ITEMS;
    }



}
