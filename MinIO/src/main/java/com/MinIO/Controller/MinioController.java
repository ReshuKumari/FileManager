package com.MinIO.Controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.MinIO.Configuration.AWSClientConfigService;
import com.MinIO.Service.MinioService;
import com.MinIO.models.Data_Model;
import com.MinIO.models.Error;
import com.MinIO.models.Payload;
import com.MinIO.models.Preview;
import com.MinIO.models.Upload_Model;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;


@RestController
@RequestMapping("/minio")
@CrossOrigin
public class  MinioController {

	@Autowired
	private MinioService minioService;
	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private AWSClientConfigService client;
	private static final Logger LOGGER = LoggerFactory.getLogger(MinioController.class);
	String policy_text = null;


	@GetMapping("/userLogin")
	public String userValidation() {
		return "User, Successfully logged in!";
	}

	@RequestMapping(value="/upload", method = RequestMethod.POST,consumes = {"multipart/form-data"})
//	@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "File Already exists")
	public ResponseEntity<?> upload(@RequestParam("uploadFiles") MultipartFile file,@RequestParam("action") String action,
									@RequestParam("path") String path,@RequestParam("data") String data,
			 @RequestHeader("username") String username,HttpServletRequest http)
			throws Exception {

		String token = http.getHeader("Authorization");

		ResponseEntity<Data_Model> response = minioService.uploadFile(file, action, data,path, token, username);
		
		if(response.getBody()==null)
		{
			Error err = new Error();
			err.setCode("400");
			err.setMessage("File Already Exists");
			
//			Map<String,String> response1 = new HashMap<String, String>();
//			response1.put("400", "File Already Exists");
//			return ResponseEntity.accepted().body(response1);
//			return new ResponseEntity<>();
//			return new ResponseEntity<err>((HttpStatus.OK));
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else if(action.equals("replace"))
		{
			return new ResponseEntity<>("File Already Exists",HttpStatus.OK);
			
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

	@RequestMapping("/operations")
	public Preview newfolder(@RequestBody Payload payload,
			 @RequestHeader("username") String username,HttpServletRequest http) throws JsonMappingException, JsonProcessingException
	{
		String token = http.getHeader("Authorization");
		
		System.out.println(payload.getPath());
		
		String action = payload.getAction();
		List<Data_Model> data = payload.getData();
		String path = payload.getPath();
		String name = payload.getName();
		String newName = payload.getNewName();
		String[] names = payload.getNames();
		String searchString = payload.getSearchString();
		String[] renameFiles = payload.getRenameFiles();
		Data_Model targetData = payload.getTargetData();
		String targetPath = payload.getTargetPath();

		System.out.println(names);
		if(action.equals("create"))
		{
			return minioService.newFolder(action,path,name,data,targetData,token, username);
		}
		else if(action.equals("read"))
		{
			return minioService.preview(action,path,false,data,token,username);
		}
		else if(action.equals("delete"))
		{
			return minioService.delete(action,path,names,data,token, username);
		}
		else if(action.equals("copy"))
		{
			return minioService.copy(action, names, path, targetPath, data, targetData, renameFiles, token, username,null);
		}
		else if(action.equals("move"))
		{
			return minioService.move(action, names, path, targetPath, data, targetData, renameFiles, token, username,null);
		}
		else if(action.equals("details"))
		{
			return minioService.details(action,path,names,data,token, username);
		}
		else if(action.equals("search"))
		{
			return minioService.search(action,path,searchString,false,data,token, username);
		}
		else if(action.equals("rename"))
		{
			return minioService.rename(action,path,name,newName,data,token,username);
		}
	
		return null;
	}
	
	@RequestMapping(value="/download",consumes = {"multipart/form-data"})
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("downloadInput") String downloadInput,
			 @RequestHeader("username") String username,HttpServletRequest http)
			throws IOException {
		
		String token = http.getHeader("Authorization");
		Payload payloads = objectMapper.readValue(downloadInput, Payload.class);

		String path = payloads.getPath();
		String[] names = payloads.getNames();
		String filterId = payloads.getData().get(0).getUuid();
		System.out.println("path "+path);
		System.out.println("names: "+names[0]);
		System.out.println("filterid: "+filterId);
		
		byte[] dataa =minioService.download(names,path,filterId,token,username);

		ByteArrayResource resource = new ByteArrayResource(dataa);
//		return resource;
		return ResponseEntity
                .ok()
                .contentLength(dataa.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + names[0]+"\"")
                .body(resource);
	
	}

	@RequestMapping(value="/share")
	public String getPresignedUrlController(@RequestHeader("Authorization") String token,
										  @RequestHeader("username") String username,
										  @RequestHeader("path") String path,
										  @RequestHeader("filename") String filename) throws IOException {
		token=token.replace("Bearer ", "");
		String username="reshukumari";
		String path="/folder/";
		String filename="hello.txt";
		String url = minioService.presignurl(token, username, path, filename);
		return url;
	}
	
//	@GetMapping("/search")
//	public List<?> getData(@RequestHeader("docType") String docType, @RequestHeader("Authorization") String token ){
//		List<?> data = minioService.getData(docType, token);
//		return data;
//		
//	}

//
//	@GetMapping("/test")
//	public String test(@RequestHeader("Authorization") String token) {
//		token = token.replace("Bearer ", "");
//		AmazonS3 awsClientConfiguration = client.awsClientConfiguration(token);
//		awsClientConfiguration.createBucket("test123");
//		GetObjectRequest nn = new GetObjectRequest("shruti", "folder1/Rangi Sari_192(PagalWorld.com.se).mp3");
//		awsClientConfiguration.getObject(nn).getObjectMetadata();
//		return "ok";
//	}
//
//	@GetMapping(path = "/download")
//	public ResponseEntity<ByteArrayResource> uploadFile(@RequestHeader("filepath") String filepath,
//			@RequestHeader("Authorization") String token, @RequestHeader("username") String username)
//			throws IOException {
//		byte[] data = minioService.getFile(filepath, token, username);
//		ByteArrayResource resource = new ByteArrayResource(data);
//
//		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
//				.header("Content-disposition", "attachment; filename=\"" + filepath + "\"").body(resource);
//	}

//
//	@PutMapping("/rename")
//	public String renameFile(@RequestHeader("filename") String filename, @RequestHeader("filepath") String filepath,
//			@RequestHeader("filepath2") String filepath2, @RequestHeader("Authorization") String token,
//			@RequestHeader("username") String username) throws InvalidKeyException, ErrorResponseException,
//			InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException,
//			ServerException, XmlParserException, IllegalArgumentException, IOException {
//		filepath2 = filepath2 + filename;
//		minioService.pasteobj(filepath, filepath2, token, username);
//		minioService.deleteFile(filepath, token, username);
//		return "File Renamed";
//	}
//
//	@GetMapping("/copy")
//	public String copyFile(MultipartFile file, @RequestHeader("filepath") String filepath,
//			@RequestHeader("Authorization") String token, @RequestHeader("username") String username)
//			throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
//			InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException,
//			IllegalArgumentException, IOException {
//		minioService.copyFile(file, filepath, token);
//		return "Copied successfully";
//	}
//
//	@GetMapping("/paste")
//	public String pasteobj(@RequestHeader("filepath") String filepath, @RequestHeader("Authorization") String token,
//			@RequestHeader("username") String username) throws InvalidKeyException, ErrorResponseException,
//			InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException,
//			ServerException, XmlParserException, IllegalArgumentException, IOException {
//		String temp = minioService.getTemp();
//		filepath = filepath + minioService.getName();
//		minioService.pasteobj(temp, filepath, token, username);
//		return "File Pasted successfully";
//	}
//
//	@GetMapping("/cut")
//	public String cutFile(MultipartFile file, @RequestHeader("filepath") String filepath,
//			@RequestHeader("Authorization") String token, @RequestHeader("username") String username)
//			throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
//			InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException,
//			IllegalArgumentException, IOException {
//		minioService.copyFile(file, filepath, token);
//		minioService.setbool(false);
//		String temp = minioService.getTemp();
////		    minioService.deleteFile(temp,token,username);
//		return "File cut successfully";
////			return temp;
//	}
////	
////	@RequestMapping("/policy")
////		public String getpolicy(@RequestHeader("Authorization") String token,@RequestHeader("username") String username)
////		{
////		policy_text = bucketpolicy.getPublicReadPolicy(username);
//////		bucketpolicy.setBucketPolicy(username,policy_text, token);
////		
//////		return "done!";
////	return policy_text;
////		}
//
////	 @GetMapping("/fetch")
////	 
////		 public String getdetails(@RequestHeader("filepath") String filepath,@RequestHeader("Authorization") String token,@RequestHeader("username") String username) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
////			 {
////				 
////			 }
////	 	
//
////	 @PostMapping("/create")
////	 public String create(@RequestHeader("name") String name) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException
////	 {
////		 
////		boolean found=client.bucketExists(BucketExistsArgs.builder().bucket(name).build());
////		if(!found)
////		{
////			client.makeBucket(
////				    MakeBucketArgs.builder()
////				        .bucket(name)
////				        .build());
////		}
////		 return null;
////	 }
	
}