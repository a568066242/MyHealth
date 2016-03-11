package com.bishe.lzj.myhealth.Bean;

import java.util.Date;

/**
 * Created by lzj on 2016/2/29.
 * 心率
 */
public class HeartRate {

    private int id;
    private int userID;
    private Date date;
    private int rate;


    public HeartRate() {
    }

    public HeartRate(int userID, Date date, int rate) {
        this.userID = userID;
        this.date = date;
        this.rate = rate;
    }

    public HeartRate(int id, int userID, Date date, int rate) {
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
