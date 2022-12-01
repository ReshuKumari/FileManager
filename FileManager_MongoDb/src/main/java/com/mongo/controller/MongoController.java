package com.mongo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongo.model.Contract_Model;
import com.mongo.model.Data_Model;
import com.mongo.model.Details;
import com.mongo.model.Invoice_Model;
import com.mongo.model.Legaldoc_Model;
import com.mongo.model.Music_Model;
import com.mongo.model.Video_Model;
import com.mongo.service.MongoService;

@RestController
@RequestMapping("/mongo")
public class MongoController {
	
	@Autowired
	private MongoService mongoService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@GetMapping("/userLogin")
	public String userValidation() {
		return "User, Successfully logged in!";
	}
	
	@PostMapping(path = "/create", consumes = "application/json")
	public Data_Model create(@RequestBody Data_Model data,@RequestHeader String path,@RequestHeader String name,@RequestHeader String type,@RequestHeader String size,@RequestHeader String action,@RequestHeader String uuid,@RequestHeader String targetid) throws JsonMappingException, JsonProcessingException
	{
		return mongoService.create(data, path, name, type,size,action,uuid,targetid);
	}
	
	@PostMapping(path = "/copy", consumes = "application/json")
	public Data_Model copy(@RequestBody Data_Model data,@RequestHeader String path,@RequestHeader String name,@RequestHeader String targetdata,@RequestHeader String size,@RequestHeader String action,@RequestHeader String uuid,@RequestHeader String names,@RequestHeader String type) throws JsonMappingException, JsonProcessingException
	{
		return mongoService.copy(data, path, name, targetdata,size,action,uuid,names,type);
	}
	
	@GetMapping("/findByFilterPath")
	public List<Data_Model> getDatabyFilterPath(@RequestHeader String parentId,@RequestHeader String path){
		List<Data_Model> listOfDatabyFilterPath = mongoService.getDatabyFilterPath(parentId,path);
		return listOfDatabyFilterPath;
	}
	
	@GetMapping("/findByName/{name}")
	public Data_Model getDatabyName(@PathVariable String name){
		return mongoService.getDatabyName(name);
	}
	
	@GetMapping("/getData")
	public Data_Model getData(@RequestHeader String name,@RequestHeader String filterPath,@RequestHeader String newName){
		Data_Model listOfDatabyFilterPath = mongoService.getData(name,filterPath,newName);
		return listOfDatabyFilterPath;
	}
	
	@GetMapping("/get")
	public Data_Model get(@RequestHeader String name,@RequestHeader String parentid){
		Data_Model listOfDatabyFilterPath = mongoService.findbynamenpath(parentid,name);
		return listOfDatabyFilterPath;
	}

	@GetMapping("/getDataByFilterPath")
	public Data_Model getDataByFilterPath(@RequestHeader String name,@RequestHeader String filterpath){
		Data_Model listOfDatabyFilterPath = mongoService.findbynamenfilterpath(filterpath,name);
		return listOfDatabyFilterPath;
	}
	
	@GetMapping("/getDetail")
	public Data_Model getDetails(@RequestHeader String uuid,@RequestHeader String name){
		Data_Model listOfDatabyFilterPath = mongoService.getDetails(name,uuid);
		return listOfDatabyFilterPath;
	}
	
	@DeleteMapping("/deleteData")
	public Data_Model delete(@RequestHeader String parentid,@RequestHeader String name)
	{
		return mongoService.delete(parentid,name);
	}
	
//	@GetMapping("/getData")
//	public List<?> getBydDoctype(@RequestHeader("docType") String docType){
//		if(docType.equals("Music"))
//		{
//			return mongoService.getAllMusic();
//		}
//		else if(docType.equals("Contract"))
//        {
//			return mongoService.getAllContract();
//        }
//		else if(docType.equals("Invoice"))
//        {
//			return mongoService.getAllInvoice();
//        }
//        else if(docType.equals("Legal_document"))
//        {
//        	return mongoService.getAllLegalDoc();
//        }
//        else if(docType.equals("Video"))
//        {
//        	return mongoService.getAllVideo();
//        }
//		return null;
//		
//	}

//	@PostMapping(path = "/create", consumes = "application/json")
//	public String createMusic(@RequestBody String jsondata,@RequestHeader("docType") String docType, @RequestHeader("url") String url,@RequestHeader("size") String size) throws JsonMappingException, JsonProcessingException
//	{
////		System.out.println("create");
//		
//		if(docType.equals("Music"))
//		{
//			Music_Model music = objectMapper.readValue(jsondata, Music_Model.class);
//			music.setSongTitle(music.getSongTitle());
//			music.setArtist(music.getArtist());
//			music.setGenre(music.getGenre());
//			music.setUrl(url);
//			music.setSize(size);
//			mongoService.createMusic(music);
//		}
//		else if(docType.equals("Contract"))
//        {
//            Contract_Model contract=objectMapper.readValue(jsondata, Contract_Model.class);
//            contract.setType_of_contract(contract.getType_of_contract());
////          contract.setStrDate(contract.getStrDate());
//            contract.setValidity_in_months(contract.getValidity_in_months());
//            contract.setUrl(url);
//            contract.setSize(size);
//            
//            mongoService.createContract(contract);
//        }
//		else if(docType.equals("Invoice"))
//        {
//            Invoice_Model invoice=objectMapper.readValue(jsondata, Invoice_Model.class);
//            invoice.setInvoice_no(invoice.getInvoice_no());
//            invoice.setType_of_invoice(invoice.getType_of_invoice());
//            invoice.setUrl(url);
//            invoice.setSize(size);
//            
//            mongoService.createInvoice(invoice);
//        }
//        else if(docType.equals("Legal Document"))
//        {
//            Legaldoc_Model legal_document=objectMapper.readValue(jsondata, Legaldoc_Model.class);
//            legal_document.setType_of_legal_doc(legal_document.getType_of_legal_doc());
//            legal_document.setUrl(url);
//            legal_document.setSize(size);
//            
//            mongoService.createLegal(legal_document);
//        }
//        else if(docType.equals("Video"))
//        {
//            Video_Model video=objectMapper.readValue(jsondata, Video_Model.class);
//            video.setVideo_title(video.getVideo_title());
//            video.setGenre(video.getGenre());
//            video.setUrl(url);
//            video.setSize(size);
//            
//            mongoService.createVideo(video);
//        }
//		
//		return "created " + docType + "File";
//	}
//	
	
//	@DeleteMapping("/deleteData")
//	public String deleteByUrl(@RequestHeader("docType") String docType, @RequestHeader("url") String url){
//		if(docType.equals("Music"))
//		{
//			return mongoService.deleteMusicbyUrl(url);
//		}
//		else if(docType.equals("Contract"))
//        {
//			return mongoService.deleteContractbyUrl(url);
//        }
//		else if(docType.equals("Invoice"))
//        {
//			return mongoService.deleteInvoicebyUrl(url);
//        }
//        else if(docType.equals("LegalDocument"))
//        {
//        	return mongoService.deleteLegalDocbyUrl(url);
//        }
//        else if(docType.equals("Video"))
//        {
//        	return mongoService.deleteVideobyUrl(url);
//        }
//		return null;
//		
//	}
	

}
