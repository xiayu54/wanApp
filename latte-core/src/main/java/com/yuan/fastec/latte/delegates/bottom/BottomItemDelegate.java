package com.yuan.fastec.latte.delegates.bottom;



import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.yuan.fastec.latte.delegates.LatteDelegate;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  每一个页面 Delegate 的基类，不直接使用
 */
public abstract class BottomItemDelegate extends LatteDelegate implements View.OnKeyListener {

    private long mExitTime = 0;
    /**
     * 间隔时间
     */
    private static final int EXIT_TIME = 2000;

    @Override
    public void onResume() {
        super.onResume();
        View rootView = getView();
        if (rootView != null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // 点击了后退键
        if (keyCode == KeyEvent.KEYCODE_BACK  && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - mExitTime)  > EXIT_TIME){
                // 点击事件时间超过2秒
                Toast.makeText(getContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }else {
                // 点击很快，直接退出
                _mActivity.finish();
                if (mExitTime != 0){
                    mExitTime = 0;
                }
            }
            // 事件消化
            return true;
        }
        return false;
    }
}
