package com.ala2i.online.store.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController implements ErrorController {

	private static final String PATH = "/error";
	
	@RequestMapping(value = {"/", "/home", "index"})
	public String index() {
		return "/pages/home";
	}
	
	@Override
	public String getErrorPath() {
		return PATH;
	}
	
	@RequestMapping("/error")
	public String error() {
		return "pages/error";
	}
}
