package com.bishe.lzj.myhealth.Bean;

import java.util.Date;

/**
 * Created by lzj on 2016/2/22.
 */
public class BloodSugar {

    private int id;
    private int userId;
    private Date date;
    private double BS;


    public BloodSugar() {
    }

    public BloodSugar(int id, double BS, Date date, int userId) {
        this.id = id;
        this.BS = BS;
        this.date = date;
        this.userId = userId;
    }


    public BloodSugar(int userId, double BS, Date date) {
        this.userId = userId;
        this.BS = BS;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getBS() {
        return BS;
    }

    public void setBS(double BS) {
        this.BS = BS;
    }
}
