package me.sabaku.web.controller.impl;

import me.sabaku.web.controller.Serializer;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class JsonSerializer implements Serializer {
	private Gson gson;
	
	public void init() {
		gson = new Gson();
	}
	
	@Override
	public String serialize(Object object) {
		return gson.toJson(object);
	}
}
