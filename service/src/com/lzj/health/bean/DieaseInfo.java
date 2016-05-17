package com.lzj.health.bean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the diease_info database table.
 * 
 */
@Entity
@Table(name="diease_info")
@NamedQueries({@NamedQuery(name="DieaseInfo.findAll", query="SELECT d FROM DieaseInfo d"),
	@NamedQuery(name="DieaseInfo.find",query="select d from DieaseInfo d where d.id= ?1")})
public class DieaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String bodysym;

	private String department;

	private String name;

	private String position;

	private String prevent;

	private String reaseon;

	private String symptoms;

	public DieaseInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBodysym() {
		return this.bodysym;
	}

	public void setBodysym(String bodysym) {
		this.bodysym = bodysym;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPrevent() {
		return this.prevent;
	}

	public void setPrevent(String prevent) {
		this.prevent = prevent;
	}

	public String getReaseon() {
		return this.reaseon;
	}

	public void setReaseon(String reaseon) {
		this.reaseon = reaseon;
	}

	public String getSymptoms() {
		return this.symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

}