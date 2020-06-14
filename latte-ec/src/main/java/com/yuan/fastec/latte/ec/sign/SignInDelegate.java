package com.yuan.fastec.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.google.android.material.textfield.TextInputEditText;
import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.R2;
import com.yuan.fastec.latte.ec.constant.Constant;
import com.yuan.fastec.latte.ec.entity.UserBean;
import com.yuan.fastec.latte.net.RestClient;
import com.yuan.fastec.latte.net.callback.ISuccess;
import com.yuan.fastec.latte.util.log.LatteLogger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  登录
 */
public class SignInDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    // 声明监听注册的变量
    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListener = (ISignListener) activity;
        }
    }

    /**
     * 检查信息
     * @return
     */
    private boolean checkForm(){
        final String name = mName.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("请输入用户名");
            isPass = false;
        }else {
            mName.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        }else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){
        if (checkForm()){
            RestClient.Builder()
                    .url(Constant.user_login)
                    .params("username", mName.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("TAG", response);
                            UserBean userBean = JSON.parseObject(response, UserBean.class);
                            boolean hasData = userBean.getErrorCode() == 0 ? true : false;
                            if (hasData){
                                // 注册成功，插入数据库
                                SignHandler.onSignIn(response, mISignListener);
                                LatteLogger.d("TAG", "插入成功");
                            }else {
                                // 注册失败提示
                                Toast.makeText(getContext(), userBean.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .build()
                    .post();
        }
    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLinkSignUp(){
        // 还未注册，去注册
        start(new SignUpDelegate());
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat(){

    }
}
