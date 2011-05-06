package me.sabaku.api;

import java.io.Serializable;

public interface Concept extends Serializable {
	String getId();
	String getLabel();
	String getUrl();
}
