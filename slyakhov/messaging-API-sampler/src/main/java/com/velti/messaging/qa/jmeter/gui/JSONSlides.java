package com.velti.messaging.qa.jmeter.gui;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class JSONSlides {

	@JsonProperty
	private List<JSONSlide> SLIDES;

	public void setSLIDES(List<JSONSlide> sLIDES) {
		SLIDES = sLIDES;
	}

	@JsonIgnore
	public List<JSONSlide> getSLIDES() {
		return SLIDES;
	}

}
