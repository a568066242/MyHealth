package com.lzj.health.bean;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lzj.health.util.JsonDateSerializer;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the bloodsugar database table.
 * 
 */
@Entity
@NamedQuery(name="Bloodsugar.findAll", query="SELECT b FROM Bloodsugar b")
public class Bloodsugar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date date;

	private double sugar;

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userid",referencedColumnName="ID")
	@JsonIgnore
	private Account user;

	public Bloodsugar() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getSugar() {
		return this.sugar;
	}

	public void setSugar(double sugar) {
		this.sugar = sugar;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	

}