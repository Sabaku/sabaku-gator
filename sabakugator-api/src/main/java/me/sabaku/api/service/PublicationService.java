package me.sabaku.api.service;

import java.util.Collection;

import me.sabaku.api.domain.Person;
import me.sabaku.api.domain.Publication;

public interface PublicationService {
	Collection<Publication> getPublications(String id);
	Collection<Person> getAuthors(String id);
}
