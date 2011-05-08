package me.sabaku.web.service.impl.mendeley;

import java.util.Collection;

import me.sabaku.api.domain.Person;
import me.sabaku.api.service.PersonService;

import org.springframework.stereotype.Service;

@Service("mendeleyPersonServiceImpl")
public class MendeleyPersonServiceImpl implements PersonService {
	private static final String MENDELEY__API_URL =
		"http://www.bigcat.unimaas.nl/~andra/sabaku/Mendeley.php?query=%s";
	
	@Override
	public Collection<Person> searchPerson(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person getPerson(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
