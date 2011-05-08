package me.sabaku.web.service.impl.vivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import me.sabaku.api.domain.Concept;
import me.sabaku.api.domain.Person;
import me.sabaku.api.service.PersonService;
import me.sabaku.web.domain.impl.ConceptImpl;
import me.sabaku.web.domain.impl.PersonImpl;

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
	
	@Value("classpath:/me/sabaku/web/service/vivo/getCOI.sparql")
	private File conceptsOfInterestQueryFile;
	private String conceptsOfInterestQuery;
	
	@Value("${vivo.sparql.endpoint}")
	private String sparqlEndpoint;
	
	public void init() throws Exception {
		{
			BufferedReader reader = new BufferedReader(new FileReader(searchPersonQueryFile));
			StringBuffer sb = new StringBuffer();
			while (reader.ready()) {
				sb.append(reader.readLine());
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
		{
			BufferedReader reader = new BufferedReader(new FileReader(conceptsOfInterestQueryFile));
			StringBuffer sb = new StringBuffer();
			while (reader.ready()) {
				sb.append(reader.readLine());
			}
			conceptsOfInterestQuery = sb.toString();
		}
	}
	
	@Override
	public Collection<Person> searchPerson(String firstName, String lastName) {
		List<Person> persons = new ArrayList<Person>();
		
		Query query = QueryFactory.create(String.format(searchPersonQuery, firstName, lastName,
				String.format("%s, %s", lastName, firstName)));
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
		ResultSet resultSet = queryExecution.execSelect();
		
		while (resultSet.hasNext()) {
			QuerySolution solution = resultSet.next();
			RDFNode uriNode = solution.get("uri");
			RDFNode firstNameNode = solution.get("firstName");
			RDFNode lastNameNode = solution.get("lastName");
			
			boolean isDuplicate = false;
			for (Person p : persons) {
				if (p.getId() == uriNode.toString()) {
					isDuplicate = true;
					break;
				}
			}
			
			if (!isDuplicate) {
				PersonImpl person = new PersonImpl();
				person.setId(uriNode.toString());
				person.setFirstName(firstNameNode.toString());
				person.setLastName(lastNameNode.toString());
				persons.add(person);
			}
		}
		
		return persons;
	}

	@Override
	public Person getPerson(String id) {
		PersonImpl person = new PersonImpl();
		person.setId(id);
		
		Query query = QueryFactory.create(String.format(personDetailsQuery, id, id, id));
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
		ResultSet resultSet = queryExecution.execSelect();
		
		while (resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.next();
			RDFNode firstNameNode = querySolution.get("firstName");
			RDFNode lastNameNode = querySolution.get("lastName");
			RDFNode concatNameNode = querySolution.get("concatName");
			
			if (firstNameNode == null || lastNameNode == null) {
				String firstName = concatNameNode.toString().split(", ")[1];
				String lastName = concatNameNode.toString().split(", ")[0];
				
				person.setFirstName(firstName);
				person.setLastName(lastName);
			} else {
				person.setFirstName(firstNameNode.toString());
				person.setLastName(lastNameNode.toString());
			}
			person.setConceptsOfInterest(getConceptsOfInterest(id));
			
			// break the loop after the first result
			// TODO: sanity checks
			break;
		}
		
		return person;
	}
	
	/**
	 * given the uri of a person, return all their concepts of interest
	 * @param uri
	 * @return
	 */
	private Collection<Concept> getConceptsOfInterest(String uri) {
		List<Concept> cois = new ArrayList<Concept>();
		
		Query query = QueryFactory.create(String.format(conceptsOfInterestQuery, uri));
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
		ResultSet resultSet = queryExecution.execSelect();
		
		Map<String,Integer> histogram = new HashMap<String,Integer>();
		
		while (resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.next();
			RDFNode labelNode = querySolution.get("coi");
			String label = labelNode.toString();
			
			if (!histogram.containsKey(label)) {
				histogram.put(label, new Integer(0));
				
				// TODO: only return the top X COIs
				ConceptImpl concept = new ConceptImpl();
				concept.setLabel(label);
				cois.add(concept);
			}
			histogram.put(label, histogram.get(label) + 1);
		}
		
		return cois;
	}
}
