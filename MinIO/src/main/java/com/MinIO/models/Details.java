package com.MinIO.models;

public class Details {
	private String created;
	private boolean isFile;
	private String location;
	private String modified;
	private boolean multipleFiles;
	private String name;
	private String permission;
	private String size;

	public Details() {
		super();
	}

	public Details(String created, boolean isFile, String location, String modified, boolean multipleFiles, String name,
			String permission, String size) {
		super();
		this.created = created;
		this.isFile = isFile;
		this.location = location;
		this.modified = modified;
		this.multipleFiles = multipleFiles;
		this.name = name;
		this.permission = permission;
		this.size = size;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public boolean getIsFile() {
		return isFile;
	}

	public void setIsFile(boolean isFile) {
		this.isFile = isFile;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public boolean getMultipleFiles() {
		return multipleFiles;
	}

	public void setMultipleFiles(boolean multipleFiles) {
		this.multipleFiles = multipleFiles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
