package me.sabaku.web.domain.impl;

import me.sabaku.api.Concept;

public class ConceptImpl implements Concept {
	private String id;
	private String label;
	private String url;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
