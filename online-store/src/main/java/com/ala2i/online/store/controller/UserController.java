package com.ala2i.online.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ala2i.online.store.data.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/manage/users")
	public String showUsers(Model model) {
		
		model.addAttribute("showUsers", true);
		model.addAttribute("users", userService.getUsers());
		
		return "/management/user/users";
	}
	
}
