package com.yuan.fastec.example;



import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.yuan.fastec.latte.activities.ProxyActivity;
import com.yuan.fastec.latte.app.latte;
import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.launcher.LauncherDelegate;
import com.yuan.fastec.latte.ec.launcher.LauncherScrollDelegate;
import com.yuan.fastec.latte.ec.sign.ISignListener;
import com.yuan.fastec.latte.ec.sign.SignInDelegate;
import com.yuan.fastec.latte.ec.sign.SignUpDelegate;
import com.yuan.fastec.latte.ui.launcher.ILauncherListener;
import com.yuan.fastec.latte.ui.launcher.OnLauncherFinishTag;
import com.yuan.fastec.latte.util.log.LatteLogger;

public class MainActivity extends ProxyActivity implements ISignListener, ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        // 注册成功
        Toast.makeText(this, "登录成功了！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpSuccess() {
        // 注册成功
        Toast.makeText(this, "注册成功了！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                // 已经登录
                Toast.makeText(this, "启动结束，登录了", Toast.LENGTH_SHORT).show();
                // 登录了，进入主页
                startWithPop(new ExampleDelegate());
                break;
            case NOT_SIGNED:
                // 未登录
                Toast.makeText(this, "启动结束，用户未登录", Toast.LENGTH_SHORT).show();
                // 启动后将栈中删一个元素彻底清除，引导页看一下，就没用了
                startWithPop(new SignUpDelegate());
                break;
            default:
                break;
        }
    }
}
