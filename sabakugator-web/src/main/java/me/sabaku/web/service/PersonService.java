package me.sabaku.web.service;

import java.util.Collection;

import me.sabaku.api.Person;

public interface PersonService {
	Collection<Person> getPerson(String name);
}
