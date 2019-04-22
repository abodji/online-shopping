package com.ala2i.online.store.data;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    protected Long productId;
	
	@Column(name = "CODE", nullable = false, unique = true)
	protected String code;
	
	@Column(name = "NAME", nullable = false, unique = true)
	protected String name;
	
	@Column(name = "BRAND")
	protected String brand;
	
	@Column(name = "DESCRIPTION", length = 2000)
	protected String description;
	
	@Column(name = "UNIT_PRICE")
	protected BigDecimal unitPrice;
	
	@Column(name = "QUANTITY")
	protected Integer quantity;
	
	@Column(name = "IS_ACTIVE")
	protected Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID", foreignKey = @ForeignKey(name = "FK_PRODUCT_CATEGORY_ID"))
	protected Category category;
	
	@ManyToOne
	@JoinColumn(name = "SUPPLIER_ID", foreignKey = @ForeignKey(name = "FK_PRODUCT_SUPPLIER_ID"))
	protected Supplier supplier;
	
	@Column(name = "PURCHASES")
	protected Integer purchases;
	
	@Column(name = "VIEWS")
	protected Integer views;
	
	/*===================== Constructors =====================*/
	
	public Product() {
	}
	
	public Product(String code, String name, String brand, String description, BigDecimal unitPrice, Integer quantity,
			Boolean isActive, Category category, Supplier supplier, Integer purchases, Integer views) {
		this.code = code;
		this.name = name;
		this.brand = brand;
		this.description = description;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.isActive = isActive;
		this.category = category;
		this.supplier = supplier;
		this.purchases = purchases;
		this.views = views;
	}

	
	/*===================== Getters and Setters =====================*/
	
	public Long getProductId() {
		return productId;
	}
	

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	

	public String getCode() {
		return code;
	}
	

	public void setCode(String code) {
		this.code = code;
	}
	

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	

	public String getBrand() {
		return brand;
	}
	

	public void setBrand(String brand) {
		this.brand = brand;
	}
	

	public String getDescription() {
		return description;
	}
	

	public void setDescription(String description) {
		this.description = description;
	}
	

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	

	public Integer getQuantity() {
		return quantity;
	}
	

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	

	public Boolean getIsActive() {
		return isActive;
	}
	

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	

	public Category getCategory() {
		return category;
	}
	

	public void setCategory(Category category) {
		this.category = category;
	}
	

	public Supplier getSupplier() {
		return supplier;
	}
	

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	

	public Integer getPurchases() {
		return purchases;
	}
	

	public void setPurchases(Integer purchases) {
		this.purchases = purchases;
	}
	

	public Integer getViews() {
		return views;
	}
	

	public void setViews(Integer views) {
		this.views = views;
	}
	
	public void updateViews() {
		this.views++;
	}
	
	/*===================== Other methods =====================*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(code);
		result = prime * result + Objects.hashCode(name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Product other = (Product) obj;
		if (!Objects.equals(this.code, other.code)) 
				return false;
		
		if (!Objects.equals(this.name, other.name)) 
			return false;

		return true;
	}	
}
