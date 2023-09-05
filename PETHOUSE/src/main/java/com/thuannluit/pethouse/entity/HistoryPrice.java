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
@Table(name = "product_details")
public class HistoryPrice extends BasedEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productDetailId;
	@ManyToOne
	@JoinColumn(name = "productId", nullable = true)
	private Products products;
	@Column(name = "priceProduct")
	private int priceProduct;

	public int getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(int productDetailId) {
		this.productDetailId = productDetailId;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public int getPriceProduct() {
		return priceProduct;
	}

	public void setPriceProduct(int priceProduct) {
		this.priceProduct = priceProduct;
	}

	@Override
	public String toString() {
		return "HistoryPrice [productDetailId=" + productDetailId + ", products=" + products + ", priceProduct="
				+ priceProduct + "]";
	}

}
