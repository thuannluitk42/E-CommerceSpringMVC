package com.thuannluit.pethouse.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Products extends BasedEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;
	@Column(name = "productName")
	private String productName;
	@Column(name = "shortDescription")
	private String shortDescription;
	@Column(name = "fullDescription")
	private String fullDescription;
	@Column(name = "inStock")
	private int inStock;

	@OneToMany(mappedBy = "products")
	private Set<Images> listProductImage;

	@OneToMany(mappedBy = "products")
	private Set<HistoryPrice> listPriceProductHistory;

	@ManyToOne
	@JoinColumn(name = "brandId")
	private Brands brands;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Categories categories;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public Set<Images> getListProductImage() {
		return listProductImage;
	}

	public void setListProductImage(Set<Images> listProductImage) {
		this.listProductImage = listProductImage;
	}

	public Set<HistoryPrice> getListPriceProductHistory() {
		return listPriceProductHistory;
	}

	public void setListPriceProductHistory(Set<HistoryPrice> listPriceProductHistory) {
		this.listPriceProductHistory = listPriceProductHistory;
	}

	public Brands getBrands() {
		return brands;
	}

	public void setBrands(Brands brands) {
		this.brands = brands;
	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

}
