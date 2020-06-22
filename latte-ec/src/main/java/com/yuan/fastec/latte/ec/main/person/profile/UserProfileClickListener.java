package com.yuan.fastec.latte.ec.main.person.profile;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.yuan.fastec.latte.delegates.LatteDelegate;
import com.yuan.fastec.latte.ec.R;
import com.yuan.fastec.latte.ec.main.person.list.ListBean;
import com.yuan.fastec.latte.ui.date.DateDialogUtil;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class UserProfileClickListener extends SimpleClickListener {

    private LatteDelegate DELEGATE;

    private String[] mGenders = new String[]{"男", "女", "保密"};


    public UserProfileClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        // 根据传入的 id 区别
        final int id = bean.getId();
        switch (id){
            case 1:
                // 点击后，开启照相机或选择图片
                break;
            case 2 :
                // 姓名
                final LatteDelegate nameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(nameDelegate);
                break;
            case 3:
                // 性别
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);   // 先取出设置的默认值
                        textView.setText(mGenders[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                // 生日
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDateDialog(DELEGATE.getContext());
                break;
            default:
                break;
        }
    }

    /**
     * 性别选择的 dialog
     */
    private void getGenderDialog(DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders, 0, listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
