package me.sabaku.web.controller.impl;

import me.sabaku.api.Person;
import me.sabaku.web.controller.PersonService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonServiceImpl implements PersonService {
	
	@Override
	@RequestMapping("/getPerson")
	public Person getPerson(@RequestParam("name") String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
