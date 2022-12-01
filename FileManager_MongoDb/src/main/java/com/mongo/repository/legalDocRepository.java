package com.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.model.Legaldoc_Model;

@Repository
public interface legalDocRepository extends MongoRepository<Legaldoc_Model, Integer> {

	void deleteByUrl(String url);

}