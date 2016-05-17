package com.bishe.lzj.myhealth.Bean;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by lzj on 2016/2/22.
 */
public class BloodPressure {

    private int id;
    private int userID;
    private Date date;


    @SerializedName("highpressure")
    private int SBP;//收缩压
    @SerializedName("lowpressure")
    private int DBP;//舒张压

    public BloodPressure() {
    }

    public BloodPressure(int id, int userID, Date date, int SBP, int DBP) {
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.SBP = SBP;
        this.DBP = DBP;
    }


    public BloodPressure(int userID, Date date, int SBP, int DBP) {
        this.userID = userID;
        this.date = date;
        this.SBP = SBP;
        this.DBP = DBP;
    }



    /**
     * 获取舒张压,舒张压偏小
     * @return
     */
    public int getDBP() {
        return DBP;
    }

    public void setDBP(int DBP) {
        this.DBP = DBP;
    }

    /**
     * 获取收缩压，收缩压偏大
     * @return
     */
    public int getSBP() {
        return SBP;
    }

    public void setSBP(int SBP) {
        this.SBP = SBP;
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





}
