package com.yuan.fastec.latte.ec.entity;

/**
 * @author XYuan
 * Version  1.0
 * Description
 */
public class UserBean {

    /**
     * data : null
     * errorCode : -1
     * errorMsg : 用户名已经被注册！
     */

    private Object data;
    private int errorCode;
    private String errorMsg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
