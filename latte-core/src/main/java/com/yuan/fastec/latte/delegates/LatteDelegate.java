package com.yuan.fastec.latte.delegates;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public abstract class LatteDelegate extends PermissionCheckerDelegate {

    /**
     * 返回父 Fragment
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }

}
