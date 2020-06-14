package com.yuan.fastec.latte.util.timer;

import java.util.TimerTask;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  倒计时基类
 */
public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null){
            mITimerListener.onTimer();
        }
    }
}
