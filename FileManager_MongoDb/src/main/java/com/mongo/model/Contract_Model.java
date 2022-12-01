package com.mongo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Contract")
public class Contract_Model {
    private String type_of_contract;
    Date date = new Date();
//    SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
//    private String strDate = formatter.format(date);
    private int validity_in_months;
    private String size;
	private String url;

	
	
	
    public Contract_Model() {
		super();
	}

	public Contract_Model(String type_of_contract, Date date, int validity_in_months, String size, String url) {
		super();
		this.type_of_contract = type_of_contract;
		this.date = date;
		this.validity_in_months = validity_in_months;
		this.size = size;
		this.url = url;
	}

	public String getType_of_contract() {
        return type_of_contract;
    }

    public void setType_of_contract(String type_of_contract) {
        this.type_of_contract = type_of_contract;
    }

    public int getValidity_in_months() {
        return validity_in_months;
    }

    public void setValidity_in_months(int validity_in_months) {
        this.validity_in_months = validity_in_months;
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

//    public String getStrDate() {
//        return strDate;
//    }
//
//    public void setStrDate(String strDate) {
//        this.strDate = strDate;
//    }
}
