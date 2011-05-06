package me.sabaku.web.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.sabaku.api.Person;
import me.sabaku.web.domain.impl.PersonImpl;
import me.sabaku.web.service.PersonService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

@Service("vivoPersonServiceImpl")
public class VivoPersonServiceImpl implements PersonService {
	private static final Logger logger = LoggerFactory.getLogger(VivoPersonServiceImpl.class);
	
	@Value("classpath:/me/sabaku/web/service/vivo/searchPersons.sparql")
	private File searchPersonQueryFile;
	private String searchPersonQuery;
	
	@Value("classpath:/me/sabaku/web/service/vivo/getPersonDetails.sparql")
	private File personDetailsQueryFile;
	private String personDetailsQuery;
	
	@Value("${vivo.sparql.endpoint}")
	private String sparqlEndpoint;
	
	public void init() throws Exception {
		{
			BufferedReader reader = new BufferedReader(new FileReader(searchPersonQueryFile));
			StringBuffer sb = new StringBuffer();
			while (reader.ready()) {
				sb.append(reader.readLine() + "\n");
			}
			searchPersonQuery = sb.toString();
		}
		{
			BufferedReader reader = new BufferedReader(new FileReader(personDetailsQueryFile));
			StringBuffer sb = new StringBuffer();
			while (reader.ready()) {
				sb.append(reader.readLine());
			}
			personDetailsQuery = sb.toString();
		}
	}
	
	@Override
	public Collection<Person> searchPerson(String firstName, String lastName) {
		List<Person> persons = new ArrayList<Person>();
		
		Query query = QueryFactory.create(String.format(searchPersonQuery, firstName, lastName));
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
		ResultSet resultSet = queryExecution.execSelect();
		
		while (resultSet.hasNext()) {
			QuerySolution solution = resultSet.next();
			RDFNode uriNode = solution.get("uri");
			RDFNode firstNameNode = solution.get("firstName");
			RDFNode lastNameNode = solution.get("lastName");
			
			PersonImpl person = new PersonImpl();
			person.setId(uriNode.toString());
			person.setFirstName(firstName);
			person.setLastName(lastName);
			persons.add(person);
		}
		
		return persons;
	}

	@Override
	public Person getPerson(String id) {
		PersonImpl person = new PersonImpl();
		person.setId(id);
		
		Query query = QueryFactory.create(String.format(personDetailsQuery, id, id));
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
		ResultSet resultSet = queryExecution.execSelect();
		
		while (resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.next();
			RDFNode firstNameNode = querySolution.get("firstName");
			RDFNode lastNameNode = querySolution.get("lastName");
			
			person.setFirstName(firstNameNode.toString());
			person.setLastName(lastNameNode.toString());
			
			// break the loop after the first result
			// TODO: sanity checks
			break;
		}
		
		return person;
	}
}
