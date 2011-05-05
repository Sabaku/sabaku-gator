package me.sabaku.web.controller;

import javax.servlet.http.HttpServletResponse;

import me.sabaku.api.Person;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {
	
	@RequestMapping("/getPerson")
	public Person getPerson(@RequestParam("name") String name, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
