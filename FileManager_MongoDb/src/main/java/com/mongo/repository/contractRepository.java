package com.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.model.Contract_Model;

@Repository
public interface contractRepository extends MongoRepository<Contract_Model, Integer> {

	void deleteByUrl(String url);

}