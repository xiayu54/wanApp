package com.yuan.fastec.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
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
 *  注册
 */
public class SignUpDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePassword = null;

    // 声明监听注册的变量
    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListener = (ISignListener) activity;
        }
    }

    private boolean checkForm(){
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("请输入用户名");
            isPass = false;
        }else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {  // 使用系统给的校验方法
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        }else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        }else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        }else {
            mRePassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp(){
        if (checkForm()){
            RestClient.Builder()
                    .url(Constant.user_rigister)
                    .params("username", mName.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .params("repassword", mRePassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("TAG", response);
                            UserBean userBean = JSON.parseObject(response, UserBean.class);
                            boolean hasData = userBean.getErrorCode() == 0 ? true : false;
                            if (hasData){
                                // 注册成功，插入数据库
                                SignHandler.onSignUp(response, mISignListener);
                                LatteLogger.d("TAG", "插入成功");
                            }else {
                                // 注册失败提示
                                Toast.makeText(getContext(), userBean.getErrorMsg()+ "，请重新输入", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .build()
                    .post();
        }
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLinkSignIn(){
        // 已有账号，去登录
        getSupportDelegate().start(new SignInDelegate());
    }

    @Override
    public void post(Runnable runnable) {

    }
}
