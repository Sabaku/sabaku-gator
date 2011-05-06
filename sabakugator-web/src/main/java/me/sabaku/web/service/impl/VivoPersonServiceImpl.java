package me.sabaku.web.service.impl;

import java.util.Collection;

import me.sabaku.api.Person;
import me.sabaku.web.service.PersonService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

@Service("vivoPersonServiceImpl")
public class VivoPersonServiceImpl implements PersonService {
	private static final Logger logger = LoggerFactory.getLogger(VivoPersonServiceImpl.class);
	private static final String VIVO_SPARQL_ENDPOINT = "http://vivodevweb.vivo.ufl.edu/sparqlendpoint";
	private static final String VIVO_SPARQL_QUERY = "prefix foaf: <http://xmlns.com/foaf/0.1/>\n" +
		"select *\n" +
		"where {\n" +
		"  ?s foaf:firstName \"%s\" .\n" +
		"  ?s foaf:lastName \"%s\" .\n" +
		"}\n";
	
	@Override
	public Collection<Person> searchPerson(String name) {
		QueryExecution qe = QueryExecutionFactory.sparqlService(
				VIVO_SPARQL_ENDPOINT, String.format(VIVO_SPARQL_QUERY, "John", "Doe"));
		ResultSet resultSet = qe.execSelect();
		
		while (resultSet.hasNext()) {
			QuerySolution solution = resultSet.next();
			RDFNode node = solution.get("s");
			logger.info("got result: {}", node.toString());
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person getPerson(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
