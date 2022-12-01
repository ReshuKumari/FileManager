package com.MinIO.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.MinIO.Configuration.AWSClientConfigService;
import com.MinIO.models.Data_Model;
import com.MinIO.models.Details;
import com.MinIO.models.Error;
import com.MinIO.models.Payload;
import com.MinIO.models.Preview;
import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.time.Duration;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.utils.IoUtils;

import javax.xml.crypto.Data;
///import software.amazon.awssdk.auth.credentials;

@Service
public class ServiceImpl implements MinioService {

	@Autowired
	private AWSClientConfigService s3Client;
	File object = new File("src/test/sample.txt");

	ObjectMapper objectMapper = new ObjectMapper();
	RestTemplate restTemplate = new RestTemplate();

	public AmazonS3 authorize(String token) {
		token = token.replace("Bearer ", "");
		AmazonS3 minioclient = s3Client.awsClientConfiguration(token);
		return minioclient;
	}

	public File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			System.out.println("Error converting the multi-part file to file= " + ex.getMessage());
		}
		return file;
	}

	@Override
	public ResponseEntity<Data_Model> uploadFile(MultipartFile file, String action, String data, String path, String token, String username)
			throws Exception {

		AmazonS3 minioclient = authorize(token);

		Data_Model Data = setvalues(data);

		boolean check = minioclient.doesBucketExistV2(username);
		if (check != true) {
			minioclient.createBucket(username);
		}

		BucketVersioningConfiguration configuration = new BucketVersioningConfiguration().withStatus("Enabled");

		SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(
				username, configuration);

		minioclient.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);

		BucketVersioningConfiguration conf = minioclient.getBucketVersioningConfiguration(username);
		System.out.println("bucket versioning configuration status:    " + conf.getStatus());

		HttpHeaders headers = new HttpHeaders();
		
		if (file != null) {

			File filee = convertMultiPartFileToFile(file);

			String uuid = (UUID.randomUUID()).toString();
			String filepath = "Files" + path  + Data.getUuid();

			String length = Long.toString(file.getSize());
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", token);
			headers.add("path", path);
			headers.add("name", file.getOriginalFilename());
			headers.add("type", "." + file.getOriginalFilename().split("[.]")[1]);
			headers.add("size", length);
			headers.add("action", action);
			headers.add("uuid", uuid);
			headers.add("targetid", null);
			
			HttpEntity<Data_Model> entity = new HttpEntity<>(Data, headers);

			ResponseEntity<Data_Model> loginResponse = restTemplate.postForEntity("http://localhost:8081/mongo/create",
					entity, Data_Model.class);
			
			if(loginResponse.getBody()!=null)
			{
				if(action.equals("replace"))
				{
					String key = "Files" + path + loginResponse.getBody().getUuid();
					minioclient.deleteObject(username, key);
					minioclient.putObject(username, filepath, filee);
				}
				else
				{
					minioclient.putObject(username, filepath, filee);
				}
			}
			
			System.out.println("loginResponse : "+loginResponse);
			
			return loginResponse;
		}
		return null;
	}

	@Override
	public Preview newFolder(String action, String path, String name, List<Data_Model> data, Data_Model targetData, String token,
			String username) throws JsonMappingException, JsonProcessingException {
		AmazonS3 minioclient = authorize(token);

		Data_Model Data = data.get(0);

		if (!Data.equals(null)) {

			Preview preview = new Preview();

			if (Data.getType().equals("Folder")) {
				boolean check = minioclient.doesBucketExistV2(username);
				if (check != true) {
					minioclient.createBucket(username);
					BucketVersioningConfiguration configuration = new BucketVersioningConfiguration().withStatus("Enabled");

					SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(
							username, configuration);

					minioclient.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);

					BucketVersioningConfiguration conf = minioclient.getBucketVersioningConfiguration(username);
					System.out.println("bucket versioning configuration status:    " + conf.getStatus());
				}

				String filepath ="Files" +  path + name;
				String uuid = (UUID.randomUUID()).toString();
//				String uid = (UUID.randomUUID()).toString();
				
				boolean check2 = minioclient.doesObjectExist(username, filepath);
				if (!check2) {
					filepath = filepath + "/";
					minioclient.putObject(username, filepath, object);

					String length = Long.toString(Data.getSize());
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					headers.add("Authorization", token);
					headers.add("name", name);
					headers.add("path", path);
					headers.add("type", Data.getType());
					headers.add("size", length);
					headers.add("action", action);
					headers.add("uuid", uuid);
					if(action.equals("copy"))
						headers.add("targetid", targetData.getFilterId());
					else
						headers.add("targetid", null);
					
					System.out.println("action11111......."+action);

					HttpEntity<Data_Model> entity = new HttpEntity<>(Data, headers);

					ResponseEntity<Data_Model> loginResponse = restTemplate
							.postForEntity("http://localhost:8081/mongo/create", entity, Data_Model.class);
					
					List<Data_Model> files = new ArrayList<>();
					Data_Model data1 = loginResponse.getBody();
					if(data1==null)
					{
						Error err = new Error();
						err.setCode("400");
						err.setMessage("A file or folder with the name " + name +" already exists.");
						preview.setError(err);
						
					}
					else
					{
						files.add(Data);
						preview.setFiles(files);
					}

					return preview;
				}
			}
		}
		return null;
	}

	public Preview preview(String action, String path, boolean showHiddenItems, List<Data_Model> data, String token,
			String username) throws JsonMappingException, JsonProcessingException {

		Preview preview = new Preview();

		HttpHeaders head = new HttpHeaders();
		head.add("Authorization", token);
		head.add("username", username);

		HttpEntity<Object> entity1 = new HttpEntity<Object>(head);

		ResponseEntity<Data_Model> response1 = restTemplate.exchange("http://localhost:8081/mongo/findByName/Files",
				HttpMethod.GET, entity1, Data_Model.class);
		Data_Model data1 = response1.getBody();
		
		HttpHeaders headers = new HttpHeaders();
		
		String filterId = data1.getFilterId();
		if(!data.isEmpty()) {
			filterId = data.get(0).getFilterId();
		}
		
		System.out.println(filterId);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", token);
		headers.add("username", username);
		headers.add("parentId", filterId);
		headers.add("path", path);

		HttpEntity<Object> entity = new HttpEntity<Object>(headers);

		ResponseEntity<List<Data_Model>> response = restTemplate.exchange(
				"http://localhost:8081/mongo/findByFilterPath", HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Data_Model>>() {
				});
		List<Data_Model> files = response.getBody();

		System.out.println(data1);

		if (data.isEmpty()) {
			data1.setId(null);
			preview.setCwd(data1);
		} else {
			data.get(0).setId(null);
			preview.setCwd(data.get(0));
		}

		preview.setFiles(files);

		return preview;

	}

	@Override
	public Preview delete(String action, String path, String[] names, List<Data_Model> data, String token,
			String username) throws JsonMappingException, JsonProcessingException {
		AmazonS3 minioclient = authorize(token);
		Preview preview = new Preview();

		for(int i=0;i<names.length;i++)
		{
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", token);
		headers.add("parentid", data.get(0).getParentId());
		headers.add("name", names[i]);

		HttpEntity<Object> entity = new HttpEntity<Object>(headers);

			ResponseEntity<Data_Model> Data = restTemplate.exchange("http://localhost:8081/mongo/deleteData",
					HttpMethod.DELETE, entity, Data_Model.class);

		String filepath = "Files" + path + names[i];
		List<S3ObjectSummary> objectSummaries = minioclient.listObjects(username, filepath).getObjectSummaries();
		System.out.println(objectSummaries);
		
		if (!data.get(0).getType().equals("Folder")) {
			filepath = "Files" + path + data.get(0).getUuid();
			minioclient.deleteObject(username, filepath);
		} 
		else {
//			String version = minioclient.getObject(username, filepath + "/").getObjectMetadata().getVersionId();
			if (objectSummaries.size() >=1)
			{
				for (S3ObjectSummary file : minioclient.listObjects(username, filepath).getObjectSummaries()) {
					String version = minioclient.getObject(username, file.getKey()).getObjectMetadata().getVersionId();
					System.out.println("key : " + file.getKey());
					minioclient.deleteVersion(username, file.getKey(),version);
				}
			}
			minioclient.deleteObject(username, filepath +"/");
		}
		}		
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", token);
		headers.add("parentId", data.get(0).getParentId());
		headers.add("path", path);

		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		
		ResponseEntity<List<Data_Model>> response = restTemplate.exchange(
				"http://localhost:8081/mongo/findByFilterPath", HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Data_Model>>() {
				});
		List<Data_Model> files = response.getBody();

		preview.setFiles(files);
		return preview;
	}

	public byte[] download(String names[], String path, String filterId,String token, String username) {	
		AmazonS3 minioclient = authorize(token);

		System.out.println(filterId);
		S3Object minioclient1 = minioclient.getObject(username,"Files"+ path + filterId);
		S3ObjectInputStream inputStream = minioclient1.getObjectContent();
		try {
				byte[] content = IOUtils.toByteArray(inputStream);
				return content;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
			
		}

	@Override
	public Preview search(String action, String path, String searchString, boolean b, List<Data_Model> data,
			String token, String username) {
		
		AmazonS3 minioclient = authorize(token);
		Preview preview = new Preview();
		
		return preview;
	}


	@Override
	public Preview details(String action, String path, String[] names, List<Data_Model> data, String token,
			String username) {
		
		AmazonS3 minioclient = authorize(token);
		Preview preview = new Preview();
		Details details = new Details();
		String type = null;
		long size=0;
		boolean f = false;
		if(names.length>1)
		{
			for(int i=0;i<names.length;i++)
			{
				HttpHeaders headers = new HttpHeaders();

				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.add("Authorization", token);
				headers.add("uuid", data.get(i).getUuid());
				headers.add("name", data.get(i).getName());

				HttpEntity<Object> entity = new HttpEntity<Object>(headers);
				ResponseEntity<Data_Model> Data = restTemplate.exchange("http://localhost:8081/mongo/getDetail",
						HttpMethod.GET, entity, Data_Model.class);
			    Data_Model detail = Data.getBody();

			    if(type==null)
			    {
			    	type = detail.getType();
			    }
			    if(!type.equals(detail.getType()))
			    {
			    	f=true;
			    	break;
			    }
			    
			    type = detail.getType();
			    size = detail.getSize() + size;
			}
			
			if(f)
			{
				details.setIsFile(false);
				details.setSize("0 B");
			}
			else if(!type.equals("Folder"))
			{
				details.setIsFile(true);
				details.setSize("0 B");
			}
			else 
			{
				details.setIsFile(false);
				details.setSize(Long.toString(size));
			}
			
			details.setLocation("Various Files or Folders");
			details.setCreated("0001-01-01T00:00:00");
			details.setModified("0001-01-01T00:00:00");
			details.setMultipleFiles(true);
			
			String name = ""
					;
			for(int j=0;j<names.length;j++)
			{
				name = name + names[j] + ", ";
			}
			details.setName(name.substring(0, name.length()-2));
			
			preview.setDetails(details);
		}
		else
		{
			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", token);
			headers.add("uuid", data.get(0).getUuid());
			headers.add("name", data.get(0).getName());

			HttpEntity<Object> entity = new HttpEntity<Object>(headers);
			ResponseEntity<Data_Model> Data = restTemplate.exchange("http://localhost:8081/mongo/getDetail",
					HttpMethod.GET, entity, Data_Model.class);
		    Data_Model detail = Data.getBody();
			
		    Details delta = new Details();
		    delta.setMultipleFiles(false);
		    delta.setCreated(detail.getDateCreated());
		    delta.setIsFile(detail.getIsFile());
		    if(path==null)
		    	delta.setLocation(data.get(0).getName());
		    else if(detail.getType().equals("Folder"))
		    {
		    	String filterpath =  path.substring(0, path.length()-1);
		    	delta.setLocation("Files" + filterpath);
		    }
		    else
		    delta.setLocation("Files" + path + data.get(0).getName());
		    delta.setModified(detail.getDateModified());
		    delta.setName(data.get(0).getName());
		    delta.setSize(Long.toString(detail.getSize()));
		    
		    preview.setDetails(delta);
		}
		return preview;
	}
	
	@Override
	public Preview copy(String action, String[] names, String path,String targetPath,
						List<Data_Model> data,Data_Model targetData,String[] renameFiles,String token,String username,
						String newName) throws JsonMappingException, JsonProcessingException {
		AmazonS3 minioclient = authorize(token);
		Preview preview = new Preview();
		List<Data_Model> files = new ArrayList<>();
		for(int k=0;k<names.length;k++)
		{
			String filepath = "";
			String target = "Files" + targetPath;
			if(data.get(k).getType().equals("Folder"))
			{
				filepath = "Files" + path + names[k];
				target = target + names[k];
				
				newFolder(action,targetPath,names[k],data,targetData,token, username);
				System.out.println("action......."+action);
				
				List<S3ObjectSummary> objectSummaries = minioclient.listObjects(username, filepath).getObjectSummaries();
				
				HttpHeaders headers = new HttpHeaders();

				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.add("Authorization", token);
				headers.add("parentId", data.get(0).getFilterId());
				headers.add("path", path);
				
				System.out.println("parentid......."+data.get(0).getFilterId());
				
				HttpEntity<Object> entity = new HttpEntity<Object>(headers);
				
				ResponseEntity<List<Data_Model>> response1 = restTemplate.exchange(
						"http://localhost:8081/mongo/findByFilterPath", HttpMethod.GET, entity,
						new ParameterizedTypeReference<List<Data_Model>>() {
						});
			    List<Data_Model> detail = response1.getBody();
			    System.out.println("detail ka size : " + detail.size());
			    System.out.println("objsumm ka size : " + objectSummaries.size());
				for (int i=1;i<objectSummaries.size();i++) {
				    
				    String[] tokens=objectSummaries.get(i).getKey().split("[/]"); 
					
				    String length = Long.toString(detail.get(i-1).getSize());
				    String uuid = (UUID.randomUUID()).toString();
				    
					HttpHeaders head = new HttpHeaders();
					head.setContentType(MediaType.APPLICATION_JSON);
					head.add("Authorization", token);
					
					if(tokens[tokens.length-1].equals(newName))
						head.add("name", newName);
					else
					    head.add("name", detail.get(i-1).getName());
					head.add("path", "" );
					head.add("targetdata",targetData.getFilterId());
					head.add("size", length);
					head.add("action", action);
					head.add("uuid", uuid);
					head.add("names", names[k]);
					head.add("type", data.get(k).getType());

					HttpEntity<Data_Model> response = new HttpEntity<>(detail.get(i-1), head);

					ResponseEntity<Data_Model> loginResponse = restTemplate
							.postForEntity("http://localhost:8081/mongo/copy", response, Data_Model.class);
					
					Data_Model data1 = loginResponse.getBody();

					String tag =  target + objectSummaries.get(i).getKey().split(names[k])[1];
					
				    String version = minioclient.getObject(username, objectSummaries.get(i).getKey()).getObjectMetadata().getVersionId();
				    if(!minioclient.doesObjectExist(username, tag))
				    {
				    	CopyObjectRequest copyObjRequest = new CopyObjectRequest(username, objectSummaries.get(i).getKey(),username,tag);
						minioclient.copyObject(copyObjRequest);
				    }
					System.out.println("dataa"+data1);
					if(data1==null)
					{
						Error err = new Error();
						err.setCode("400");
						err.setMessage("File Already Exists");
						err.setFileExists(names);
						preview.setError(err);
					}
					else
					{
						files.add(data1);
						preview.setFiles(files);
						preview.setCwd(targetData);
					}		
					}

			}
			else
			{
				filepath ="Files" + path + data.get(k).getUuid();
				
				String length = Long.toString(data.get(k).getSize());
				String uuid = (UUID.randomUUID()).toString();
				
				HttpHeaders head = new HttpHeaders();
				head.setContentType(MediaType.APPLICATION_JSON);
				head.add("Authorization", token);
				if(action.equals("rename"))
					head.add("name", newName);
				else
				head.add("name", names[k]);
				head.add("path", "");
				head.add("targetdata", targetData.getFilterId() );
				head.add("size", length);
				head.add("uuid", uuid);
				head.add("action", action);
				head.add("names", names[k]);
				head.add("type", data.get(k).getType());
				
				HttpEntity<Data_Model> response = new HttpEntity<>(data.get(k), head);

				ResponseEntity<Data_Model> loginResponse = restTemplate
						.postForEntity("http://localhost:8081/mongo/copy", response, Data_Model.class);
					
				Data_Model data1 = loginResponse.getBody();
				
				target = target + data1.getUuid();
				CopyObjectRequest copyObjRequest = new CopyObjectRequest(username,filepath,username,target);
				minioclient.copyObject(copyObjRequest);
				
				files.add(data1);
			}
				preview.setFiles(files);
				preview.setCwd(targetData);
			}
			
	return preview;
	}
			

	@Override
	public Preview move(String action, String[] names, String path, String targetPath, List<Data_Model> data,
			Data_Model targetData, String[] renameFiles, String token, String username, String newName) throws JsonMappingException, JsonProcessingException {
		Preview preview = new Preview();
		Preview preview1 = copy(action, names, path, targetPath, data, targetData, renameFiles, token, username,newName);
	    delete(action,path,names,data,token, username);

		List<Data_Model> files = new ArrayList<>();
		if(!preview1.getFiles().isEmpty())
		files.add(preview1.getFiles().get(0));
		preview.setFiles(files);
		
		String[] tokens=path.split("[/]"); 
		String tag = "";
		
		if(path.equals("/"))
		{
			tag= path;
		}
		else
		tag= path.split(tokens[tokens.length-1])[0];
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", token);
		if(path.equals("/"))
		{
			headers.add("filterpath", "");
			headers.add("name","Files");
		}
		else {
			headers.add("filterpath", tag);
			headers.add("name",tokens[tokens.length-1]);
		}
		
		
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		ResponseEntity<Data_Model> Data = restTemplate.exchange("http://localhost:8081/mongo/get",
				HttpMethod.GET, entity, Data_Model.class);
	    Data_Model detail = Data.getBody();
		preview.setCwd(detail);
		return preview;
	}

	
	@Override
	public Preview rename(String action, String path, String name, String newName, List<Data_Model> data, String token,
			String username) throws JsonMappingException, JsonProcessingException {
		
		Preview preview = new Preview();
		String[] names= new String[1];
		String[] renameFiles = new String[1];
		names[0] = name;

		Preview preview1 = move(action, names, path, path, data, data.get(0),renameFiles , token, username,newName);
		List<Data_Model> files = new ArrayList<>();
		if(!preview1.getFiles().isEmpty())
		files.add(preview1.getFiles().get(0));
		preview.setFiles(files);
		
		return preview;
	}
	
	public Data_Model setvalues(String dataa) throws JsonMappingException, JsonProcessingException {

		if (!dataa.isEmpty()) {
			Data_Model data = objectMapper.readValue(dataa, Data_Model.class);

			data.setType(data.getType());
			data.setName(data.getName());
			data.setHasChild(data.isHasChild());
			data.setFilterPath(data.getFilterPath());
			data.setCaseSensitive(data.isCaseSensitive());
			data.setData(data.getData());
			data.setDateCreated(data.getDateCreated());
			data.setDateModified(data.getDateModified());
			data.setFilterId(data.getFilterId());
			data.setId(null);
			data.setUuid((UUID.randomUUID()).toString());
			if (data.getType().equals("Folder") && !data.equals(null)) {
				data.setIsFile(false);
			} else
				data.setIsFile(data.getIsFile());
			data.setNames(data.getNames());
			data.setNewName(data.getNewName());
			data.setParentId(data.getParentId());
			data.setPath(data.getPath());
			data.setPermission(data.getPermission());
			data.setRenameFiles(data.getRenameFiles());
			data.setSearchString(data.getSearchString());
			data.setShowHiddenItems(data.isShowHiddenItems());
			data.setSize(data.getSize());
			data.setTargetData(data.getTargetData());
			data.setTargetPath(data.getTargetPath());
			data.setUploadFiles(data.getUploadFiles());
			data.setPreviousName(data.getPreviousName());
			data.set_fm_icon(data.get_fm_icon());
			data.set_fm_htmlAttr(data.get_fm_htmlAttr());

			return data;
		}
		return null;
	}

	public Payload setpayload(Payload payloads) throws JsonMappingException, JsonProcessingException {
//		Payload payloads = objectMapper.readValue(payload, Payload.class);

		payloads.setAction(payloads.getAction());
		payloads.setData(payloads.getData());
		payloads.setName(payloads.getName());
		payloads.setPath(payloads.getPath());
		payloads.setShowHiddenItems(payloads.isShowHiddenItems());

		return payloads;
	}

	public String presignurl(String token, String username, String path, String filename) throws IOException {
		Regions clientRegion = Regions.DEFAULT_REGION;
		String bucketName = username;
		int secondLast = path.length()-2;
		String filterpath = path.substring(0, path.lastIndexOf("/",secondLast)+1);
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", token);
		headers.add("name", filename);
		headers.add("filterpath", filterpath);

		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		ResponseEntity<Data_Model> Data = restTemplate.exchange("http://localhost:8081/mongo/getDataByFilterPath",
				HttpMethod.GET, entity, Data_Model.class);
		Data_Model detail = Data.getBody();
		String uuid = detail.getUuid();
		String objectKey = "Files"+path+uuid;//"Files/fold/fdc22e2b-26a1-4f7d-8090-0f4723d1449d";

		try {
			AmazonS3 minioclient = authorize(token);

			// Set the presigned URL to expire after one hour.
			java.util.Date expiration = new java.util.Date();
			long expTimeMillis = Instant.now().toEpochMilli();
			// link expire time is 1 day
			expTimeMillis += 1000 * 60 * 60 * 24;
			expiration.setTime(expTimeMillis);

			// Generate the presigned URL.
			System.out.println("Generating pre-signed URL.");
			GeneratePresignedUrlRequest generatePresignedUrlRequest =
					new GeneratePresignedUrlRequest(bucketName, objectKey)
							//.withMethod(HttpMethod.GET)
							.withExpiration(expiration);
			//URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
			URL url = minioclient.generatePresignedUrl(generatePresignedUrlRequest);
			//URL url2 = client.generatePresignedUrl(generatePresignedUrlRequest);

			System.out.println("Pre-Signed URL: " + url.toString());
			return url.toString();
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}
		return "";
	}


}
