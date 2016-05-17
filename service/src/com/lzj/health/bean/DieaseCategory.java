package com.lzj.health.bean;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the diease_category database table.
 * 
 */
@Entity
@Table(name="diease_category")

@NamedQueries(value={@NamedQuery(name="DieaseCategory.findAll", query="SELECT d FROM DieaseCategory d"),
		@NamedQuery(name="DieaseCategory.getBigCategory",query="select d from DieaseCategory d where d.dieaseCategory is Null "),
		@NamedQuery(name="DieaseCategory.getSubCategory",query="SELECT d from DieaseCategory d where d.dieaseCategory.id= ?1")})
public class DieaseCategory implements Serializable,Comparable<DieaseCategory>{
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private Integer attr;

	private String name;

	@Transient
	@JsonIgnore
	private String url;

	//bi-directional many-to-one association to DieaseCategory
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pid")
	@JsonIgnore
	private DieaseCategory dieaseCategory;//疾病父类

	//bi-directional many-to-one association to DieaseCategory
	@OneToMany(mappedBy="dieaseCategory",fetch=FetchType.LAZY)
	@JsonIgnore
	private List<DieaseCategory> dieaseCategories;//疾病子类

	public DieaseCategory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getAttr() {
		return this.attr;
	}

	public void setAttr(int attr) {
		this.attr = attr;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public DieaseCategory getDieaseCategory() {
		return this.dieaseCategory;
	}

	public void setDieaseCategory(DieaseCategory dieaseCategory) {
		this.dieaseCategory = dieaseCategory;
	}

	public List<DieaseCategory> getDieaseCategories() {
		return this.dieaseCategories;
	}

	public void setDieaseCategories(List<DieaseCategory> dieaseCategories) {
		this.dieaseCategories = dieaseCategories;
	}

	public DieaseCategory addDieaseCategory(DieaseCategory dieaseCategory) {
		getDieaseCategories().add(dieaseCategory);
		dieaseCategory.setDieaseCategory(this);

		return dieaseCategory;
	}

	public DieaseCategory removeDieaseCategory(DieaseCategory dieaseCategory) {
		getDieaseCategories().remove(dieaseCategory);
		dieaseCategory.setDieaseCategory(null);

		return dieaseCategory;
	}

	@Override
	public String toString() {
		return "DieaseCategory [id=" + id + ", attr=" + attr + ", name=" + name
				+ ", url=" + url + "]";
	}

	@Override
	public int compareTo(DieaseCategory o) {//将其它放在最后
		if(this.getName().equals("其它"))
			return 1;
		if(o.getName().equals("其它"))
			return -1;
		return o.getName().compareTo(this.getName());
		
	}

	
	
}