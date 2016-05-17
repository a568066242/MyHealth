package com.lzj.health.bean;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lzj.health.util.JsonDateSerializer;

import java.util.Date;


/**
 * The persistent class for the pulse database table.
 * 
 */
@Entity
@NamedQuery(name="Pulse.findAll", query="SELECT p FROM Pulse p")
public class Pulse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date date;

	private int pulse;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userid",referencedColumnName="ID")
	@JsonIgnore
	private Account user;

	public Pulse() {
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

	public int getPulse() {
		return this.pulse;
	}

	public void setPulse(int pulse) {
		this.pulse = pulse;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	

}