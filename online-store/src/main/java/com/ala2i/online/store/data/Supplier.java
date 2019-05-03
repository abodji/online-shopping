package com.ala2i.online.store.data;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SUPPLIER")
public class Supplier {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUPPLIER_ID")
    protected Long supplierId;
	
	@NotNull(message = "Supplier name must not be null")
	@Size(min = 2, max = 45, message = "Supplier name must be between {min} - {max} characters")
	@Column(name = "NAME", unique = true, nullable = false)
	protected String name;
	
	@NotNull(message = "Supplier email must not be null")
	@Email(message = "Invalid email provided")
	@Size(min = 6, max = 100, message = "Supplier email must be between {min} - {max} characters")
	@Column(name = "EMAIL", unique = true, nullable = false)
	protected String email;
	
	@NotNull(message = "Supplier telephone number must not be null")
	@Size(min = 5, max = 100, message = "Supplier telephone number must be between {min} - {max} characters")
	@Column(name = "PHONE", nullable = false)
	protected String phone;
	
	/*===================== Constructors =====================*/
	
	public Supplier() {
	}
	
	public Supplier(String name, String email, String phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/*===================== Other methods =====================*/

	@Override
	public int hashCode() {
		return Objects.hash(email, name, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Supplier other = (Supplier) obj;
		return Objects.equals(email, other.email) 
			&& Objects.equals(name, other.name)
			&& Objects.equals(phone, other.phone);
	}	
}
