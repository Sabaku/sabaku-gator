package me.sabaku.web.service.impl;

import java.util.Collection;

import me.sabaku.api.domain.Concept;
import me.sabaku.api.domain.Person;
import me.sabaku.api.service.Aggregator;
import me.sabaku.api.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("aggregatePersonServiceImpl")
public class AggregatePersonServiceImpl implements PersonService {
	@Autowired
	@Qualifier("aggregator")
	private Aggregator aggregator;
	@Autowired
	@Qualifier("vivoPersonServiceImpl")
	private PersonService vivoPersonServiceImpl;
	@Autowired
	@Qualifier("wikiPeoplePersonServiceImpl")
	private PersonService wikiPeoplePersonServiceImpl;
	
	@Override
	public Collection<Person> searchPerson(String firstName, String lastName) {
		Collection<Person> wp = wikiPeoplePersonServiceImpl.searchPerson(firstName, lastName);
		Collection<Person> vp = vivoPersonServiceImpl.searchPerson(firstName, lastName);
		
		wp.addAll(vp);
		
		return wp;
	}

	@Override
	public Person getPerson(String id) {
		Person vp = wikiPeoplePersonServiceImpl.getPerson(id);
		return vp;
	}

	@Override
	public Collection<Concept> getConceptsOfInterest(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
