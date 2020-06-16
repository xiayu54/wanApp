package com.yuan.fastec.latte.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuan.fastec.latte.ui.recycler.DataConverter;
import com.yuan.fastec.latte.ui.recycler.ItemType;
import com.yuan.fastec.latte.ui.recycler.MultipleFields;
import com.yuan.fastec.latte.ui.recycler.MultipleItemEntity;


import java.util.ArrayList;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *
 */
public class VerticalListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        // 获取分类数组对象
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.TAG, false)    // 点击状态，默认无
                    .build();

            dataList.add(entity);
            // 设置第一被选中
            dataList.get(0).setField(MultipleFields.TAG, true);
        }
        return dataList;
    }
}
