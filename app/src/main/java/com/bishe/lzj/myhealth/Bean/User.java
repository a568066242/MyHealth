package com.bishe.lzj.myhealth.Bean;

/**
 * Created by lzj on 2016/2/19.
 */
public class User {
    private int id;
    private String userName;
    private String password;
    private UserBasic userBasic;
    private UserHealth userHealth;

    public  User(){}

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public User(int id, String password, String userName) {
        this.id = id;
        this.password = password;
        this.userName = userName;
    }

    public User(int id, String userName, String password, UserBasic userBasic, UserHealth userHealth) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userBasic = userBasic;
        this.userHealth = userHealth;
    }

    public User(String userName, String password, UserBasic userBasic, UserHealth userHealth) {
        this.userName = userName;
        this.password = password;
        this.userBasic = userBasic;
        this.userHealth = userHealth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserBasic getUserBasic() {
        return userBasic;
    }

    public void setUserBasic(UserBasic userBasic) {
        this.userBasic = userBasic;
    }

    public UserHealth getUserHealth() {
        return userHealth;
    }

    public void setUserHealth(UserHealth userHealth) {
        this.userHealth = userHealth;
    }
}
