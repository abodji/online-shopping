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

import com.ala2i.online.store.data.Role;
import com.ala2i.online.store.data.service.PrivilegeService;
import com.ala2i.online.store.data.service.RoleService;

@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PrivilegeService privilegeService;
	/**
	 *  List all roles in the administration area
	 *  
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/manage/roles")
	public String showRoles(Model model) {
		
		model.addAttribute("activePage", true);
		model.addAttribute("role", new Role());
		model.addAttribute("privileges", privilegeService.getAllPrivileges());
		model.addAttribute("roles", roleService.getAllRoles());
		
		return "/management/security/role/roles";
	}
	
	/**
	 * Receives a post request, validates data and saves it into the database
	 * @param role
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/manage/roles")
	public String saveRole(@Valid Role role, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("hasErrors", true);
			model.addAttribute("roles", roleService.getAllRoles());
			
			return "/management/security/role/roles";
		} 
		
		if(role.getRoleId() == null && roleService.exists(role)) {
			model.addAttribute("roleExist", true);
			model.addAttribute("hasErrors", true);
			model.addAttribute("roles", roleService.getAllRoles());		
			
			return "/management/security/role/roles";
		}
		
		roleService.save(role);
		return "redirect:/manage/roles";
	}
	
	/**
	 * Gets a particular role to edit
	 * 
	 * @param roleId The id of the role to edit
	 * @return A role to edit in JSON format
	 */
	@RequestMapping("/manage/roles/{roleId}/edit")
	@ResponseBody
	public Role editRole(@PathVariable(name = "roleId") long roleId) {
		Role role = null;
		
		try {
			role = roleService.getRole(roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return role;
	}
	
	/**
	 * Receives a GET request and deletes a role of the given id
	 * 
	 * @param roleId The ID of the role to delete
	 * @return
	 */
	@RequestMapping("/manage/roles/{roleId}/delete")
	public String deleteRole(@PathVariable(name = "roleId") long roleId) {
		roleService.delete(roleId);
		
		return "redirect:/manage/roles";
	}
	
	/**
	 * Receives a GET request and deletes roles of the given IDs
	 * @param ids IDs of the roles to delete
	 * @return
	 */
	@RequestMapping("/manage/roles/{ids}/delete/selected")
	public String deleteSelectedRoles(@PathVariable(name = "ids") String ids) {
		
		String[] selectedIds = ids.split("_");
		roleService.deleteSelected(selectedIds);
		
		return "redirect:/manage/roles";
	}	
	
	/**
	 * Receives a GET request and deletes all the roles
	 * @return
	 */
	@RequestMapping("/manage/roles/delete/all")
	public String deleteAllRoles() {
		
		roleService.deleteAll();
		
		return "redirect:/manage/roles";
	}
}
