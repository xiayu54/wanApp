package com.yuan.fastec.latte.ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author XYuan
 * Version  1.0
 * Description
 *  用户信息
 */
@Entity(nameInDb = "user_profile")
public class UserProfile {
    @Id
    private long id = 0;
    private String username = null;
    private String email = null;
    private String nickname = null;
    private String publicName = null;
    private long type = 0;

    @Generated(hash = 2076181328)
    public UserProfile(long id, String username, String email, String nickname,
            String publicName, long type) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.publicName = publicName;
        this.type = type;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPublicName() {
        return this.publicName;
    }
    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }
    public long getType() {
        return this.type;
    }
    public void setType(long type) {
        this.type = type;
    }
}
