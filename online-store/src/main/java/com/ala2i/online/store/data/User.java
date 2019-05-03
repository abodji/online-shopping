package com.ala2i.online.store.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USERS")
public class User implements Serializable {
	private static final long serialVersionUID = -472635925406715559L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    protected Long userId;
	
	@Column(name="FIRST_NAME", length = 100) 
	@NotNull(message = "First name must not be null")
    @Size(min = 4, max = 100, message = "First name must be between {min} - {max} characters")
    protected String firstName;
	
	@Column(name="LAST_NAME", length = 100)  
	@NotNull(message = "Last name must not be null")
    @Size(min = 4, max = 100, message = "Last name must be between {min} - {max} characters")
    protected String lastName;
    
    @Column(name="USERNAME", length = 45, nullable = false, unique = true) 
    @NotNull(message = "Username must not be null")
    @Size(min = 4, max = 100, message = "Username must be between {min} - {max} characters")
    protected String username;
    
    @Column(name="PASSWORD", nullable = false) 
    @NotNull(message = "User password must not be null")
    @Size(min = 4, max = 100, message = "User password must be between {min} - {max} characters")
    protected String password;
    
    @Column(name="EMAIL", length = 100, nullable = false, unique = true) 
    @NotNull(message = "User email address must not be null")
	@Email(message = "Invalid email provided")
	@Size(min = 6, max = 100, message = "User email address must be between {min} - {max} characters")	
    protected String email;
    
    @Column(name="PHONE", length = 100) 
    protected String phone;
    
    @Column(name="ACTIVE") 
    protected Boolean isActive = false;
    
    @Column(name="TOKEN_EXPIRE") 
    protected Boolean tokenExpire;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
		name="USERS_ROLES", 
		joinColumns={ @JoinColumn(name="USER_ID", foreignKey = @ForeignKey(name = "FK_UR_USER_ID"))},
	    inverseJoinColumns={ @JoinColumn(name="ROLE_ID", foreignKey = @ForeignKey(name = "FK_UR_ROLE_ID"))}
	) 
    protected Set<Role> roles = new HashSet<>(); 
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
		name="USERS_ADDRESS", 
		joinColumns={ @JoinColumn(name="USER_ID", foreignKey = @ForeignKey(name = "FK_UA_USER_ID"))},
	    inverseJoinColumns={ @JoinColumn(name="ADDRESS_ID", foreignKey = @ForeignKey(name = "FK_UA_ADDRESS_ID"))}
	)
    protected Set<Address> billingAddresses = new HashSet<>();
    
    /*===================== Constructors =====================*/
    
    public User() {
	}
    
    public User(String firstName, String lastName, String username, String password, String email, String phone, Boolean isActive, Boolean tokenExpire) {
		this.firstName   = firstName;
		this.lastName    = lastName;
		this.username    = username;
		this.password    = password;
		this.email       = email;
		this.phone       = phone;
		this.isActive    = isActive;
		this.tokenExpire = tokenExpire;
	}

	public User(String firstName, String lastName, String username, String password, String email, String phone, Boolean isActive, Boolean tokenExpire, Set<Address> billingAddresses) {
		
		this(firstName, lastName, username, password, email, phone, isActive, tokenExpire);
		
		this.billingAddresses = billingAddresses;
	}
	
	public User(User user) {
		this(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(), user.getIsActive(), user.getTokenExpire(), user.getBillingAddresses());
	
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

	public Boolean getIsActive() {
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
		setRoles(roles.stream());
	}
	
	public void setRoles(Role... roles) {
		setRoles(Stream.of(roles));
	}
	
	public void setRoles(Stream<Role> roles) {
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
	
	public Set<Address> getBillingAddresses() {
		return new HashSet<>(billingAddresses);
	}
	
	public void setBillingAddress(Set<Address> billingAddress) {
		if(billingAddress != null)
			billingAddress.forEach(this::addBillingAddress);
	}
	
	public void addBillingAddress(Address address) {
		if(address != null)
			billingAddresses.add(address);
	}
	
	/*===================== HashCode and Equals =====================*/

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, password, phone, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		User other = (User) obj;
		return Objects.equals(email, other.email) 
			&& Objects.equals(firstName, other.firstName)
			&& Objects.equals(lastName, other.lastName) 
			&& Objects.equals(password, other.password)
			&& Objects.equals(phone, other.phone) 
			&& Objects.equals(username, other.username);
	}
}
