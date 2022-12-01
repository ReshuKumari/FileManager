package com.MinIO.models;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Payload {

	private String name;
	private String[] names;
	private String newName;
	private String[] renameFiles;
	private String path;
	private String targetPath;
	private String action;
	private List<Data_Model> data;
	private Data_Model data1;
	private Data_Model targetData;
	private boolean showHiddenItems;
	private MultipartFile uploadFiles;
	private String searchString;
	private String filterId;
	
	public Payload() {
		super();
	}
	
	
	public Payload(String name, String[] names, String newName, String[] renameFiles, String path, String targetPath,
			String action, List<Data_Model> data, Data_Model data1, Data_Model targetData, boolean showHiddenItems,
			MultipartFile uploadFiles, String searchString,String filterId) {
		super();
		this.name = name;
		this.names = names;
		this.newName = newName;
		this.renameFiles = renameFiles;
		this.path = path;
		this.targetPath = targetPath;
		this.action = action;
		this.data = data;
		this.data1 = data1;
		this.targetData = targetData;
		this.showHiddenItems = showHiddenItems;
		this.uploadFiles = uploadFiles;
		this.searchString = searchString;
		this.filterId = filterId;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTargetPath() {
		return targetPath;
	}
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean isShowHiddenItems() {
		return showHiddenItems;
	}
	public void setShowHiddenItems(boolean showHiddenItems) {
		this.showHiddenItems = showHiddenItems;
	}
	public List<Data_Model> getData() {
		return data;
	}
	public void setData(List<Data_Model> data) {
		this.data = data;
	}
	public Data_Model getTargetData() {
		return targetData;
	}
	public void setTargetData(Data_Model targetData) {
		this.targetData = targetData;
	}
	public MultipartFile getUploadFile() {
		return uploadFiles;
	}
	public void setUploadFile(MultipartFile uploadFiles) {
		this.uploadFiles = uploadFiles;
	}


	public Data_Model getData1() {
		return data1;
	}

	public void setData1(Data_Model data1) {
		this.data1 = data1;
	}
	
	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String[] getRenameFiles() {
		return renameFiles;
	}


	public void setRenameFiles(String[] renameFiles) {
		this.renameFiles = renameFiles;
	}

	public String getSearchString() {
		return searchString;
	}


	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}


	public String getNewName() {
		return newName;
	}


	public void setNewName(String newName) {
		this.newName = newName;
	}


	public String getFilterId() {
		return filterId;
	}


	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}


	
	
	
	
}