package com.ala2i.online.store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ala2i.online.store.data.User;
import com.ala2i.online.store.data.service.AddressService;
import com.ala2i.online.store.data.service.RoleService;
import com.ala2i.online.store.data.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 *  List all users in the administration area
	 *  
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/manage/users")
	public String showUsers(Model model) {
		
		model.addAttribute("activePage", true);
		model.addAttribute("user", new User());
		model.addAttribute("addresses", addressService.getAllAddresses());
		model.addAttribute("roles", roleService.getAllRoles());
		model.addAttribute("users", userService.getAllUsers());
		
		return "/management/security/user/users";
	}
	
	/**
	 * Receives a post request, validates data and saves it into the database
	 * @param user
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/manage/users")
	public String saveUser(@Valid User user, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("hasErrors", true);
			model.addAttribute("addresses", addressService.getAllAddresses());
			model.addAttribute("roles", roleService.getAllRoles());
			model.addAttribute("users", userService.getAllUsers());
			
			return "/management/security/user/users";
		} 
		
		if(user.getUserId() == null && userService.exists(user)) {
			model.addAttribute("userExist", true);
			model.addAttribute("hasErrors", true);
			model.addAttribute("addresses", addressService.getAllAddresses());
			model.addAttribute("roles", roleService.getAllRoles());
			model.addAttribute("users", userService.getAllUsers());		
			
			return "/management/security/user/users";
		}
		
		userService.save(user);
		
		return "redirect:/manage/users";
	}
	
	/**
	 * Gets a particular user to edit
	 * 
	 * @param userId The id of the user to edit
	 * @return A user to edit in JSON format
	 */
	@RequestMapping("/manage/users/{userId}/edit")
	@ResponseBody
	public User editUser(@PathVariable(name = "userId") long userId) {
		User user = null;
		
		try {
			user = userService.getUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	@RequestMapping("/manage/users/{userId}/activate")
	@ResponseBody
	public String activateUser(@PathVariable("userId") Long userId) {
		String message = "";
		
		
		try {
			User user = userService.getUser(userId);
			user.setIsActive(!user.getIsActive());
			userService.save(user);
			message = "success";
		} catch (Exception e) {
			message = e.getMessage();
		}
		return message;
	}
	
	/**
	 * Receives a GET request and deletes a user of the given id
	 * 
	 * @param userId The ID of the user to delete
	 * @return
	 */
	@RequestMapping("/manage/users/{userId}/delete")
	public String deleteUser(@PathVariable(name = "userId") long userId) {
		addressService.deleteByUser(userId);
		userService.delete(userId);
		
		return "redirect:/manage/users";
	}
	
	/**
	 * Receives a GET request and deletes users of the given IDs
	 * @param ids IDs of the users to delete
	 * @return
	 */
	@RequestMapping("/manage/users/{ids}/delete/selected")
	public String deleteSelectedUsers(@PathVariable(name = "ids") String ids) {
		
		String[] selectedIds = ids.split("_");
		addressService.deleteSelectedByUser(selectedIds);
		userService.deleteSelected(selectedIds);
		
		return "redirect:/manage/users";
	}	
	
	/**
	 * Receives a GET request and deletes all the users
	 * 
	 * @return
	 */
	@RequestMapping("/manage/users/delete/all")
	public String deleteAllUsers() {
		
		userService.deleteAll();
		
		return "redirect:/manage/users";
	}
	
}
