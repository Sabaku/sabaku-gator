package me.sabaku.web.service;

import java.util.Collection;

import me.sabaku.api.Person;
import me.sabaku.web.domain.impl.PersonImpl;

public interface PersonService {
	Collection<Person> searchPerson(String name);
	Person getPerson(String id);
}
