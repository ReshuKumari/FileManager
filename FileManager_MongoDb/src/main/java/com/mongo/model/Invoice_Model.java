package com.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Invoice")
public class Invoice_Model {
    private String invoice_no;
    private String type_of_invoice;
    private String size;
	private String url;

    public Invoice_Model(String invoice_no, String type_of_invoice, String size, String url) {
		super();
		this.invoice_no = invoice_no;
		this.type_of_invoice = type_of_invoice;
		this.size = size;
		this.url = url;
	}

    
    public Invoice_Model() {
		super();
	}

	public String getType_of_invoice() {
        return type_of_invoice;
    }

    public void setType_of_invoice(String type_of_invoice) {
        this.type_of_invoice = type_of_invoice;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
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
}
