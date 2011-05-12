package me.sabaku.web.service.impl.vivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.sabaku.api.domain.Person;
import me.sabaku.api.domain.Publication;
import me.sabaku.api.service.PersonService;
import me.sabaku.api.service.PublicationService;
import me.sabaku.web.domain.impl.PublicationImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

@Service("vivoPublicationServiceImpl")
public class VivoPublicationServiceImpl implements PublicationService {
	private static final Logger logger = LoggerFactory.getLogger(VivoPublicationServiceImpl.class);
	
	@Value("classpath:/me/sabaku/web/service/vivo/getPublications.sparql")
	private File publicationsQueryFile;
	private String publicationsQuery;
	
	@Value("classpath:/me/sabaku/web/service/vivo/getPublicationAuthors.sparql")
	private File authorsQueryFile;
	private String authorsQuery;
	
	@Value("${vivo.sparql.endpoint}")
	private String sparqlEndpoint;
	
	@Autowired
	@Qualifier("vivoPersonServiceImpl")
	private PersonService personService;
	
	public void init() throws Exception {
		{
			BufferedReader reader = new BufferedReader(new FileReader(publicationsQueryFile));
			StringBuffer sb = new StringBuffer();
			while (reader.ready()) {
				sb.append(reader.readLine() + "\n");
			}
			publicationsQuery = sb.toString();
		}
		{
			BufferedReader reader = new BufferedReader(new FileReader(authorsQueryFile));
			StringBuffer sb = new StringBuffer();
			while (reader.ready()) {
				sb.append(reader.readLine());
			}
			authorsQuery = sb.toString();
		}
		
	}
	
	@Override
	public Collection<Publication> getPublications(String id) {
		List<Publication> publications = new ArrayList<Publication>();
		
		Query query = QueryFactory.create(String.format(publicationsQuery, id));
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
		ResultSet resultSet = queryExecution.execSelect();
		
		while (resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.next();
			
			RDFNode url = querySolution.get("paper");
			RDFNode title = querySolution.get("title");
			
			PublicationImpl publication = new PublicationImpl();
			publication.setId(url.toString());
			publication.setTitle(title.toString());
			publications.add(publication);
		}
		
		return publications;
	}
	
	/**
	 * given the publication uri, get all authors
	 * @param id
	 * @return
	 */
	public Collection<Person> getAuthors(String id) {
		List<Person> authors = new ArrayList<Person>();
		
		Query query = QueryFactory.create(String.format(authorsQuery, id));
		QueryExecution queryExecution = QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
		ResultSet resultSet = queryExecution.execSelect();
		
		while (resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.next();
			RDFNode personUriNode = querySolution.get("person");
			
			Person person = personService.getPerson(personUriNode.toString());
			authors.add(person);
		}
		
		return authors;
	}
}
