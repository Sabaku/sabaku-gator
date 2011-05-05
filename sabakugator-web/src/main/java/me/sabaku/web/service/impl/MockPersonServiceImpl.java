package me.sabaku.web.service.impl;

import me.sabaku.api.Person;
import me.sabaku.web.domain.impl.PersonImpl;
import me.sabaku.web.service.PersonService;

import org.springframework.stereotype.Service;

@Service
public class MockPersonServiceImpl implements PersonService {
	
	@Override
	public Person getPerson(String name) {
		PersonImpl person = new PersonImpl();
		person.setFirstName("foo");
		person.setLastName("last");
		
		return person;
	}
}
