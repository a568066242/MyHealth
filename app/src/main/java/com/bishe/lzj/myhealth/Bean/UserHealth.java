package com.bishe.lzj.myhealth.Bean;

/**
 * Created by lzj on 2016/2/22.
 */
public class UserHealth {

    private int id;
    private double height;//单位：cm
    private double weight;//单位：公斤

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
}
