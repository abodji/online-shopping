package com.ala2i.online.store.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUPPLIER")
public class Supplier {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUPPLIER_ID")
    protected Long supplierId;
	
	@Column(name = "NAME", unique = true, nullable = false)
	protected String name;
	
	/*===================== Constructors =====================*/
	
	public Supplier() {
	}

	public Supplier(String name) {
		super();
		this.name = name;
	}
	
	/*===================== Getters and Setters =====================*/
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	/*===================== Other methods =====================*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
