package com.example.artidemo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table (name = "Product")
public class Product {
	
	
	@Id
	@JsonProperty("id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "mallname")
	private String mallname;
	
	@Column(name = "city")
	private String city;

	@Column(name = "category")
	private String category;

	@Column(name = "category2")
	private String category2;
	
	@Column(name = "brandname")
	private String brandname;

	@Column(name = "productname")
	private String productname;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "originalprice")
	private Long originalprice;
	
	@Column(name = "offer")
	private Long offer;
	
	@Column(name = "offerprice")
	private Long offerprice;
	
	@Column(name = "offerDate")
	private Timestamp offerDate;
	
	@Column(name = "offerDateEnd")
	private Timestamp offerDateEnd;
	
	@Column(name = "shopname")
	private String shopname;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMallname() {
		return mallname;
	}

	public void setMallname(String mallname) {
		this.mallname = mallname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getOriginalprice() {
		return originalprice;
	}

	public void setOriginalprice(Long originalprice) {
		this.originalprice = originalprice;
	}

	public Long getOffer() {
		return offer;
	}

	public void setOffer(Long offer) {
		this.offer = offer;
	}

	public Long getOfferprice() {
		return offerprice;
	}

	public Timestamp getOfferstartdate() {
		return offerDate;
	}

	public void setOfferstartdate(String offerDate) {
		
		Timestamp t= Timestamp.valueOf(offerDate );
		this.offerDate = t;
	}

	public Timestamp getOfferenddate() {
		return offerDateEnd;
	}

	public void setOfferenddate(String offerDateEnd) {
		Timestamp t= Timestamp.valueOf(offerDateEnd );
		this.offerDateEnd = t;
	}

	public void setOfferprice(Long offerprice) {
		this.offerprice = this.originalprice - this.originalprice*(this.offer/100);
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
}
