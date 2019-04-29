package com.ala2i.online.store.security;

import java.util.ArrayList; 
import java.util.Collection; 
import java.util.List;
  
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.authority.SimpleGrantedAuthority; 
import org.springframework.security.core.userdetails.UserDetails;
  
import com.ala2i.online.store.data.User;
  
public class CustomUserDetails extends User implements UserDetails {
  
	private static final long serialVersionUID = 1L;
  
	/*===================== Constructors =====================*/
  
	public CustomUserDetails() { 
	}
  
	public CustomUserDetails(String firstName, String lastName, String username, String password, String email, String phone, Boolean isActive, Boolean tokenExpire) {
  
		super(firstName, lastName, username, password, email, phone, isActive, tokenExpire); 
	}
  
	public CustomUserDetails(User user) { 
		super(user); 
	}
  
	/*===================== Other methods =====================*/
  
	@Override 
	public Collection<? extends GrantedAuthority> getAuthorities() {
  
		List<GrantedAuthority> authorities = new ArrayList<>();
	  
		getRoles().forEach(role -> { 
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
			
			role.getPrivileges().stream() 
				.map(privilege -> new SimpleGrantedAuthority("PERM_" + privilege.getName())) 
				.forEach(authorities::add); 		
		});
	  
		return authorities; 
	}
  
	@Override 
	public String getPassword() { 
		return super.getPassword(); 		
	}
  
	@Override 
	public String getUsername() { 
		return super.getUsername(); 
	}
  
	@Override 
	public boolean isAccountNonExpired() { 
		return true; 
	}
  
	@Override 
	public boolean isAccountNonLocked() { 
		return true; 
	}
  
	@Override 
	public boolean isCredentialsNonExpired() { 
		return true; 
	}
  
	@Override 
	public boolean isEnabled() { 
		return true; 
	}
  
  }
 