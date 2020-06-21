package com.yuan.fastec.latte.ec.detatil;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.R2;
import com.yuan.fastec.latte.ui.recycler.ItemType;
import com.yuan.fastec.latte.ui.recycler.MultipleFields;
import com.yuan.fastec.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class ImageDelegate extends LatteDelegate {

    @BindView(R2.id.rv_image_container)
    RecyclerView mRecyclerView = null;

    private static final String ARGS_PICTURES = "ARGS_PICTURES";


    public static ImageDelegate create(ArrayList pictures){
        final Bundle args = new Bundle();
        args.putStringArrayList(ARGS_PICTURES, pictures);
        final ImageDelegate delegate = new ImageDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    private void initImages(){
        final ArrayList<String> pictures = getArguments().getStringArrayList(ARGS_PICTURES);
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final int size;
        if (pictures != null){
            size = pictures.size();
            for (int i = 0; i <size; i++){
                final String imageUrl = pictures.get(i);
                final MultipleItemEntity entity = MultipleItemEntity
                        .builder()
                        .setItemType(ItemType.SINGLE_BIG_IMAGE)
                        .setField(MultipleFields.IMAGER_URL, imageUrl)
                        .build();
                entities.add(entity);
            }
            final RecyclerViewImageAdapter adapter = new RecyclerViewImageAdapter(entities);
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_image;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        initImages();
    }

    @Override
    public void post(Runnable runnable) {

    }
}
