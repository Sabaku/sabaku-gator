package me.sabaku.web.domain.impl;

import java.util.ArrayList;
import java.util.Collection;

import me.sabaku.api.domain.Concept;
import me.sabaku.api.domain.Person;

public class PersonImpl implements Person {
	private static final long serialVersionUID = 2694615840539141875L;
	private String id;
	private String firstName;
	private String lastName;
	private Collection<Concept> conceptsOfInterest;
	
	@Override
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public Collection<Concept> getConceptsOfInterest() {
		return conceptsOfInterest;
	}

	public void setConceptsOfInterest(Collection<Concept> conceptsOfInterest) {
		this.conceptsOfInterest = conceptsOfInterest;
	}
}
