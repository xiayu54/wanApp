package com.yuan.fastec.latte.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.WeakHashMap;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class MultipleItemEntity implements MultiItemEntity {

    // 数据转换
    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    // 处理加入数据的键值对，保证有序
    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE =
            new SoftReference<LinkedHashMap<Object, Object>>(MULTIPLE_FIELDS, ITEM_QUEUE);

    public MultipleItemEntity(LinkedHashMap<Object, Object> fields){
        FIELDS_REFERENCE.get().putAll(fields);
    }

    public static MultipleItemEntityBuilder builder(){
        return new MultipleItemEntityBuilder();
    }

    @Override
    public int getItemType() {
        // 为了保证每一个type，所强制实现的，返回类型
        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    public final <T>T getField(Object key){
        return (T) FIELDS_REFERENCE.get().get(key);
    }

    /**
     *  返回整个 map
     * @return
     */
    public final LinkedHashMap<?, ?> getFields(){
        return FIELDS_REFERENCE.get();
    }

    public final MultiItemEntity setField(Object key, Object value){
        FIELDS_REFERENCE.get().put(key, value);
        return this;
    }



}
