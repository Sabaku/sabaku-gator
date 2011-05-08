package me.sabaku.api.service;

import java.util.Collection;

import me.sabaku.api.domain.Person;

public interface PersonService {
	Collection<Person> searchPerson(String firstName, String lastName);
	Person getPerson(String id);
}
