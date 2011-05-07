package me.sabaku.web.controller.impl;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import me.sabaku.api.Person;
import me.sabaku.api.Publication;
import me.sabaku.web.service.PersonService;
import me.sabaku.web.service.PublicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicationController {
	@Autowired
	private JsonSerializer serializer;
	@Autowired
	@Qualifier("vivoPublicationServiceImpl")
	private PublicationService publicationService;
	@Autowired
	@Qualifier("vivoPersonServiceImpl")
	private PersonService personService;
	
	@RequestMapping("/getPublications")
	public void getPublications(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
		Person person = personService.getPerson(id);
		Collection<Publication> publications = publicationService.getPublications(person);
		
		response.setContentType("application/json");
		response.getWriter().print(serializer.serialize(publications));
	}
}
