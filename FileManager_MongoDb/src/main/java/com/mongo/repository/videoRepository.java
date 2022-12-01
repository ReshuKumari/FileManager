package com.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.model.Video_Model;

@Repository
public interface videoRepository extends MongoRepository<Video_Model, Integer> {

	void deleteByUrl(String url);

}
