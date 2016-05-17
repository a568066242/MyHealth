package com.lzj.health.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(length=30,unique = true)
	@JsonIgnore
	private String email;
	
	@Column(nullable=false,unique = true,name="NAME")
	private String userName;
	@Column(length=11)
	@JsonIgnore
	private String phone;
	@Column(nullable=false)
	private String pwd;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
	@JsonIgnore
	private List<Bloodpressure> bloods = new ArrayList<>();
	
	
	
	
	
	public Account() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public List<Bloodpressure> getBloods() {
		return bloods;
	}
	
	public void setBloods(List<Bloodpressure> bloods) {
		this.bloods = bloods;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", email=" + email + ", userName="
				+ userName + ", phone=" + phone + ", pwd=" + pwd + ", bloods="
				+ bloods + "]";
	}
	
	
	
}