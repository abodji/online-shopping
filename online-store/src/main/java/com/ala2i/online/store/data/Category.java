package com.ala2i.online.store.data;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {
	
	private static final long serialVersionUID = -6647250082408396503L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CATEGORY_ID")
	protected Long categoryId;
	
	@Column(name = "NAME", nullable = false, unique = true)
	protected String name;
	
	@Column(name = "DESCRIPTION")
	protected String description;
	
	@Column(name = "IMAGE_URL")
	protected String imgUrl;
	
	@Column(name = "IS_ACTIVE")
	private Boolean active = true;
	
	/*========================= CONSTRUCTORS ============================*/
	
	public Category() {
	}
	
	public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Category(String name, String description, String imgUrl, Boolean active) {
		this(name, description);
		
		this.imgUrl = imgUrl;
		this.active = active;
	}

	/*========================= GETTERS AND SETTERS ============================*/
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public Boolean isActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}

	/*========================= OTHER METHODS ============================*/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(imgUrl);
		result = prime * result + Objects.hashCode(name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Category other = (Category) obj;
		
		if(!Objects.equals(this.imgUrl, other.imgUrl))
			return false;
		
		if(!Objects.equals(this.name, other.name))
			return false;
		
		return true;
	}
}
