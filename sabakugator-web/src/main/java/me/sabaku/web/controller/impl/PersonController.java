package me.sabaku.web.controller.impl;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import me.sabaku.api.domain.Concept;
import me.sabaku.api.domain.Person;
import me.sabaku.api.service.PersonService;
import me.sabaku.web.domain.impl.PersonImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/person")
public class PersonController {
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	@Autowired
	private JsonSerializer serializer;
	@Autowired
	@Qualifier("vivoPersonServiceImpl")
	private PersonService personService;

	@RequestMapping("/search")
	public void searchPerson(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam(value="jsonp", required=false) String jsonpCallback,
			HttpServletResponse response) throws Exception {
		Collection<Person> persons = personService.searchPerson(firstName, lastName);

		String json = serializer.serialize(persons);
		response.setContentType("application/json");
		
		if (jsonpCallback != null) {
			response.getWriter().print(JsonpFormatter.format(json, jsonpCallback));
		} else {
			response.getWriter().print(json);
		}
	}

	@RequestMapping("/get")
	public void getPerson(@RequestParam("id") String id,
			@RequestParam(value="jsonp", required=false) String jsonpCallback,
			HttpServletResponse response) throws Exception {
		PersonImpl person = (PersonImpl) personService.getPerson(id);
		
		String json = serializer.serialize(person);
		response.setContentType("application/json");
		
		if (jsonpCallback != null) {
			response.getWriter().print(JsonpFormatter.format(json, jsonpCallback));
		} else {
			response.getWriter().print(json);
		}
	}
	
	@RequestMapping("coi")
	public void getCOI(@RequestParam("id") String id,
			@RequestParam(value="jsonp", required=false) String jsonpCallback,
			HttpServletResponse response) throws Exception {
		
		Collection<Concept> cois = personService.getConceptsOfInterest(id);
		
		String json = serializer.serialize(cois);
		response.setContentType("application/json");
		
		if (jsonpCallback != null) {
			response.getWriter().print(JsonpFormatter.format(json, jsonpCallback));
		} else {
			response.getWriter().print(json);
		}
	}
}
