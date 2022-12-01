package com.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Legal Document")
public class Legaldoc_Model {
    private String type_of_legal_doc;
	private String size;
	private String url;

    public Legaldoc_Model(String type_of_legal_doc, String size, String url) {
		super();
		this.type_of_legal_doc = type_of_legal_doc;
		this.size = size;
		this.url = url;
	}

	public Legaldoc_Model() {
		super();
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public String getType_of_legal_doc() {
        return type_of_legal_doc;
    }

    public void setType_of_legal_doc(String type_of_legal_doc) {
        this.type_of_legal_doc = type_of_legal_doc;
    }
}
