/**
 * Difference between Role and GrantedAuthority in Spring Security
 * 
 *   1 - GrantedAuthority
 *    
 *   We can think of each GrantedAuthority as an individual privilege. Examples could include
 *   READ_AUTHORITY, WRITE_PRIVILEGE, CAN_EXECUTE_AS_ROOT, ... The important thing to understand 
 *   is that the name is arbitrary.
 *   When using GrantedAuthority directly, such as through the use of an expression like  
 *   hasAuthority('READ_AUTHORITY'), we are restricting access in a fined-grained manner
 *   As we can probably gather, we can refer to the concept of authority by using privilege as well.
 *   
 *   2 - Role as Authority
 * 
 *   Similarly, we can think of each Role as a coarse-grained GrantedAuthority that is represented as a String 
 *   and prefixed with 'ROLE'. When using a Role directly, such as through the use of an expression like 
 *   hasRole('ADMIN'), we are restricting access in coarse-grained manner.
 *   
 *   The core difference between these two is the semantics we attach to how we use the feature.
 *   
 *   3 - Role as Container
 *   
 *   Using Role as containers of authorities/privileges is a higher level approach to roles - making them a more 
 *   business-facing concept rather than an implementation-centric one.
 *   The Spring security framework doesn't give any guidance in terms of how we should use the concept, so the
 *   choice is entirely implementation specific.
 *   
 */
package com.ala2i.online.store.security;

/*
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter; import
 * org.springframework.security.core.userdetails.UserDetailsService;
 * 
 * public class SecurityConfig extends WebSecurityConfigurerAdapter {
 * 
 * @Autowired private UserDetailsService userDetailsService;
 * 
 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
 * Exception { auth.userDetailsService(userDetailsService); }
 * 
 * @Override protected UserDetailsService userDetailsService() { // TODO
 * Auto-generated method stub return super.userDetailsService(); }
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception { //
 * TODO Auto-generated method stub super.configure(http); }
 * 
 * }
 */