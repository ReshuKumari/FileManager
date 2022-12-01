package com.MinIO.models;

import org.springframework.web.multipart.MultipartFile;

public class Upload_Model {

		private String name;
		private String path;
		private String action;
		private String data;
		private boolean showHiddenItems;
		private MultipartFile uploadFile;
		
		public Upload_Model() {
			super();
		}
		
		
		public Upload_Model(String name, String path, String action, String data, boolean showHiddenItems,
				MultipartFile uploadFile) {
			super();
			this.name = name;
			this.path = path;
			this.action = action;
			this.data = data;
			this.showHiddenItems = showHiddenItems;
			this.uploadFile = uploadFile;
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
		
		public MultipartFile getUploadFile() {
			return uploadFile;
		}
		public void setUploadFile(MultipartFile uploadFile) {
			this.uploadFile = uploadFile;
		}


		public String getData() {
			return data;
		}


		public void setData(String data) {
			this.data = data;
		}
		
	}


