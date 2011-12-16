package com.velti.messaging.qa.jmeter.gui;

import java.net.URL;
import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class JSONSlide {

	@JsonProperty
	private Long DURATION;
	@JsonProperty
	private String TEXT;
	@JsonProperty
	private String IMAGE_LAYOUT;
	@JsonProperty
	private URL SOUND;
	@JsonProperty
	private URL IMAGE;
	@JsonProperty
	private URL VIDEO;
	@JsonProperty
	private HashMap<String, String> SLIDE_PROPERTIES;

	@JsonIgnore
	public Long getDURATION() {
		return DURATION;
	}

	public void setDURATION(Long dURATION) {
		DURATION = dURATION;
	}

	@JsonIgnore
	public String getTEXT() {
		return TEXT;
	}

	public void setTEXT(String tEXT) {
		TEXT = tEXT;
	}

	@JsonIgnore
	public String getIMAGE_LAYOUT() {
		return IMAGE_LAYOUT;
	}

	public void setIMAGE_LAYOUT(String iMAGELAYOUT) {
		IMAGE_LAYOUT = iMAGELAYOUT;
	}

	@JsonIgnore
	public URL getSOUND() {
		return SOUND;
	}

	public void setSOUND(URL sOUND) {
		SOUND = sOUND;
	}

	@JsonIgnore
	public URL getIMAGE() {
		return IMAGE;
	}

	public void setIMAGE(URL iMAGE) {
		IMAGE = iMAGE;
	}

	@JsonIgnore
	public URL getVIDEO() {
		return VIDEO;
	}

	public void setVIDEO(URL vIDEO) {
		VIDEO = vIDEO;
	}

	@JsonIgnore
	public HashMap<String, String> getSLIDE_PROPERTIES() {
		return SLIDE_PROPERTIES;
	}

	public void setSLIDE_PROPERTIES(HashMap<String, String> sLIDEPROPERTIES) {
		SLIDE_PROPERTIES = sLIDEPROPERTIES;
	}

}
