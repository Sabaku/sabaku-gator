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

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getUrl() {
		return url;
	}
}
