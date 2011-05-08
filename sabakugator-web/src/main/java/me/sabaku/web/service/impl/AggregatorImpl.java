package me.sabaku.web.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Component;

import me.sabaku.api.domain.Concept;
import me.sabaku.api.domain.Person;
import me.sabaku.api.service.Aggregator;
import me.sabaku.web.domain.impl.PersonImpl;

@Component("aggregator")
public class AggregatorImpl implements Aggregator {

	@Override
	public Person aggregatePerson(Collection<Person> persons) {
		PersonImpl aggregatedPerson = new PersonImpl();
		
		for (Person p : persons) {
			for (Concept coi : p.getConceptsOfInterest()) {
				aggregatedPerson.getConceptsOfInterest().add(coi);
			}
		}
		
		return aggregatedPerson;
	}
}
