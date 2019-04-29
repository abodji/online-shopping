package com.ala2i.online.store.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PRIVILEGES")
public class Privilege {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRIVILEGE_ID")
    protected Long privilegeId;
	
	@NotNull(message = "Privilege name must not be null")
	@Size(min = 3, max = 20, message = "Privilege name must be between {min} - {max}")
	@Column(name = "NAME", nullable = false, unique = true)
	protected String name;
	
	@Column(name = "DESCRIPTION")
	protected String description;
	
	/*===================== Constructors =====================*/
	
	public Privilege() {
	}
	
	public Privilege(String name) {
		this.name = name;
	} 
	
	public Privilege(String name, String description) {
		this.name = name;
		this.description = description;
	} 
	
	/*===================== Getters and Setters =====================*/

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
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
	
	/*===================== Hashcode and Equals =====================*/

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
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Role other = (Role) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
