package com.yuan.fastec.latte.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  指导页面的holder
 */
public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {

    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
