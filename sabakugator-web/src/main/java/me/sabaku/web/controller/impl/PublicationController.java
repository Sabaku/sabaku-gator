package me.sabaku.web.controller.impl;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import me.sabaku.api.domain.Person;
import me.sabaku.api.domain.Publication;
import me.sabaku.api.service.PublicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/publication")
public class PublicationController {
	@Autowired
	private JsonSerializer serializer;
	@Autowired
	@Qualifier("vivoPublicationServiceImpl")
	private PublicationService publicationService;
	
	@RequestMapping("/get")
	public void getPublications(@RequestParam("id") String id,
			@RequestParam(value="jsonp", required=false) String jsonpCallback,
			HttpServletResponse response) throws Exception {
		Collection<Publication> publications = publicationService.getPublications(id);

		String json = serializer.serialize(publications);
		response.setContentType("application/json");
		
		if (jsonpCallback != null) {
			response.getWriter().print(JsonpFormatter.format(json, jsonpCallback));
		} else {
			response.getWriter().print(json);
		}
	}
	
	@RequestMapping("/authors")
	public void getAuthors(@RequestParam("id") String id,
			@RequestParam(value="jsonp", required=false) String jsonpCallback,
			HttpServletResponse response) throws Exception {
		Collection<Person> authors = publicationService.getAuthors(id);
		
		String json = serializer.serialize(authors);
		response.setContentType("application/json");
		
		if (jsonpCallback != null) {
			response.getWriter().print(JsonpFormatter.format(json, jsonpCallback));
		} else {
			response.getWriter().print(json);
		}
	}
}
