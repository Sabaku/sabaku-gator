package me.sabaku.api;

import java.util.Collection;

public interface Person {
	String getFirstName();
	String getLastName();
	Collection<Concept> getConceptsOfInterest();
}
