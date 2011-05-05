package me.sabaku.web.controller.impl;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import me.sabaku.api.Person;
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
	@Qualifier("wikiPeoplePersonServiceImpl")
	PersonService personService;
	
	@RequestMapping("/getPerson")
	public void getPerson(@RequestParam("name") String name, HttpServletResponse response) throws Exception {
		DateTime start = new DateTime();
		
		Collection<Person> persons = personService.getPerson(name);
		response.getWriter().print(serializer.serialize(persons));
		
		logger.info("Query for '{}' took {}ms", name, new Interval(start, new DateTime()).toDurationMillis());
	}
}
