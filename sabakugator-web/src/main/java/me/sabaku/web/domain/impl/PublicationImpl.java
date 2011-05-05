package me.sabaku.web.domain.impl;

import java.util.Collection;

import me.sabaku.api.Person;
import me.sabaku.api.Publication;

public class PublicationImpl implements Publication {
	private String id;
	private String title;
	private String abstractText;
	private Collection<Person> authors;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getAbstract() {
		return abstractText;
	}

	@Override
	public Collection<Person> getAuthors() {
		return authors;
	}
}
