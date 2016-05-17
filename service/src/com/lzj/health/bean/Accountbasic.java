package com.lzj.health.bean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the accountbasic database table.
 * 
 */
@Entity
@NamedQuery(name="Accountbasic.findAll", query="SELECT a FROM Accountbasic a")
public class Accountbasic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int age;

	private String name;

	private String phone;

	private String sex;

	public Accountbasic() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Accountbasic [id=" + id + ", age=" + age + ", name=" + name
				+ ", phone=" + phone + ", sex=" + sex + "]";
	}
	
	

}