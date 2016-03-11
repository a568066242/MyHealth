package com.bishe.lzj.myhealth.Bean;

/**
 * Created by lzj on 2016/2/22.
 */
public class UserBasic {



    private int id;
    private String name;
    private String sex;
    private int age;
    private String phone;//phone number

    public UserBasic(){}

    public UserBasic(int id, String name, String sex, int age, String phone) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
    }

    public UserBasic(String name, String sex, int age, String phone) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
