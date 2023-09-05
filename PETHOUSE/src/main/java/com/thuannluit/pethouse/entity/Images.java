package com.thuannluit.pethouse.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class Images extends BasedEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int imageId;
	@ManyToOne
	@JoinColumn(name = "productId")
	private Products products;
	@ManyToOne
	@JoinColumn(name = "brandId")
	private Brands brands;
	@Column(name = "urlImageProduct")
	private String urlImageProduct;
	@Column(name = "imageName")
	private String imageName;

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public Brands getBrands() {
		return brands;
	}

	public void setBrands(Brands brands) {
		this.brands = brands;
	}

	public String getUrlImageProduct() {
		return urlImageProduct;
	}

	public void setUrlImageProduct(String urlImageProduct) {
		this.urlImageProduct = urlImageProduct;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "Images [imageId=" + imageId + ", products=" + products + ", brands=" + brands + ", urlImageProduct="
				+ urlImageProduct + ", imageName=" + imageName + "]";
	}

}
