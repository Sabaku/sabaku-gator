package me.sabaku.web.controller.impl;

import java.io.StringWriter;

import me.sabaku.web.controller.Serializer;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JsonSerializer implements Serializer {
	private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);
	private ObjectMapper mapper;
	
	public void init() {
		mapper = new ObjectMapper();
	}
	
	@Override
	public String serialize(Object object) {
		StringWriter w = new StringWriter();
		try {
			mapper.writeValue(w, object);
		} catch(Exception e) {
			logger.warn("kaboom", e);
		}
		return w.toString();
	}
}
