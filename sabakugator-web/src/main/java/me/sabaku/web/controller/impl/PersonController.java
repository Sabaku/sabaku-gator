package me.sabaku.web.controller.impl;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import me.sabaku.api.Person;
import me.sabaku.web.domain.impl.PersonImpl;
import me.sabaku.web.service.impl.VivoPersonServiceImpl;

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
	private JsonSerializer serializer;
	@Autowired
	@Qualifier("vivoPersonServiceImpl")
	private VivoPersonServiceImpl personService;
	
	@RequestMapping("/searchPerson")
	public void searchPerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, 
			HttpServletResponse response) throws Exception {
		Collection<Person> persons = personService.searchPerson(firstName, lastName);
		
		response.setContentType("application/json");
		response.getWriter().print(String.format("callback(%s)", serializer.serialize(persons)));
	}
	
	@RequestMapping("/getPerson")
	public void getPerson(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
		DateTime start = new DateTime();
		
		PersonImpl person = (PersonImpl)personService.getPerson(id);
		
		response.setContentType("application/json");
		response.getWriter().print(String.format("callback(%s)", serializer.serialize(person)));
		
		logger.info("Query for '{}' took {}ms", id, new Interval(start, new DateTime()).toDurationMillis());
	}
}
