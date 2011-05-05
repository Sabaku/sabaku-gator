package me.sabaku.web.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.sabaku.api.Person;
import me.sabaku.web.domain.impl.PersonImpl;
import me.sabaku.web.service.PersonService;

import org.springframework.stereotype.Service;

@Service
public class MockPersonServiceImpl implements PersonService {
	
	@Override
	public Collection<Person> getPerson(String name) {
		List<Person> persons = new ArrayList<Person>();
		
		PersonImpl person = new PersonImpl();
		person.setFirstName("foo");
		person.setLastName("last");
		persons.add(person);
		
		return persons;
	}
}
