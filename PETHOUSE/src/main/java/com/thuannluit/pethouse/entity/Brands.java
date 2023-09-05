package com.thuannluit.pethouse.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "brands")
public class Brands extends BasedEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int brandId;
	@Column(name = "brandName")
	private String brandName;
	@OneToMany(mappedBy = "brands")
	private Set<Images> listBrandImage;

	@OneToMany(mappedBy = "brands")
	private Set<Products> listProducts;

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Set<Images> getListBrandImage() {
		return listBrandImage;
	}

	public void setListBrandImage(Set<Images> listBrandImage) {
		this.listBrandImage = listBrandImage;
	}

	public Set<Products> getListProducts() {
		return listProducts;
	}

	public void setListProducts(Set<Products> listProducts) {
		this.listProducts = listProducts;
	}

}
