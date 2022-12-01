package com.mongo.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

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
import com.mongo.repository.contractRepository;
import com.mongo.repository.dataRepository;
import com.mongo.repository.invoiceRepository;
import com.mongo.repository.legalDocRepository;
import com.mongo.repository.musicRepository;
import com.mongo.repository.videoRepository;

@Service
public class MongoServiceImpl implements MongoService {

	@Autowired
	private dataRepository DataRepo;

	ObjectMapper objectMapper = new ObjectMapper();

	public Data_Model create(Data_Model data, String path, String name,String type,String size,String action,String uuid,String targetid)
			throws JsonMappingException, JsonProcessingException {
		
//		System.out.println(data.getParentId());
//		System.out.println(name);
//		System.out.println(data.getName());
		Data_Model detail = DataRepo.findByNameAndParentId(name,data.getFilterId());
		
		System.out.println("details......"+detail);
		
		OffsetDateTime now = OffsetDateTime.now( ZoneOffset.UTC );
		if(detail==null)
		{
			System.out.println(action);
			Data_Model data1= DataRepo.findByNameAndParentId(data.getName(),data.getParentId());
			System.out.println("action......"+action);
			System.out.println("Target id......"+targetid);
			if(action.equals("copy")) {
				data.setParentId(targetid);
			}
			else
			data.setParentId(data1.getFilterId());
			data.setDateCreated(now.toString());
			data.setDateModified(now.toString());
			data.setSize(Long.parseLong(size));
			data.setFilterId(uuid);
			if(!type.equals("Folder") && !data.equals(null))
			{
				data.setType(type);
				data.setIsFile(true);
			}
			else
				{
				String uid = (UUID.randomUUID()).toString();
				data.setUuid(uid);  
				data.setIsFile(false);
				data.setSize(0);
				}
			
			data.setName(name);			
			
			DataRepo.save(data);
			Data_Model datasize;
			String check = data.getParentId();
			do {
				datasize = DataRepo.findByFilterId(check);
				datasize.setSize(datasize.getSize()+data.getSize());
				DataRepo.save(datasize);
				check = datasize.getParentId();
			}while(datasize.getParentId()!=null);
			
			return DataRepo.save(data);

		}
		else if(action.equals("replace"))
		{
			Data_Model newB = new Data_Model();
			newB.setUuid(detail.getUuid());
			detail.setUuid(data.getUuid());
			detail.setDateCreated(now.toString());
			detail.setDateModified(now.toString());
			DataRepo.save(detail);
			return newB;
		}
		else if(action.equals("keepboth"))
		{
			data.setFilterId(detail.getFilterId());
			data.setParentId(detail.getParentId());
			List<Data_Model> data2 = DataRepo.findAllByFilterId(detail.getFilterId());
			int sizee = data2.size();
			String newname = name;
			int n=0;
			for(int j=0;j<sizee;j++)
			{
				Data_Model newB2 = DataRepo.findByNameAndParentId(newname,data.getParentId());
				System.out.println("newB22222 " + newB2);
				if(newB2==null)
				{
					break;
				}
				else
				{
					n++;
					newname = name.split("[.]")[0] + "(" + n + ")." + name.split("[.]")[1]; 
				}
				
			}
			data.setName(newname);
			data.setDateCreated(now.toString());
			data.setDateModified(now.toString());
			data.setSize(Long.parseLong(size));
			if(!type.equals("Folder") && !data.equals(null))
			{
				data.setType(type);
				data.setIsFile(true);
			}
			else
				data.setIsFile(false);
		
			Data_Model datasize;
			String check = data.getParentId();
			do {
				datasize = DataRepo.findByFilterId(check);
				datasize.setSize(datasize.getSize()+data.getSize());
				DataRepo.save(datasize);
				check = datasize.getParentId();
			}while(datasize.getParentId()!=null);
			
			return DataRepo.save(data);
		}	
		return null;
		
	}
	
	public Data_Model copy(Data_Model data, String path, String name,String targetdata,String size,String action,String uuid,String names,String type)
	{
		Data_Model detail = DataRepo.findByNameAndParentId(name,targetdata);
		OffsetDateTime now = OffsetDateTime.now( ZoneOffset.UTC );
		System.out.println("dataaaa..." +data);
		
		if(type.equals("Folder"))
		{
			Data_Model newB = DataRepo.findByNameAndParentId(names,targetdata);
			targetdata = newB.getFilterId();
			System.out.println("targetid??..." +targetdata);
		}
		System.out.println("targetid..." +targetdata);
		data.setParentId(targetdata);
		if(detail==null)
		{
			data.setFilterId(uuid);
		}
		else
		{
			List<Data_Model> data2 = DataRepo.findAllByFilterId(detail.getFilterId());
			int sizee = data2.size();
			String newname = name;
			int n=0;
			for(int j=0;j<sizee;j++)
			{
				Data_Model newB2 = DataRepo.findByNameAndParentId(newname,targetdata);
				System.out.println("newB22222 " + newB2);
				if(newB2==null)
				{
					break;
				}
				else
				{
					n++;
					newname = name.split("[.]")[0] + "(" + n + ")." + name.split("[.]")[1]; 
				}
			}
			data.setName(newname);
		}

		data.setUuid((UUID.randomUUID()).toString());
		data.setDateModified(now.toString());
		
		return DataRepo.save(data);
	}
	
	 @Override
		public List<Data_Model> getDatabyFilterPath(String filterId,String path) throws java.util.NoSuchElementException {
			List<Data_Model> data = DataRepo.findAllByParentId(filterId);
			System.out.println(data);
			if(!data.isEmpty())
			{
			    for(int i=0;i<data.size();i++)
			    {
			    	data.get(i).setId(null);
			    	data.get(i).setFilterPath(path);
			    }
				return data;
			}
			else 
			 return data;
	}

//		 @Override
//			public List<Data_Model> getDatabyFilterId(String filterId,String path) throws java.util.NoSuchElementException {
//				
//				List<Data_Model> data;
//				do{
//					data = DataRepo.findAllByParentId(filterId);
//					
//				}while());
//				System.out.println(data);
//				if(!data.isEmpty())
//				{
//				    for(int i=0;i<data.size();i++)
//				    {
//				    	data.get(i).setId(null);
//				    	data.get(i).setFilterPath(path);
//				    }
//					return data;
//				}
//				else 
//				 return data;
//		}
	@Override
	public Data_Model getDatabyName(String name) throws java.util.NoSuchElementException {
		Data_Model data = DataRepo.findByName(name);
		if (data!=null)
			return data;
		else
			return null;
	}
	
	@Override
	public Data_Model getData(String name,String filterpath,String newName) throws java.util.NoSuchElementException {
		Data_Model data = DataRepo.findByNameAndFilterPath(name,filterpath);
		if (!data.equals(null) && !newName.equals(null)) {
			data.setName(newName);
		}
		
		DataRepo.save(data);
		return DataRepo.save(data);
	}
	
	@Override
	public Data_Model delete(String parentid,String name) {
		Data_Model data = DataRepo.findByNameAndParentId(name,parentid);
		System.out.println(name);
//		System.out.println(filterpath);
		
		if(data!=null)
		{
			Data_Model datasize;
			String check = parentid;
			do {
				datasize = DataRepo.findByFilterId(check);
				datasize.setSize(datasize.getSize()-data.getSize());
				DataRepo.save(datasize);
				check = datasize.getParentId();
			}while(datasize.getParentId()!=null);
			
			data = DataRepo.deleteByNameAndParentId(name,parentid);
			deletepath(data.getFilterId(),name);
		}
		return data;

	}
	
	public List<Data_Model> deletepath(String filterid,String name) {
		List<Data_Model> data = DataRepo.deleteByParentId(filterid);
		return data;

	}

	@Override
		public Data_Model getDetails(String name, String uuid) {

			Data_Model data = DataRepo.findByNameAndUuid(name,uuid);

			return data;
		}

	@Override
	public Data_Model findbynamenpath(String parentid,String name) 
	{
		Data_Model data = DataRepo.findByNameAndParentId(name,parentid);
		return data;
	}

	@Override
	public Data_Model findbynamenfilterpath(String filterpath,String name)
	{
		Data_Model data = DataRepo.findByNameAndFilterPath(name,filterpath);
		return data;
	}



}