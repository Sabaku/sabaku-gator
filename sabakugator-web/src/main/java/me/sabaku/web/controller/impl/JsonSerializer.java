package me.sabaku.web.controller.impl;

import me.sabaku.web.controller.Serializer;

import org.springframework.stereotype.Service;

@Service
public class JsonSerializer implements Serializer {
	//private Gson gson;
	private flexjson.JSONSerializer serializer;
	
	public void init() {
		//gson = new Gson();
		serializer = new flexjson.JSONSerializer().exclude("*.class");
	}
	
	@Override
	public String serialize(Object object) {
		//return gson.toJson(object);
		return serializer.deepSerialize(object);
	}
}
