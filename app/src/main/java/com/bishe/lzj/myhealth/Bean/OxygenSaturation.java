package com.bishe.lzj.myhealth.Bean;

import java.util.Date;

/**
 * Created by lzj on 2016/2/29.
 * 血氧饱和度
 */
public class OxygenSaturation {

    private int id;
    private int userID;
    private Date date;
    private int os;

    public OxygenSaturation() {
    }

    public OxygenSaturation(int id, int userID, Date date, int os) {
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.os = os;
    }

    public OxygenSaturation(int userID, Date date, int os) {
        this.userID = userID;
        this.date = date;
        this.os = os;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOs() {
        return os;
    }

    public void setOs(int os) {
        this.os = os;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
