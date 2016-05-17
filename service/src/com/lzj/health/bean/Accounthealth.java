package com.lzj.health.bean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the accounthealth database table.
 * 
 */
@Entity
@NamedQuery(name="Accounthealth.findAll", query="SELECT a FROM Accounthealth a")
public class Accounthealth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int chuanran;

	private double height;

	private int jiazu;

	private String qita;

	private double weight;

	public Accounthealth() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChuanran() {
		return this.chuanran;
	}

	public void setChuanran(int chuanran) {
		this.chuanran = chuanran;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getJiazu() {
		return this.jiazu;
	}

	public void setJiazu(int jiazu) {
		this.jiazu = jiazu;
	}

	public String getQita() {
		return this.qita;
	}

	public void setQita(String qita) {
		this.qita = qita;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Accounthealth [id=" + id + ", chuanran=" + chuanran
				+ ", height=" + height + ", jiazu=" + jiazu + ", qita=" + qita
				+ ", weight=" + weight + "]";
	}
	
	

}