package com.yuan.fastec.latte.ec.main.person.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuan.fastec.latte.ui.recycler.DataConverter;
import com.yuan.fastec.latte.ui.recycler.MultipleFields;
import com.yuan.fastec.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class OrderListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray jsonArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final int size = jsonArray.size();

        for (int i = 0; i < size; i++){
            final JSONObject data = jsonArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final double price = data.getDouble("price");
            final String time = data.getString("time");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(OrderListItemType.ITME_ORDER_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGER_URL, thumb)
                    .setField(MultipleFields.TITLE, title)
                    .setField(OrderItemFields.PRICE, price)
                    .setField(OrderItemFields.TIME, time)
                    .build();

            dataList.add(entity);
        }
        return dataList;
    }
}
