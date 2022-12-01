package com.MinIO.models;

import java.util.List;

public class Preview {
	private Data_Model cwd;
	private List<Data_Model> files;
	private Details details;
	private Error error;
	public Preview(Data_Model cwd, List<Data_Model> files, Details details, Error error) {
		super();
		this.cwd = cwd;
		this.files = files;
		this.details = details;
		this.error = error;
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
	public Details getDetails() {
		return details;
	}
	public void setDetails(Details details) {
		this.details = details;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	
}

