package com.yuan.fastec.latte.ec.detatil;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  FragmentStatePagerAdapter 这个 Adapter 并不会保留每一个 Pager 的状态，也就是说我们这个页面销毁以后，我们的数据状态也会随之销毁
 *  防止点击不同商品时，会出现商品信息重叠
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // 存放每一个tab 的名称
    private final ArrayList<String> TAB_TITLES = new ArrayList<>();
    // 存放每个 tab 里面的图片路径
    private final ArrayList<ArrayList<String>> PICTURES = new ArrayList<>();

    public TabPagerAdapter(FragmentManager fm, JSONObject data) {
        super(fm);
        // 获取 tabs 的信息，注意，这里的tabs 是一条信息
        final JSONArray tabs = data.getJSONArray("tabs");
        final int size = tabs.size();
        for (int i = 0; i < size; i++){
            final  JSONObject eachTab = tabs.getJSONObject(i);
            final String name = eachTab.getString("name");
            final JSONArray pictureUrls = eachTab.getJSONArray("pictures");
            final ArrayList<String> eachTabPicturesArray = new ArrayList<>();
            // 存储每个图片
            final int pictureSize = pictureUrls.size();
            for (int j = 0; j <pictureSize; j++){
                eachTabPicturesArray.add(pictureUrls.getString(j));
            }
            TAB_TITLES.add(name);
            PICTURES.add(eachTabPicturesArray);
        }
    }

    @Override
    public Fragment getItem(int position) {
        // 有几个 tab，写几个
        if (position == 0){
            return ImageDelegate.create(PICTURES.get(0));
        }else if (position == 1){
            return ImageDelegate.create(PICTURES.get(1));
        } else if (position == 2){
            return ImageDelegate.create(PICTURES.get(2));
        }
        return null;
    }

    @Override
    public int getCount() {
        // 记得一定要传
        // 返回几条数据
        return TAB_TITLES.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // 返回 tab  的title
        return TAB_TITLES.get(position);
    }
}
