package com.yuan.fastec.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuan.fastec.latte.app.AccountManager;
import com.yuan.fastec.latte.ec.database.DatabaseManager;
import com.yuan.fastec.latte.ec.database.UserProfile;

/**
 * @author XYuan
 * Version  1.0
 * Description
 * 注册登录的辅助类，保存信息
 */
public class SignHandler {

    /**
     * 注册后的返回信息
     * "data": {
     * "admin": false,
     * "chapterTops": [],
     * "collectIds": [],
     * "email": "",
     * "icon": "",
     * "id": 66894,
     * "nickname": "xxy3",
     * "password": "",
     * "publicName": "xxy3",
     * "token": "",
     * "type": 0,
     * "username": "xxy3"
     * }
     *
     * @param response
     */
    public static void onSignUp(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long id = profileJson.getLong("id");
        final String username = profileJson.getString("username");
        final String email = profileJson.getString("email").isEmpty() ? "test@qq.com" : profileJson.getString("email");
        final String nickname = profileJson.getString("nickname");
        final String publicName = profileJson.getString("publicName");
        final long type = profileJson.getLong("type");

        final UserProfile profile = new UserProfile(id, username, email, nickname, publicName, type);
        // 插入数据
        DatabaseManager.getInstance().getDao().insert(profile);

        // 已经注册成功了，并登录了,设置用户状态为 true
        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();

    }

    /**
     * 登录
     * @param response
     * @param signListener
     */
    public static void onSignIn(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long id = profileJson.getLong("id");
        final String username = profileJson.getString("username");
        final String email = profileJson.getString("email").isEmpty() ? "test@qq.com" : profileJson.getString("email");
        final String nickname = profileJson.getString("nickname");
        final String publicName = profileJson.getString("publicName");
        final long type = profileJson.getLong("type");

        final UserProfile profile = new UserProfile(id, username, email, nickname, publicName, type);
        // 插入数据
        DatabaseManager.getInstance().getDao().insert(profile);

        // 已经注册成功了，并登录了,设置用户状态为 true
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();

    }


}
