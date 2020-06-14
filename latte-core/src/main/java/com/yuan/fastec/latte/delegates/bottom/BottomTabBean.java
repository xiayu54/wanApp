package com.yuan.fastec.latte.delegates.bottom;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  bottom 每一个 item tab 的bean类
 */
public final class BottomTabBean {
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getICON() {
        return ICON;
    }

    public CharSequence getTITLE() {
        return TITLE;
    }
}
