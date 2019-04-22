package com.ala2i.online.store.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "ROLES")
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    protected Long roleId;
	
	@Column(name = "NAME", nullable = false, unique = true)
	protected String name;
	
	@ManyToMany(mappedBy = "roles")
	protected Set<User> users;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
	
	public void addUser(User user) {
		if(user != null)
			users.add(user);
	}
	
	public void addPrivilege(Privilege privilege) {
		if(privilege != null)
			privileges.add(privilege);
	}
	
	public Set<User> getUsers(){
		return users;
	}
	
	public void setUsers(Set<User> users) {
		if(users != null)
			users.forEach(this::addUser);
	}
	
	public Set<Privilege> getPrivileges(){
		return privileges;
	}
	
	public void setPrivileges(Set<Privilege> privileges) {
		if(privileges != null)
			privileges.forEach(this::addPrivilege);
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
