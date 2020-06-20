package com.yuan.fastec.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.yuan.fastec.latte.app.AccountManager;
import com.yuan.fastec.latte.app.IUserChecker;
import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ui.launcher.ILauncherListener;
import com.yuan.fastec.latte.ui.launcher.LauncherHolderCreator;
import com.yuan.fastec.latte.ui.launcher.OnLauncherFinishTag;
import com.yuan.fastec.latte.ui.launcher.ScrollLauncherTag;
import com.yuan.fastec.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  引导页
 */
public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    // 声明Launcher 页结束的监听
    private ILauncherListener mILauncherListener = null;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }
    }


    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    /**
     * 初始化 banner
     */
    private void initBanner(){
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false); // 不可循环
    }

    @Override
    public void onItemClick(int position) {
        if (position == INTEGERS.size() - 1){
            // 引导页滑动到最后一页，将标记置为 true
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            // 检查用户是否已经登录
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

    @Override
    public void post(Runnable runnable) {

    }
}
