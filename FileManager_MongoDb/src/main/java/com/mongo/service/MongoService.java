package com.mongo.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongo.model.Contract_Model;
import com.mongo.model.Data_Model;
import com.mongo.model.Details;
import com.mongo.model.Invoice_Model;
import com.mongo.model.Legaldoc_Model;
import com.mongo.model.Music_Model;
import com.mongo.model.Video_Model;

public interface MongoService {

	
	public Data_Model create(Data_Model data,String path,String name,String type, String size, String action, String uuid,String targetid)throws JsonMappingException, JsonProcessingException;
	
	public Data_Model getDatabyName(String name) throws java.util.NoSuchElementException;
	
	public List<Data_Model> getDatabyFilterPath(String filterPath, String path);

	public Data_Model delete(String filterPath, String name);

	Data_Model getData(String name, String filterpath,String newName) throws NoSuchElementException;

	public Data_Model getDetails(String name, String filterPath);

	Data_Model findbynamenpath(String filterpath, String name);
	Data_Model findbynamenfilterpath(String filterpath,String name);

	public Data_Model copy(Data_Model data, String path, String name, String targetdata, String size, String action,
			String uuid, String names, String type);
}
