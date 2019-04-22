package com.ala2i.online.store.controller;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class CustomErrorController implements ErrorController {

	private static final String PATH = "/error";
	private ErrorAttributes errorAttributes;
	
	public CustomErrorController() {
	}
		
	@Autowired
	public CustomErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
	
	@RequestMapping("/error")
	public String error(Model model, HttpServletRequest request, WebRequest webRequest) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			String errorMessage = null;
			
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				errorMessage = "Sorry, we couldn't find the page you were looking for.";
			} else {				

				// A map of error attributes
				Map<String, Object> body = errorAttributes.getErrorAttributes(webRequest, true);
				
				// The exception message
				errorMessage = (String) body.get("message");
				
				// The error reason
				String error = (String) body.get("error");
				
				// Extract the exception stack trace
				String trace = (String) body.get("trace");
				
				// Extract timestamp string
				String timestamp = body.get("timestamp").toString();
				
				// Extract path - The URL path when the exception was raised
				String path = body.get("path").toString();
				
				// Extract the class name of the root exception (if configured)
				Exception exception = (Exception) body.get("exception");
				
				
				StringBuffer buffer = new StringBuffer();
				buffer.append("<pre>");
				
				if(statusCode != null) {
					buffer.append("Status Code : ");
					buffer.append(statusCode);
				}
				
				if(timestamp != null) {
					buffer.append("\n\rTimestamp : ");
					buffer.append(timestamp);
				}
				
				if(path != null) {
					buffer.append("\n\rPath : ");
					buffer.append(path);
				}
				
				if(errorMessage != null && errorMessage.trim().length() > 0) {
					buffer.append("\n\rError Message : ");
					buffer.append(errorMessage);
				}
				
				if(error != null) {
					buffer.append("\n\rError : ");
					buffer.append(error);
				}
				
				if(exception != null) {
					buffer.append("\n\rException : ");
					buffer.append(exception.getClass().getName());
				}
				
				if(trace != null) {
					buffer.append("\n\rStack Trace : ");
					buffer.append(trace);
				}
				
				buffer.append("</pre>");
				
				errorMessage = buffer.toString();
			}
			
			model.addAttribute("error", errorMessage);
		}
		return "pages/error";
	}
}
