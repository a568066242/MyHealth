package com.lzj.health.bean;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lzj.health.util.JsonDateSerializer;

import java.util.Date;


/**
 * The persistent class for the oxygensaturation database table.
 * 
 */
@Entity
@NamedQuery(name="Oxygensaturation.findAll", query="SELECT o FROM Oxygensaturation o")
public class Oxygensaturation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date date;

	private int os;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userid",referencedColumnName="ID")
	@JsonIgnore
	private Account user;
	
	public Oxygensaturation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getOs() {
		return this.os;
	}

	public void setOs(int os) {
		this.os = os;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	

}