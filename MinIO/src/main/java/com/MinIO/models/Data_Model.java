package com.MinIO.models;


import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.databind.JsonNode;

public class Data_Model {
	protected String path;
	protected String action;
	protected String newName;
	protected String names;
	protected String name;
	protected long size;
	protected String dateModified;
	protected String dateCreated;
	protected boolean hasChild;
	protected boolean isFile;
	protected String type;
//	@Id
	protected String id;
	protected String uuid;
	protected String filterPath;
	protected String filterId;
	protected String parentId;
	protected String targetPath;
	protected String renameFiles;
	protected String uploadFiles;
	protected boolean caseSensitive;
	protected String searchString;
	protected boolean showHiddenItems;
	protected String data;
	protected String targetData;
	protected String permission;
	protected String _fm_id;
	protected String previousName;
	protected String _fm_icon;
	protected JsonNode _fm_htmlAttr;
	
	protected String _fm_created;
	protected String _fm_modified;
	protected String _fm_iconClass;
	protected String _fm_imageUrl;
	protected JsonNode _fm_imageAttr;
	
	public Data_Model() {
		super();
	}
	
	public Data_Model(String path, String action, String newName, String names, String name, long size,
			String dateModified, String dateCreated, boolean hasChild, boolean isFile, String type, String id,String uuid,
			String filterPath, String filterId, String parentId, String targetPath, String renameFiles,
			String uploadFiles, boolean caseSensitive, String searchString, boolean showHiddenItems, String data,
			String targetData, String permission, String _fm_id, String previousName, String _fm_icon,
			JsonNode _fm_htmlAttr, String _fm_created, String _fm_modified, String _fm_iconClass, String _fm_imageUrl,
			JsonNode _fm_imageAttr) {
		super();
		this.path = path;
		this.action = action;
		this.newName = newName;
		this.names = names;
		this.name = name;
		this.size = size;
		this.dateModified = dateModified;
		this.dateCreated = dateCreated;
		this.hasChild = hasChild;
		this.isFile = isFile;
		this.type = type;
		this.id = id;
		this.uuid=uuid;
		this.filterPath = filterPath;
		this.filterId = filterId;
		this.parentId = parentId;
		this.targetPath = targetPath;
		this.renameFiles = renameFiles;
		this.uploadFiles = uploadFiles;
		this.caseSensitive = caseSensitive;
		this.searchString = searchString;
		this.showHiddenItems = showHiddenItems;
		this.data = data;
		this.targetData = targetData;
		this.permission = permission;
		this._fm_id = _fm_id;
		this.previousName = previousName;
		this._fm_icon = _fm_icon;
		this._fm_htmlAttr = _fm_htmlAttr;
		this._fm_created = _fm_created;
		this._fm_modified = _fm_modified;
		this._fm_iconClass = _fm_iconClass;
		this._fm_imageUrl = _fm_imageUrl;
		this._fm_imageAttr = _fm_imageAttr;
	}



	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public boolean isHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	public boolean getIsFile() {
		return isFile;
	}
	public void setIsFile(boolean isFile) {
		this.isFile = isFile;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFilterPath() {
		return filterPath;
	}
	public void setFilterPath(String filterPath) {
		this.filterPath = filterPath;
	}
	public String getFilterId() {
		return filterId;
	}
	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getTargetPath() {
		return targetPath;
	}
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	public String getRenameFiles() {
		return renameFiles;
	}
	public void setRenameFiles(String renameFiles) {
		this.renameFiles = renameFiles;
	}
	public String getUploadFiles() {
		return uploadFiles;
	}
	public void setUploadFiles(String uploadFiles) {
		this.uploadFiles = uploadFiles;
	}
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public boolean isShowHiddenItems() {
		return showHiddenItems;
	}
	public void setShowHiddenItems(boolean showHiddenItems) {
		this.showHiddenItems = showHiddenItems;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getTargetData() {
		return targetData;
	}
	public void setTargetData(String targetData) {
		this.targetData = targetData;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String get_fm_id() {
		return _fm_id;
	}
	public void set_fm_id(String _fm_id) {
		this._fm_id = _fm_id;
	}
	public String getPreviousName() {
		return previousName;
	}
	public void setPreviousName(String previousName) {
		this.previousName = previousName;
	}
	public String get_fm_icon() {
		return _fm_icon;
	}
	public void set_fm_icon(String _fm_icon) {
		this._fm_icon = _fm_icon;
	}
	public JsonNode get_fm_htmlAttr() {
		return _fm_htmlAttr;
	}
	public void set_fm_htmlAttr(JsonNode _fm_htmlAttr) {
		this._fm_htmlAttr = _fm_htmlAttr;
	}
	
	public String get_fm_created() {
		return _fm_created;
	}
	public void set_fm_created(String _fm_created) {
		this._fm_created = _fm_created;
	}
	public String get_fm_modified() {
		return _fm_modified;
	}
	public void set_fm_modified(String _fm_modified) {
		this._fm_modified = _fm_modified;
	}
	public String get_fm_iconClass() {
		return _fm_iconClass;
	}
	public void set_fm_iconClass(String _fm_iconClass) {
		this._fm_iconClass = _fm_iconClass;
	}
	public String get_fm_imageUrl() {
		return _fm_imageUrl;
	}
	public void set_fm_imageUrl(String _fm_imageUrl) {
		this._fm_imageUrl = _fm_imageUrl;
	}
	public JsonNode get_fm_imageAttr() {
		return _fm_imageAttr;
	}
	public void set_fm_imageAttr(JsonNode _fm_imageAttr) {
		this._fm_imageAttr = _fm_imageAttr;
	}
	@Override
	public String toString() {
		return "Data_Model [path=" + path + ", action=" + action + ", newName=" + newName + ", names=" + names
				+ ", name=" + name + ", size=" + size + ", dateModified=" + dateModified + ", dateCreated="
				+ dateCreated + ", hasChild=" + hasChild + ", isFile=" + isFile + ", type=" + type + ", id=" + id
				+ ", filterPath=" + filterPath + ", filterId=" + filterId + ", parentId=" + parentId + ", targetPath="
				+ targetPath + ", renameFiles=" + renameFiles + ", uploadFiles=" + uploadFiles + ", caseSensitive="
				+ caseSensitive + ", searchString=" + searchString + ", showHiddenItems=" + showHiddenItems + ", data="
				+ data + ", targetData=" + targetData + ", permission=" + permission + ", _fm_id=" + _fm_id
				+ ", previousName=" + previousName + ", _fm_icon=" + _fm_icon + ", _fm_htmlAttr=" + _fm_htmlAttr + "]";
	}

	
}
