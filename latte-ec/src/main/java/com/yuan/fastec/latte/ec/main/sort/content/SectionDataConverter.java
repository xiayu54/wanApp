package com.yuan.fastec.latte.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.util.ArrayList;
import java.util.List;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class SectionDataConverter {

    final List<SectionBean> conert(String json){
        final List<SectionBean> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONObject("data").getJSONArray("datas");
        int size = dataArray.size();
        for (int i = 0; i < size; i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            // 文章标题
            final String title = data.getString("title");
            // 缩略图
            final String envelopePic = data.getString("envelopePic");

            final SectionBean sectionBean = new SectionBean();
            sectionBean.setId(id);
            sectionBean.setTitle(title);
            sectionBean.setEnvelopePic(envelopePic);
            // 添加到集合中
            dataList.add(sectionBean);
        }

        return dataList;
    }
}
