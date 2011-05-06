package me.sabaku.api;

import java.io.Serializable;
import java.util.Collection;

public interface Person extends Serializable {
	String getId();
	String getFirstName();
	String getLastName();
	Collection<Concept> getConceptsOfInterest();
}
