package com.yuan.fastec.latte.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;

import com.wang.avi.AVLoadingIndicatorView;
import com.yuan.fastec.latte.R;
import com.yuan.fastec.latte.util.DimenUtil;

import java.util.ArrayList;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class LatteLoader {

    // 宽高缩放比
    private static final int LOADER_SIZE_SCALE = 8;
    // 偏移量
    private static final int LOADER_OFFSET_SCALE = 10;
    // 创建一个集合，存储所有的 dialog ，方便统一管理
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    // 默认的 loading 样式
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStyle> loaderStyle){
        showLoading(context, loaderStyle.name());
    }

    public static void showLoading(Context context, String type){
        // 动画放在 dialog 上
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.creator(type, context);

        dialog.setContentView(avLoadingIndicatorView);

        // 控制宽高
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();
        // 创建 window 对象
         final Window dialogWindow = dialog.getWindow();
         if (dialogWindow != null){
             WindowManager.LayoutParams lp = dialogWindow.getAttributes();
             lp.width = deviceWidth/LOADER_SIZE_SCALE;
             lp.height = deviceHeight/LOADER_SIZE_SCALE;
             lp.height = lp.height + deviceHeight/LOADER_OFFSET_SCALE;
             lp.gravity = Gravity.CENTER;
         }
         // 加入集合
        LOADERS.add(dialog);
         dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null){
                if (dialog.isShowing()){
                    dialog.cancel();    // 有回调，为后面考虑
//                    dialog.dismiss();   // 无回调
                }
            }
        }
    }
}
