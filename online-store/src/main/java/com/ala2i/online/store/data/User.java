package com.ala2i.online.store.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User implements Serializable {
	private static final long serialVersionUID = -472635925406715559L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    protected Long userId;
	
	@Column(name="FIRST_NAME", length = 100) 
    protected String firstName;
	
	@Column(name="LAST_NAME", length = 100) 
    protected String lastName;
    
    @Column(name="USERNAME", length = 45, nullable = false, unique = true) 
    protected String username;
    
    @Column(name="PASSWORD", nullable = false) 
    protected String password;
    
    @Column(name="EMAIL", length = 100, nullable = false, unique = true) 
    protected String email;
    
    @Column(name="PHONE", length = 100) 
    protected String phone;
    
    @Column(name="ACTIVE") 
    protected Boolean isActive;
    
    @Column(name="TOKEN_EXPIRE") 
    protected Boolean tokenExpire;
    
    @ManyToMany
    @JoinTable(
		name="USERS_ROLES", 
		joinColumns={ @JoinColumn(name="USER_ID", foreignKey = @ForeignKey(name = "FK_UR_USER_ID"))},
	    inverseJoinColumns={ @JoinColumn(name="ROLE_ID", foreignKey = @ForeignKey(name = "FK_UR_ROLE_ID"))}
	) 
    protected Set<Role> roles = new HashSet<>(); 
    
    @ManyToMany
    @JoinTable(
		name="USERS_ADDRESS", 
		joinColumns={ @JoinColumn(name="USER_ID", foreignKey = @ForeignKey(name = "FK_UA_USER_ID"))},
	    inverseJoinColumns={ @JoinColumn(name="ADDRESS_ID", foreignKey = @ForeignKey(name = "FK_UA_ADDRESS_ID"))}
	)
    protected List<Address> billingAddresses = new ArrayList<>();
    
    /*===================== Constructors =====================*/
    
    public User() {
	}
    
    public User(String firstName, String lastName, String username, String password, String email, String phone, Boolean isActive, Boolean tokenExpire) {
		this.firstName = firstName;
		this.lastName  = lastName;
		this.username  = username;
		this.password  = password;
		this.email     = email;
		this.phone     = phone;
		this.isActive  = isActive;
		this.tokenExpire = tokenExpire;
	}

	public User(String firstName, String lastName, String username, String password, String email, String phone, Boolean isActive, Boolean tokenExpire, List<Address> billingAddresses) {
		
		this(firstName, lastName, username, password, email, phone, isActive, tokenExpire);
		
		this.billingAddresses = billingAddresses;
	}
	
	public User(User user) {
		this(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(), user.isActive(), user.getTokenExpire(), user.getBillingAddresses());
	
		this.roles = user.getRoles();
	}	
	
	/*===================== Getters and Setters =====================*/

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFullName() {
		return String.format("%s %s", firstName, lastName);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean isActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	
	public void addRole(Role role) {
		if(role != null)
			this.roles.add(role);
	}

	public void setRoles(Set<Role> roles) {
		if(roles != null)
			roles.forEach(this::addRole);
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Boolean getTokenExpire() {
		return tokenExpire;
	}
	
	public void setTokenExpire(Boolean tokenExpire) {
		this.tokenExpire = tokenExpire;
	}
	
	public List<Address> getBillingAddresses() {
		return new ArrayList<>(billingAddresses);
	}
	
	public void setBillingAddress(List<Address> billingAddress) {
		if(billingAddress != null)
			billingAddress.forEach(this::addBillingAddress);
	}
	
	public void addBillingAddress(Address address) {
		if(address != null)
			billingAddresses.add(address);
	}
	
	/*===================== Hashcode and Equals =====================*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
