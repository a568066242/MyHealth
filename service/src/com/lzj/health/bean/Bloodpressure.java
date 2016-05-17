package com.lzj.health.bean;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.lzj.health.util.JsonDateSerializer;

import java.util.Date;


/**
 * The persistent class for the bloodpressure database table.
 * 
 */
@Entity
@NamedQuery(name="Bloodpressure.findAll", query="SELECT b FROM Bloodpressure b")
public class Bloodpressure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date date;
	
	private int highpressure;

	private int lowpressure;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USERID",referencedColumnName="ID")
	@JsonIgnore
	private Account user;

	public Bloodpressure() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getHighpressure() {
		return this.highpressure;
	}

	public void setHighpressure(int highpressure) {
		this.highpressure = highpressure;
	}

	public int getLowpressure() {
		return this.lowpressure;
	}

	public void setLowpressure(int lowpressure) {
		this.lowpressure = lowpressure;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Bloodpressure [id=" + id + ", date=" + date + ", highpressure="
				+ highpressure + ", lowpressure=" + lowpressure + ", user="
				+ user + "]";
	}
}