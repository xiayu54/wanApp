package com.yuan.fastec.latte.ui.recycler.divide;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class DividerLookUpImpl implements DividerItemDecoration.DividerLookup {

    private final int COLOR;
    private final int SIZE;

    public DividerLookUpImpl(int color, int size) {
        this.COLOR = color;
        this.SIZE = size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder()
                .size(SIZE)
                .color(COLOR)
                .build();
    }
}
