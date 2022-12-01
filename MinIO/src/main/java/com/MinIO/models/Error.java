package com.MinIO.models;

public class Error {
	private String code;
	private String message;
	private String[] fileExists;

	public Error(String code, String[] fileExists, String message) {
		super();
		this.code = code;
		this.message = message;
		this.fileExists = fileExists;
	}

	public Error() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String[] getFileExists() {
		return fileExists;
	}

	public void setFileExists(String[] fileExists) {
		this.fileExists = fileExists;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
