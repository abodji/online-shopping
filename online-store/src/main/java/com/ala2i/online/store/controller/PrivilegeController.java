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

import com.ala2i.online.store.data.Privilege;
import com.ala2i.online.store.data.service.PrivilegeService;

@Controller
public class PrivilegeController {
	
	@Autowired
	private PrivilegeService privilegeService;
	
	/**
	 *  List all privileges in the administration area
	 *  
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/manage/privileges")
	public String showPrivileges(Model model) {
		
		model.addAttribute("activePage", true);
		model.addAttribute("privilege", new Privilege());
		model.addAttribute("privileges", privilegeService.getAllPrivileges());
		
		return "/management/security/privilege/privileges";
	}
	
	/**
	 * Receives a post request, validates data and saves it into the database
	 * @param privilege
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/manage/privileges")
	public String savePrivilege(@Valid Privilege privilege, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("hasErrors", true);
			model.addAttribute("privileges", privilegeService.getAllPrivileges());
			
			return "/management/security/privilege/privileges";
		} 
		
		if(privilege.getPrivilegeId() == null && privilegeService.exists(privilege)) {
			model.addAttribute("privilegeExist", true);
			model.addAttribute("hasErrors", true);
			model.addAttribute("privileges", privilegeService.getAllPrivileges());		
			
			return "/management/security/privilege/privileges";
		}
		
		privilegeService.save(privilege);
		return "redirect:/manage/privileges";
	}
	
	/**
	 * Gets a particular privilege to edit
	 * 
	 * @param privilegeId The id of the privilege to edit
	 * @return A privilege to edit in JSON format
	 */
	@RequestMapping("/manage/privileges/{privilegeId}/edit")
	@ResponseBody
	public Privilege editPrivilege(@PathVariable(name = "privilegeId") long privilegeId) {
		Privilege privilege = null;
		
		try {
			privilege = privilegeService.getPrivilege(privilegeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return privilege;
	}
	
	/**
	 * Receives a GET request and deletes a privilege of the given id
	 * 
	 * @param privilegeId The ID of the privilege to delete
	 * @return
	 */
	@RequestMapping("/manage/privileges/{privilegeId}/delete")
	public String deletePrivilege(@PathVariable(name = "privilegeId") long privilegeId) {
		privilegeService.delete(privilegeId);
		
		return "redirect:/manage/privileges";
	}
	
	/**
	 * Receives a GET request and deletes privileges of the given IDs
	 * @param ids IDs of the privileges to delete
	 * @return
	 */
	@RequestMapping("/manage/privileges/{ids}/delete/selected")
	public String deleteSelectedPrivileges(@PathVariable(name = "ids") String ids) {
		
		String[] selectedIds = ids.split("_");
		privilegeService.deleteSelected(selectedIds);
		
		return "redirect:/manage/privileges";
	}	
	
	/**
	 * Receives a GET request and deletes all the privileges
	 * @return
	 */
	@RequestMapping("/manage/privileges/delete/all")
	public String deleteAllPrivileges() {
		
		privilegeService.deleteAll();
		
		return "redirect:/manage/privileges";
	}
}
