package com.yuan.fastec.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.yuan.fastec.latte.app.AccountManager;
import com.yuan.fastec.latte.app.IUserChecker;
import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.R2;
import com.yuan.fastec.latte.ui.launcher.ILauncherListener;
import com.yuan.fastec.latte.ui.launcher.OnLauncherFinishTag;
import com.yuan.fastec.latte.ui.launcher.ScrollLauncherTag;
import com.yuan.fastec.latte.util.storage.LattePreference;
import com.yuan.fastec.latte.util.timer.BaseTimerTask;
import com.yuan.fastec.latte.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class LauncherDelegate extends LatteDelegate implements ITimerListener {

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvLauncherTimer;

    private Timer mTimer = null;
    // 倒计时数字
    private int mCount = 3;
    // 声明Launcher 页结束的监听
    private ILauncherListener mILauncherListener = null;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView(){
        if (mCount < 0){
            if (mTimer != null){
                mTimer.cancel();
                mTimer = null;
                checkIsShowScroll();
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }


    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task,0, 1000);
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvLauncherTimer != null){
                    mTvLauncherTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0){
                        if (mTimer != null){
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }

    /**
     * 判断是否展示引导页
     */
    private void checkIsShowScroll(){
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            // 第一次登录，进入引导页
            start(new LauncherScrollDelegate(), SINGLETASK);
        }else {
            // 检查用户是否登录了 app
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (mILauncherListener != null){
                        // 登录成功的状态
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null){
                        // 没有登录成功的状态
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }
}
