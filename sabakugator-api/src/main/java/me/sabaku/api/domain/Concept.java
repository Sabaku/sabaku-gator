package me.sabaku.api.domain;

import java.io.Serializable;

public interface Concept extends Serializable {
	String getId();
	String getLabel();
	String getUrl();
}
