package com.codeone.project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class APIController {

	@GetMapping("test/{testString}")
	public String testAPI(@PathVariable("testString") String testString) {
		return testString;
	}
	
	@GetMapping("findById/{id}")
	public String findById(@PathVariable("id") String id) {
		return "data with "+id;
	}
	
}
