package com.yuan.fastec.latte.ec.main.sort.list;

import android.graphics.Color;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.main.sort.SortDelegate;
import com.yuan.fastec.latte.ec.main.sort.content.ContentDelegate;
import com.yuan.fastec.latte.ui.recycler.ItemType;
import com.yuan.fastec.latte.ui.recycler.MultipleFields;
import com.yuan.fastec.latte.ui.recycler.MultipleItemEntity;
import com.yuan.fastec.latte.ui.recycler.adpter.MultipleRecyclerAdapter;
import com.yuan.fastec.latte.ui.recycler.adpter.MultipleViewHolder;

import java.util.List;

import me.yokeyword.fragmentation.SupportHelper;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class SortRecyclerAdapter extends MultipleRecyclerAdapter {

    private final SortDelegate DELEGATE;
    // 记录上一个点击的位置
    private int mPrePosition = 0;

    /**
     * @param data
     * @param delegate 传入 这个，控制左右的关联关系
     */
    protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE =delegate;

        // 添加垂直布局菜单
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ItemType.VERTICAL_MENU_LIST:
                final String text = entity.getField(MultipleFields.TEXT);
                final boolean isClicked = entity.getField(MultipleFields.TAG);
                final AppCompatTextView name = holder.getView(R.id.tv_vertical_item_name);
                final View line = holder.getView(R.id.view_line);
                // 获取整个 VIew
                final View itemView = holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 获取当前位置
                        final int currentPosition = holder.getAdapterPosition();
                        if(mPrePosition != currentPosition){
                            // 还原上一个
                            getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                            notifyItemChanged(mPrePosition);
                            // 更新选中的部分
                            entity.setField(MultipleFields.TAG, true);
                            notifyItemChanged(currentPosition);
                            // 把当前变化后的值，赋值给 mPrePosition
                            mPrePosition = currentPosition;
                            // 获取当前点击的 id
                            final int contentId =  getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });
                if (!isClicked){
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                }else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                name.setText(text);
                break;
            default:
                break;
        }
    }

    private void showContent(int contentId){
        // 写在类里面的方法，这个时候，好处就体现出来了，很方便
        final ContentDelegate delegate = ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate){
        final LatteDelegate contentDelegate = SupportHelper.findFragment(DELEGATE.getChildFragmentManager(), ContentDelegate.class);
        if (contentDelegate != null){
            contentDelegate.getSupportDelegate().replaceFragment(delegate, false);
        }
    }
}
