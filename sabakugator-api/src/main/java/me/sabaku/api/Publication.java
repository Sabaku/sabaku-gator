package me.sabaku.api;

import java.io.Serializable;
import java.util.Collection;

public interface Publication extends Serializable {
	String getId();
	String getTitle();
	String getAbstract();
	Collection<Person> getAuthors();
}
