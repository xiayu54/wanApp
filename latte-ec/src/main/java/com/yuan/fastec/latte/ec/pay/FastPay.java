package com.yuan.fastec.latte.ec.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class FastPay implements View.OnClickListener {

    // 设置支付监听
    private IAliPayResultListener mAliPayResultListener = null;
    private Activity mActivity = null;
    // 看看弹出框
    private AlertDialog mDialog = null;
    private int mOrderId = -1;


    private FastPay(LatteDelegate delegate){
        this.mActivity = delegate.getProxyActivity();
        // 系统的 AlertDialog 用的就是 Builder 设计模式
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    // 简单工厂
    public static FastPay create(LatteDelegate delegate){
        return new FastPay(delegate);
    }

    /**
     * 开启弹框
     */
    public void beginPayDialog(){
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null){
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);  // 动画
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 透明
            // 设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_dialog_pay_alipay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        // library 里面不能使用 switch
        int id = v.getId();
        if (id == R.id.btn_dialog_pay_alipay){
            Toast.makeText(mActivity, "支付宝支付正在启动", Toast.LENGTH_SHORT).show();
            mDialog.cancel();
        }else if (id == R.id.btn_dialog_pay_wechat){
            Toast.makeText(mActivity, "微信支付正在启动", Toast.LENGTH_SHORT).show();
            mDialog.cancel();
        }else if (id == R.id.btn_dialog_pay_cancel){
            Toast.makeText(mActivity, "支付取消", Toast.LENGTH_SHORT).show();
            mDialog.cancel();
        }
    }
}
