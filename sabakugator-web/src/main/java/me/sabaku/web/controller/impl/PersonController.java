package me.sabaku.web.controller.impl;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import me.sabaku.api.Person;
import me.sabaku.web.domain.impl.PersonImpl;
import me.sabaku.web.service.PersonService;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	@Autowired
	JsonSerializer serializer;
	@Autowired
	@Qualifier("mockPersonServiceImpl")
	PersonService personService;
	
	@RequestMapping("/searchPerson")
	public void searchPerson(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
		DateTime start = new DateTime();
		
		Collection<Person> persons = personService.searchPerson(name);
		
		response.setContentType("application/json");
		response.getWriter().print(serializer.serialize(persons));
		
		logger.info("Query for '{}' took {}ms", name, new Interval(start, new DateTime()).toDurationMillis());
	}
	
	@RequestMapping("/getPerson")
	public void getPerson(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
		DateTime start = new DateTime();
		
		PersonImpl person = (PersonImpl)personService.getPerson(id);
		
		response.setContentType("application/json");
		response.getWriter().print(serializer.serialize(person));
		
		logger.info("Query for '{}' took {}ms", id, new Interval(start, new DateTime()).toDurationMillis());
	}
}
