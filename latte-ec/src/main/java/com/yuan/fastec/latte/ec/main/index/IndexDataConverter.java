package com.yuan.fastec.latte.ec.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuan.fastec.latte.ui.recycler.DataConverter;
import com.yuan.fastec.latte.ui.recycler.ItemType;
import com.yuan.fastec.latte.ui.recycler.MultipleFields;
import com.yuan.fastec.latte.ui.recycler.MultipleItemEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class IndexDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        // 处理数据
        // 获取 json对象
        final JSONObject dataObject = JSON.parseObject(getJsonData()).getJSONObject("data");
        // 获取 head json 对象
        final JSONObject headObject = dataObject.getJSONObject("head");
        // 获取 list 数组
        final JSONArray listArray = dataObject.getJSONArray("list");
        // list 数组大小
        final int sizeList =  listArray.size();
        // 取出 list 里面的数据
        for (int i = 0;i < sizeList; i++){
            // 得到每一个 item 对象
            final JSONObject itemList = listArray.getJSONObject(i);
            /**
             * type 类型：2，3，1，2，2，1，2，
             */
            final int dataType = itemList.getInteger("type");
            final String imageUrl = itemList.getString("logo");
            final String text = itemList.getString("title");
            final int spanSize = Integer.parseInt(itemList.getString("spanSize"));  // 所占宽度
            final JSONArray banners = itemList.getJSONArray("banners");
            final ArrayList<String> bannerImages = new ArrayList<>();
            int type = 0;
            if (dataType == 3){ // 只有字
                type = ItemType.TEXT;
            }else if(dataType == 1){  // 只有图片
                type = ItemType.IMAGE;
            }else if (dataType == 2){   // 图文都有
                type = ItemType.TEXT_IMAGE;
            }
            else if (banners != null){ // banner 不为 null
                type = ItemType.BANNER;
                // banner 初始化
                final int sizeBanner = banners.size();
                for (int j = 0; j < sizeBanner; j++){
                    final String banner = banners.getString(j);
                    // 加入到集合中
                    bannerImages.add(banner);
                }
            }
            // 初始化 entity
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.SPAN_SIZE, spanSize)
                    .setField(MultipleFields.TEXT, text)
                    .setField(MultipleFields.IMAGER_URL, imageUrl)
                    .setField(MultipleFields.BANNERS, bannerImages)
                    .setField(MultipleFields.ID, i)
                    .build();

            ENTITIES.add(entity);

        }



        return ENTITIES;
    }
}
