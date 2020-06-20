package com.yuan.fastec.latte.ec.main.cart;

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
public class ShopCartDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONObject("data").getJSONArray("list");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String title = data.getString("title");
            final double price = data.getInteger("price");
            final String desc = data.getString("desc");
            final int count = data.getInteger("count");
            final String logo = data.getString("logo");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ShopCartItemType.SHOP_CART_ITEM)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGER_URL, logo)
                    .setField(ShopCartFields.TITLE, title)
                    .setField(ShopCartFields.DESC, desc)
                    .setField(ShopCartFields.COUNT, count)
                    .setField(ShopCartFields.PRICE, price)
                    .setField(ShopCartFields.IS_SELECTED, false)    // 设置默认是未选中
                    .setField(ShopCartFields.POSITION, i)
                    .build();
            // 添加到
            dataList.add(entity);
        }
        return dataList;
    }
}
