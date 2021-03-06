package me.sabaku.web.domain.impl;

import java.util.Collection;

import me.sabaku.api.domain.Person;
import me.sabaku.api.domain.Publication;

public class PublicationImpl implements Publication {
	private static final long serialVersionUID = -4284204277340165126L;
	private String id;
	private String title;
	private String abstractText;
	private Collection<Person> authors;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String getAbstract() {
		return abstractText;
	}

	public void setAbstract(String abstractText) {
		this.abstractText = abstractText;
	}
	
	@Override
	public Collection<Person> getAuthors() {
		return authors;
	}
	
	public void setAuthors(Collection<Person> authors) {
		this.authors = authors;
	}
}
