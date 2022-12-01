package com.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.model.Data_Model;


public interface dataRepository extends MongoRepository<Data_Model, Integer>{
	
	Data_Model findByFilterId(String id);
	List<Data_Model> findAllByFilterId(String Id);
	List<Data_Model> findAllByParentId(String parentId);
	Data_Model deleteByNameAndParentId(String name,String ParentId);
	List<Data_Model> deleteByParentId(String ParentId);
	Data_Model findByNameAndParentId(String name,String ParentId);
	Data_Model findByName(String name);
	Data_Model findByNameAndUuid(String name, String uuid);
	
	List<Data_Model> findAllByFilterPath(String filterPath);
	Data_Model deleteByNameAndFilterPath(String name,String filterPath);
	List<Data_Model> deleteByFilterPath(String filterpath);
	Data_Model findByNameAndFilterPath(String name,String filterPath);
	
	
}
