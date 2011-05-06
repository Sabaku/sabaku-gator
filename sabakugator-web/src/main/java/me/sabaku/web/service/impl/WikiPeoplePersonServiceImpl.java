package me.sabaku.web.service.impl;

import static java.net.URLEncoder.encode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import me.sabaku.api.Concept;
import me.sabaku.api.Person;
import me.sabaku.web.controller.impl.JsonSerializer;
import me.sabaku.web.domain.impl.ConceptImpl;
import me.sabaku.web.domain.impl.PersonImpl;
import me.sabaku.web.service.PersonService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service("wikiPeoplePersonServiceImpl")
public class WikiPeoplePersonServiceImpl implements PersonService {
	private static final Logger logger = LoggerFactory.getLogger(WikiPeoplePersonServiceImpl.class);
	private static final String WIKIPEOPLE_SEARCH_URL =
		"http://conceptwiki.org/index.php/Special:DataSearch?search-text=%s&format=xml";
	private static final String WIKIPEOPLE_DETAIL_URL = 
		"http://conceptwiki.org/index.php/Special:ConceptAsXML?uuid=%s";
	private static final String URLENCODE_ENCODING = "UTF-8";
	private static final String XPATH_SEARCH_RESULT_QUERY = "/results//result/id/text()";
	private static final String XPATH_FIRST_NAME = "//infoItem[@name='definedMeaningAttributes']/info[@name='has first name']/text()";
	private static final String XPATH_LAST_NAME = "//infoItem[@name='definedMeaningAttributes']/info[@name='has last name']/text()";
	private static final String XPATH_COI = "//infoItem[@name='conceptsOfInterest']/info/text()";
	
	private DocumentBuilder documentBuilder;
	private XPathExpression xpathSearchResultQuery;
	private XPathExpression xpathFirstNameQuery;
	private XPathExpression xpathLastNameQuery;
	private XPathExpression xpathCoiQuery;
	
	// testing purposes
	@Autowired
	JsonSerializer serializer;
	
	public void init() throws Exception {
		documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		XPath xpath = XPathFactory.newInstance().newXPath();
		xpathSearchResultQuery = xpath.compile(XPATH_SEARCH_RESULT_QUERY);
		xpathFirstNameQuery = xpath.compile(XPATH_FIRST_NAME);
		xpathLastNameQuery = xpath.compile(XPATH_LAST_NAME);
		xpathCoiQuery = xpath.compile(XPATH_COI);
	}
	
	@Override
	public Collection<Person> searchPerson(String name) {
		List<Person> persons = new ArrayList<Person>();
		try {
			Document dom = documentBuilder.parse(String.format(WIKIPEOPLE_SEARCH_URL,
					encode(name, URLENCODE_ENCODING)));
			
			NodeList results = (NodeList)xpathSearchResultQuery.evaluate(dom, XPathConstants.NODESET);
			for (int i = 0; i < results.getLength(); i++) {
				Node node = results.item(i);
				String id = node.getNodeValue();

				Person person = getPersonDetails(id);
				persons.add(person);
			}
		} catch(Exception e) {
			logger.warn("Danger, danger!", e);
		}
		
		return persons;
	}
	
	@Override
	public Person getPerson(String id) {
		PersonImpl person = new PersonImpl();
		person.setId(id);
		
		try {
			Document dom = documentBuilder.parse(String.format(WIKIPEOPLE_DETAIL_URL, id));
			NodeList results = (NodeList)xpathCoiQuery.evaluate(dom, XPathConstants.NODESET);
			List<Concept> conceptsOfInterest = new ArrayList<Concept>();
			
			for (int i = 0; i < results.getLength(); i++) {
				String label = results.item(i).getNodeValue();
				ConceptImpl concept = new ConceptImpl();
				concept.setId("empty");
				concept.setLabel(label);
				concept.setUrl("cw.org/bla");
				conceptsOfInterest.add(concept);
			}
			
			person.setConceptsOfInterest(conceptsOfInterest);
		} catch(Exception e) {
			logger.warn("Danger, danger!", e);
		}
		
		return person;
	}
	
	private Person getPersonDetails(String id) {
		PersonImpl person = new PersonImpl();
		
		person.setId(id);
		
		try {
			Document dom = documentBuilder.parse(String.format(WIKIPEOPLE_DETAIL_URL, id));
			String firstName = xpathFirstNameQuery.evaluate(dom);
			String lastName = xpathLastNameQuery.evaluate(dom);
			
			person.setFirstName(firstName);
			person.setLastName(lastName);
		} catch(Exception e) {
			logger.warn("Danger, dagner!", e);
		}
		
		return person;
	}
}
