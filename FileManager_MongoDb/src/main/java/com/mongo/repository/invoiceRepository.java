package com.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongo.model.Invoice_Model;

@Repository
public interface invoiceRepository extends MongoRepository<Invoice_Model, Integer> {

	void deleteByUrl(String url);

}
