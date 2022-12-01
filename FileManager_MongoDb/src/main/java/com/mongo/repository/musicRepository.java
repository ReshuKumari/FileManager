package com.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.model.Music_Model;

@Repository
public interface musicRepository extends MongoRepository<Music_Model, Integer> {

	void deleteByUrl(String url);

}
