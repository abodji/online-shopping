package com.ala2i.online.store.data;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PRIVILEGES")
public class Privilege {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRIVILEGE_ID")
    protected Long privilegeId;
	
	@Column(name = "NAME", nullable = false, unique = true)
	protected String name;
	
	@ManyToMany(mappedBy = "privileges")
	protected Set<Role> roles; 
	
	/*===================== Constructors =====================*/
	
	public Privilege() {
	}
	
	public Privilege(String name) {
		this.name = name;
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
	
	public void addRole(Role role) {
		if(role != null)
			roles.add(role);
	}
	
	public Set<Role> getRoles(){
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		if(roles != null)
			roles.forEach(this::addRole);
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
