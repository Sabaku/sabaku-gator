package me.sabaku.web.service;

import java.util.Collection;

import me.sabaku.api.Person;
import me.sabaku.api.Publication;

public interface PublicationService {
	Collection<Publication> getPublications(Person person);
}
