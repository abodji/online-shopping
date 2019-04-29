package com.ala2i.online.store.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ROLES")
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    protected Long roleId;
	
	@NotNull(message = "Role name must not be null")
	@Size(min = 3, max = 20, message = "Role name must be between {min} - {max}")
	@Column(name = "NAME", nullable = false, unique = true)
	protected String name;
	
	@Column(name = "DESCRIPTION")
	protected String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
		name="ROLES_PRIVILEGES", 
		joinColumns={ @JoinColumn(name="ROLE_ID", foreignKey = @ForeignKey(name = "FK_RP_ROLE_ID"))},
	    inverseJoinColumns={ @JoinColumn(name="PRIVILEGE_ID", foreignKey = @ForeignKey(name = "FK_RP_PRIVILEGE_ID"))}
	) 
    protected Set<Privilege> privileges = new HashSet<>(); 
	
	/*===================== Constructors =====================*/
	
	public Role() {
	}
	
	public Role(String name) {
		this.name = name;
	} 
	
	public Role(String name, String description) {
		this.name = name;
		this.description = description;
	} 
	
	/*===================== Getters and Setters =====================*/

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	public void addPrivilege(Privilege privilege) {
		if(privilege != null)
			privileges.add(privilege);
	}
	
	public Set<Privilege> getPrivileges(){
		return privileges;
	}
	
	public void setPrivileges(Set<Privilege> privileges) {
		if(privileges != null)
			privileges.forEach(this::addPrivilege);
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
