package com.zpmc.ztos.infra.business.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class AdminController {

	@RequestMapping({ "/admin/hello" })
	public String hello() {
		return "Hello World";
	}

}
