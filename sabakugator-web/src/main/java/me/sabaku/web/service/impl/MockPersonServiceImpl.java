package me.sabaku.web.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.sabaku.api.Concept;
import me.sabaku.api.Person;
import me.sabaku.web.domain.impl.ConceptImpl;
import me.sabaku.web.domain.impl.PersonImpl;
import me.sabaku.web.service.PersonService;

import org.springframework.stereotype.Service;

@Service("mockPersonServiceImpl")
public class MockPersonServiceImpl implements PersonService {
	
	@Override
	public Collection<Person> searchPerson(String name) {
		List<Person> persons = new ArrayList<Person>();
		
		PersonImpl p1 = new PersonImpl();
		p1.setId("123");
		p1.setFirstName("John");
		p1.setLastName("Doe");
		persons.add(p1);
		
		PersonImpl p2 = new PersonImpl();
		p2.setId("456");
		p2.setFirstName("Alice");
		p2.setLastName("Doe");
		persons.add(p2);
		
		return persons;
	}

	@Override
	public Person getPerson(String id) {
		PersonImpl person = new PersonImpl();
		
		person.setId(id);
		person.setFirstName("John");
		person.setLastName("Doe");
		
		List<Concept> cois = new ArrayList<Concept>();
		ConceptImpl c1 = new ConceptImpl();
		c1.setId("ABC1");
		c1.setLabel("Test1");
		c1.setUrl("example.org/ABC1");
		cois.add(c1);
		ConceptImpl c2 = new ConceptImpl();
		c2.setId("ABC2");
		c2.setLabel("Test2");
		c2.setUrl("example.org/ABC2");
		cois.add(c2);
		
		person.setConceptsOfInterest(cois);
		
		return person;
	}
}
