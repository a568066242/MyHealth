package com.bishe.lzj.myhealth.Bean;

import java.util.Date;

/**
 * Created by lzj on 2016/2/29.
 * 脉搏
 */
public class Pulse {
    private int id;
    private int userID;
    private Date date;
    private int pulse;

    public Pulse() {
    }

    public Pulse(int id, int userID, Date date, int pulse) {
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.pulse = pulse;
    }

    public Pulse(int userID, int pulse, Date date) {
        this.userID = userID;
        this.pulse = pulse;
        this.date = date;
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

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }
}
