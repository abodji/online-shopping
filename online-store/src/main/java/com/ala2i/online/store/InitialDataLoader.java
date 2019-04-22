package com.ala2i.online.store;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ala2i.online.store.data.Address;
import com.ala2i.online.store.data.AddressType;
import com.ala2i.online.store.data.Category;
import com.ala2i.online.store.data.Country;
import com.ala2i.online.store.data.Privilege;
import com.ala2i.online.store.data.Product;
import com.ala2i.online.store.data.Role;
import com.ala2i.online.store.data.Supplier;
import com.ala2i.online.store.data.User;
import com.ala2i.online.store.data.repository.AddressRepository;
import com.ala2i.online.store.data.service.CategoryService;
import com.ala2i.online.store.data.service.CountryService;
import com.ala2i.online.store.data.service.PrivilegeService;
import com.ala2i.online.store.data.service.ProductService;
import com.ala2i.online.store.data.service.RoleService;
import com.ala2i.online.store.data.service.SupplierService;
import com.ala2i.online.store.data.service.UserService;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	boolean alreadySetup = true;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SupplierService supplierService;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(alreadySetup)
			return;
		
		Privilege readPrivilege  = createPrivilegeIfNotFound("READ_PRIVILEGE");
		Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
		
		List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
	
		Role adminRole =  createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
		Role userRole  = createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
		
		Country gabon = createCountryIfNotFound(new Country("GABON", "GAB", "GA", "Libreville", "Africa", "Central Africa", ""));
		Country togo  = createCountryIfNotFound(new Country("TOGO", "TGO", "TG", "Lome", "Africa", "West Africa", ""));
		
		Address address  = addressRepository.save(new Address(AddressType.Commercial, "SAAS GABON", "LEON MBA", "Libreville", gabon, "Estuaire", "00"));
		Address address2 = addressRepository.save(new Address(AddressType.Commercial, "Good Tree Consulting", "Rong Maman NDanida", "Lome", togo, "Maritime", "00"));
		
		User user = new User("Alassani", "ABODJI", "ala2i", "ala2i", "abodjialassani@gmail.com", "70 46 70 51", true, false, Arrays.asList(address));
		user.setRoles(new HashSet<>(Arrays.asList(adminRole)));
		createUserIfNotFound(user);
		
		User user2 = new User("Nimatou", "SALIFOU", "nima", "nima", "salifounimatou@gmail.com", "93 16 87 02", true, false, Arrays.asList(address2));
		user.setRoles(new HashSet<>(Arrays.asList(adminRole)));
		createUserIfNotFound(user2);
		
		User user3 = new User("Aminou", "ABODJI", "baros", "baros", "abodjiaminou@gmail.com", "91 84 81 46", true, false, Arrays.asList(address2));
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		createUserIfNotFound(user3);
		
		Category computer = categoryService.save(new Category("Computer", "Computers: laptops, desktops"));
		Category tv = categoryService.save(new Category("TV", "All kinds of televisions"));
		Category phone = categoryService.save(new Category("Phone", "Mobile phone, telephone, smart phone"));
		
		Supplier alasko = createSupplierIfNotFound("Alasko");
		Supplier petitParis = createSupplierIfNotFound("Petit Paris");
		
		Product iphone5 = createProductIfNotFound(new Product("5PRDIPHONE", "iphone 5", "apple", "This is the one of the best phone available in the market", new BigDecimal(700_000), 25, true, phone, alasko, 0, 0));
		Product samsungS7 = createProductIfNotFound(new Product("7PRDSAMSUNG", "Samsung S7", "samsung", "A smart phone by Samsung", new BigDecimal(80_000), 5, true, phone, alasko, 0, 0));
		Product googlepx = createProductIfNotFound(new Product("PRDGPX", "Google Pixel", "google", "This is the one of the best android phone available in the market", new BigDecimal(120_000), 2, true, phone, alasko, 0, 0));
		Product macbookpro = createProductIfNotFound(new Product("PROPRDMACBOOK", "Mac Book Pro", "apple", "This is the one of the best laptops available in the market", new BigDecimal(1_100_000), 7, true, computer, petitParis, 0, 0));
		Product delllatitude = createProductIfNotFound(new Product("DELLPRDLATITUDE", "Dell Latitude E6510", "dell", "This is the one of the best laptop series available in the market", new BigDecimal(550_000), 25, true, computer, alasko, 0, 0));
		
		alreadySetup = true;
	}

	@Transactional
	private Role createRoleIfNotFound(String name, List<Privilege> privileges) {
		Role role = null;
		try {
			role = roleService.getRoleByName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		if(role == null) {
			role = new Role(name);
			role.setPrivileges(new HashSet<>(privileges));
			roleService.save(role);
		}
		return role;
	}

	@Transactional
	private Privilege createPrivilegeIfNotFound(String name) {
		Privilege privilege = null;
		try {
			privilege = privilegeService.getPrivilegeByName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		if(privilege == null) {
			privilege = new Privilege(name);
			privilegeService.save(privilege);
		}
		
		return privilege;
	}
	
	@Transactional
	private User createUserIfNotFound(User user) {
		User dbUser = null;
		try {
			dbUser = userService.getUserByUsername(user.getUsername());
		} catch (Exception e) {
		}
		
		if(dbUser == null) {
			dbUser = userService.save(user);
		}
		
		return dbUser;
	}
	
	@Transactional
	private Country createCountryIfNotFound(Country country) {
		Country dbCountry = null;
		
		try {
			dbCountry = countryService.getCountry(country.getName());
		} catch (Exception e) {
		}
		
		if(dbCountry == null) {
			dbCountry = countryService.save(country);
		}
		
		return dbCountry;
	}

	private Product createProductIfNotFound(Product product) {
		Product dbProduct = null;
		
		try {
			dbProduct = productService.getProductByCode(product.getCode());
		} catch (Exception e) {
		}
		
		if(dbProduct == null) {
			dbProduct = productService.save(product);
		}
		
		return dbProduct;
	}

	private Supplier createSupplierIfNotFound(String name) {
		Supplier dbSupplier = null;
		
		try {
			dbSupplier = supplierService.getSupplierByName(name);
		} catch (Exception e) {
		}
		
		if(dbSupplier == null) {
			dbSupplier = supplierService.save(new Supplier(name));
		}
		
		return dbSupplier;
	}

}
