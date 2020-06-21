package com.yuan.fastec.latte.ec.main.person.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yuan.fastec.latte.activities.ProxyActivity;
import com.yuan.fastec.latte.delegates.LatteDelegate;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  我的 功能模块里的数据bean
 */
public class ListBean implements MultiItemEntity {

    // item 类型
    private int mItemType = 0;
    // 头像 url
    private String mImageUrl = null;
    // 用户名
    private String mText = null;
    private String mValue = null;
    private int mId = 0;
    private LatteDelegate mDelegate = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = null;

    // 因为变量比较多，可以使用构造者模式，也就是 Builder 设计模式


    public ListBean(int itemType, String imageUrl, String text, String value, int id, LatteDelegate delegate, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        mItemType = itemType;
        mImageUrl = imageUrl;
        mText = text;
        mValue = value;
        mId = id;
        mDelegate = delegate;
        mOnCheckedChangeListener = onCheckedChangeListener;
    }


    public String getImageUrl() {
        return mImageUrl;
    }

    public String getText() {
        if (mText == null){
            return "";
        }
        return mText;
    }

    public String getValue() {
        if (mValue == null){
            return "";
        }
        return mValue;
    }

    public int getId() {
        return mId;
    }

    public LatteDelegate getDelegate() {
        return mDelegate;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Buidler{
        private int id = 0;
        private int itemType = 0;
        private String imageUrl = null;
        private String text = null;
        private String value = null;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;
        private LatteDelegate delegate = null;

        public Buidler setId(int id) {
            this.id = id;
            return this;
        }

        public Buidler setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Buidler setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Buidler setText(String text) {
            this.text = text;
            return this;
        }

        public Buidler setValue(String value) {
            this.value = value;
            return this;
        }

        public Buidler setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public Buidler setDelegate(LatteDelegate delegate) {
            this.delegate = delegate;
            return this;
        }

        public ListBean build(){
            return new ListBean(itemType, imageUrl, text, value, id, delegate, onCheckedChangeListener);
        }
    }
}
