package me.sabaku.api;

import java.util.Collection;

public interface Publication {
	String getId();
	String getTitle();
	String getAbstract();
	Collection<Person> getAuthors();
}
