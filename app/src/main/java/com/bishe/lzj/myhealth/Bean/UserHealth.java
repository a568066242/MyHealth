package com.bishe.lzj.myhealth.Bean;

/**
 * Created by lzj on 2016/2/22.
 */
public class UserHealth {

    private int id;
    private double height;//单位：cm
    private double weight;//单位：公斤
    private int chuanran;

    private int jiazu;

    private String qita;

    public UserHealth() {
    }

    public UserHealth(int id, String qita, int jiazu, int chuanran, double weight, double height) {
        this.id = id;
        this.qita = qita;
        this.jiazu = jiazu;
        this.chuanran = chuanran;
        this.weight = weight;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    public int getChuanran() {
        return chuanran;
    }

    public void setChuanran(int chuanran) {
        this.chuanran = chuanran;
    }

    public int getJiazu() {
        return jiazu;
    }

    public void setJiazu(int jiazu) {
        this.jiazu = jiazu;
    }

    public String getQita() {
        return qita;
    }

    public void setQita(String qita) {
        this.qita = qita;
    }
}
