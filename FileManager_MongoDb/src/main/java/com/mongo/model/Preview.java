package com.mongo.model;

import java.util.List;

public class Preview {
	private Data_Model cwd;
	private List<Data_Model> files;
	private String details;
	private String errors;
	public Preview(Data_Model cwd, List<Data_Model> files, String details, String errors) {
		super();
		this.cwd = cwd;
		this.files = files;
		this.details = details;
		this.errors = errors;
	}
	public Preview() {
		super();
	}
	public Data_Model getCwd() {
		return cwd;
	}
	public void setCwd(Data_Model cwd) {
		this.cwd = cwd;
	}
	public List<Data_Model> getFiles() {
		return files;
	}
	public void setFiles(List<Data_Model> files) {
		this.files = files;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	
	
}
