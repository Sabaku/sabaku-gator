package me.sabaku.web.controller.impl;

import javax.servlet.http.HttpServletResponse;

import me.sabaku.api.Person;
import me.sabaku.web.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {
	@Autowired
	JsonSerializer serializer;
	@Autowired
	PersonService mockPersonService;
	
	@RequestMapping("/getPerson")
	public void getPerson(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
		Person person = mockPersonService.getPerson(name);
		response.getWriter().print(serializer.serialize(person));
	}
}
