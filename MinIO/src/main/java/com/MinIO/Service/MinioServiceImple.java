//package com.MinIO.Service;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.MinIO.Configuration.AWSClientConfigService;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CopyObjectRequest;
//import com.amazonaws.services.s3.model.ListObjectsRequest;
//import com.amazonaws.services.s3.model.ObjectListing;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
//import com.amazonaws.services.s3.model.S3ObjectSummary;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Service
//public class MinioServiceImple implements MinioService {
//
//	@Autowired
//	private AWSClientConfigService s3Client;
//	private String temp = null;
//	private String fileName = null;
//	private boolean copycut = false;
//	File object = new File("src/test/sample.txt");
//	
//	ObjectMapper objectMapper = new ObjectMapper();
//	RestTemplate restTemplate = new RestTemplate();
//
//	
//	@Override
//	public AmazonS3 authorize(String token) {
//		token = token.replace("Bearer ", "");
//		AmazonS3 minioclient = s3Client.awsClientConfiguration(token);
//		return minioclient;
//	}
//
//	@Override
//	public File convertMultiPartFileToFile(final MultipartFile multipartFile) {
//		final File file = new File(multipartFile.getOriginalFilename());
//		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
//			outputStream.write(multipartFile.getBytes());
//		} catch (final IOException ex) {
////            LOGGER.error("Error converting the multi-part file to file= ", ex.getMessage());
//			System.out.println("Error converting the multi-part file to file= " + ex.getMessage());
//		}
//		return file;
//	}
//
////	@Override
////	public String uploadFile(MultipartFile file, String jsondata, String docType, String filepath, String token,
////			String username) throws Exception {
////
////		AmazonS3 minioclient = authorize(token);
////
////		String delimiter = "/";
////	    if (!filepath.endsWith(delimiter)) {
////	    	filepath += delimiter;
////	    }
////	    
////		ObjectMetadata data = new ObjectMetadata();
////		data.addUserMetadata("docType", docType);
////
////		newFolder(filepath, token, username);
//////		data.getUserMetaDataOf(filepath);
////		boolean check = minioclient.doesBucketExistV2(username);
////		if (check != true) {
////			minioclient.createBucket(username);
////		}
////
////		HttpHeaders headers = new HttpHeaders();
////
////		if (file != null && minioclient.doesObjectExist(username, filepath)) {
////			
////			File filee = convertMultiPartFileToFile(file);
////
////			if (minioclient.doesObjectExist(username, filepath + "src/test/sample.txt") && filee != null) {
////				deleteFile(filepath + "/object.txt", token, username);
////			}
////
////			String fileName = file.getOriginalFilename();
////			filepath = filepath + fileName;
////			InputStream targetStream = new FileInputStream(filee);
////			minioclient.putObject(username, filepath, targetStream, data);
//////			minioclient.putObject(username, filepath, filee);
////
////				
////			String url = "http://127.0.0.1:9001" + "/" + username + "/" + filepath;
////
////			ObjectMetadata metadata = minioclient.getObjectMetadata(username, filepath);
//////			minioclient.generatePresignedUrl(username, filepath, DateTime.Now.AddMinutes(5));
////			headers.setContentType(MediaType.APPLICATION_JSON);
////			headers.add("Authorization", token);
////			headers.add("docType", docType);
////			headers.add("url", url);
////			headers.add("size", getFileSize(file));
//////			headers.addAll("lastModified", metadata.getLastModified());
////			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
////
////			HttpEntity<String> entity = new HttpEntity<String>(jsondata, headers);
////			System.out.println(headers);
////			System.out.println(docType);
////
////			ResponseEntity<String> loginResponse = restTemplate.postForEntity("http://localhost:8081/mongo/create",
////					entity, String.class, "");
////			System.out.println(loginResponse);
////			System.out.println(minioclient.getUrl(username, filepath));
//////			return minioclient.getUrl(username, filepath);
//////			return url;
//////			return file.getContentType();
//////			return data.getUserMetaDataOf(filepath);
////		}
////
//////		 File newfile = new File(filepath);
//////		 minioclient.putObject(new PutObjectRequest(username, filepath,"/path/to/file/object.txt" ));		 
//////		 minioclient.putObject(new PutObjectRequest(username, filepath,filee));
////		return null;
////
////	}
//
//	@Override
//	public void newFolder(String filepath, String token, String username) {
//		AmazonS3 minioclient = authorize(token);
//		boolean check = minioclient.doesBucketExistV2(username);
//		if (check != true) {
//			minioclient.createBucket(username);
//		}
//
//		boolean check2 = minioclient.doesObjectExist(username, filepath);
//		if (!check2) {
//			minioclient.putObject(username, filepath, object);
//		} else
//			return;
//	}
//
//	@Override
//	public String getTemp() {
//		return temp;
//	}
//
//	@Override
//	public void setTemp(String temp) {
//		this.temp = temp;
//	}
//
//	@Override
//	public String getName() {
//		return fileName;
//	}
//
//	@Override
//	public void setName(String fileName) {
//		this.fileName = fileName;
//	}
//
//	@Override
//	public boolean getbool() {
//		return copycut;
//	}
//
//	@Override
//	public void setbool(boolean copycut) {
//		this.copycut = copycut;
//	}
//
//	@Override
//	public void pasteobj(String filepath1, String filepath2, String token, String username) {
//		AmazonS3 minioclient = authorize(token);
//
//		CopyObjectRequest copyObjRequest = new CopyObjectRequest(username, filepath1, username, filepath2);
//		minioclient.copyObject(copyObjRequest);
//
//		if (!getbool()) {
//			deleteFile(filepath1, token, username);
//		}
//	}
//
//	@Override
//	public void copyFile(MultipartFile file, String filepath, String token) {
//		AmazonS3 minioclient = authorize(token);
//		filepath = filepath + file.getOriginalFilename();
//		setTemp(filepath);
//		setName(file.getOriginalFilename());
//		setbool(true);
//	}
//
////	List<Bucket> bucketList =
////    minioClient.listBuckets(ListBuckets.builder().extraHeaders(headers).build());
//
//	@Override
//	public String deleteFile(String filepath, String token, String username) {
//		AmazonS3 minioclient = authorize(token);
//		HttpHeaders headers = new HttpHeaders();
//
//		ObjectMetadata data = minioclient.getObjectMetadata(username, filepath);
//
//		String url = "http://127.0.0.1:9001" + "/" + username + "/" + filepath;
//
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.add("Authorization", token);
//		headers.add("docType", data.getUserMetadata().get("docType"));
//		headers.add("url", url);
//		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
//
//		ResponseEntity<String> Data = restTemplate.exchange("http://localhost:8081/mongo/deleteData", HttpMethod.DELETE,
//				entity, String.class);
//
//		minioclient.deleteObject(username, filepath);
//
//		return Data.toString();
//	}
//
//	@Override
//	public byte[] getFile(String filepath, String token, String username) {
//		AmazonS3 minioclient = authorize(token);
//		S3Object minioclient1 = minioclient.getObject(username, filepath);
//		S3ObjectInputStream inputStream = minioclient1.getObjectContent();
//		try {
//			byte[] content = IOUtils.toByteArray(inputStream);
//			return content;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Override
//	public String getFileSize(MultipartFile file) {
//		double sizeFile = file.getSize() / Math.pow(1024, 2);
//		String res = Math.round(sizeFile * 100) / 100.0 + " MB";
//		return res;
//	}
//
//	@Override
//	public List<?> getData(String docType, String token) {
//
//		HttpHeaders headers = new HttpHeaders();
//
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.add("Authorization", token);
//		headers.add("docType", docType);
//		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
//
//		ResponseEntity<List<?>> Data = restTemplate.exchange("http://localhost:8081/mongo/getData", HttpMethod.GET,
//				entity, new ParameterizedTypeReference<List<?>>() {
//				});
//
//		return Data.getBody();
//	}
//	
//	@Override
//	public List<String> listKeysInDirectory(String username, String prefix, String token)
//	{
//		AmazonS3 minioclient = authorize(token);
//		
////		List<String> paths = new ArrayList<String>();
////		
//		Boolean isTopLevel = false;
//	    String delimiter = "/";
//	    if(prefix == "" || prefix == "/") {
//	      isTopLevel = true;
//	    }
//	    if (!prefix.endsWith(delimiter)) {
//	      prefix += delimiter;
//	    }
////
////	    ListObjectsRequest listObjectsRequest = null;
////	    if (isTopLevel) {
////	      listObjectsRequest =
////	          new ListObjectsRequest().withBucketName(username).withDelimiter(delimiter);
////	    } else {
////	      listObjectsRequest = new ListObjectsRequest().withBucketName(username).withPrefix(prefix)
////	          .withDelimiter(delimiter);
////	    }
////	    ObjectListing objects = minioclient.listObjects(listObjectsRequest);
//////	    return objects.getCommonPrefixes();
////	    paths.addAll(objects.getCommonPrefixes());
////	    
////	    while (objects == null || objects.isTruncated()) {
////	    	objects = minioclient.listNextBatchOfObjects(objects);
////            paths.addAll(objects.getCommonPrefixes());
////        }
////        return paths;
//		
//		ListObjectsRequest listObjectsRequest =
//                new ListObjectsRequest()
//                        .withBucketName(username).withPrefix(prefix)
//          	          .withDelimiter(delimiter);
//
//        List<String> keys = new ArrayList<>();
//
//        ObjectListing objects = minioclient.listObjects(listObjectsRequest);
//
//        while (true) {
//            List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();
//            if (objectSummaries.size() < 1) {
//                break;
//            }
//            ObjectListing listObjects = minioclient.listObjects("miniouser","Files");
////            for ( item : listObjects) {
//////                if (!item.getKey().endsWith(prefix))
////                    keys.add(item.getKey());
////            }
//            List<S3ObjectSummary> objectSummaries2 = listObjects.getObjectSummaries();
//            for(S3ObjectSummary obj:objectSummaries2)
//            {
//            	System.out.println("key =>"+obj.getKey());
//            	if(obj.getKey().contains(prefix))
//            	{
//            		String[] split = obj.getKey().split("/");
//            		if(!(split.length==1))
//            		{
//            			if(!keys.contains(split[1]))
//            				keys.add(split[1]);
//            			System.out.println(split[1]);
//            		}
//            	}
//            	
//            }
//            objects = minioclient.listNextBatchOfObjects(objects);
//        }
//
//        return keys;
//
//	}
//
//	@Override
//	public String uploadFile1(MultipartFile file, String docType, String filepath, String token, String username)
//			throws Exception {
//
//		AmazonS3 minioclient = authorize(token);
//
//		ObjectMetadata data = new ObjectMetadata();
//		data.addUserMetadata("docType", docType);
//
//		boolean check = minioclient.doesBucketExistV2(username);
//		if (check != true) {
//			minioclient.createBucket(username);
//		}
//
//		HttpHeaders headers = new HttpHeaders();
//		newFolder(filepath, token, username);
//
//		if (file != null && minioclient.doesObjectExist(username, filepath)) {
////			boolean check = minioclient.doesBucketExistV2(username);
////			if (check != true) {
////				minioclient.createBucket(username);
////			}
//
//			File filee = convertMultiPartFileToFile(file);
//
//			if (minioclient.doesObjectExist(username, filepath + "src/test/sample.txt") && filee != null) {
//				deleteFile(filepath + "/object.txt", token, username);
//			}
//
//			String fileName = file.getOriginalFilename();
//			filepath = filepath + fileName;
//			InputStream targetStream = new FileInputStream(filee);
//			minioclient.putObject(username, filepath, targetStream, data);
////			minioclient.putObject(username, filepath, filee);
//
//			String url = "http://127.0.0.1:9001" + "/" + username + "/" + filepath;
//
//			ObjectMetadata metadata = minioclient.getObjectMetadata(username, filepath);
////			minioclient.generatePresignedUrl(username, filepath, 09/08/2022 15:35:44);
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			headers.add("Authorization", token);
//			headers.add("docType", docType);
//			headers.add("url", url);
//			headers.add("size", getFileSize(file));
////			headers.addAll("lastModified", metadata.getLastModified());
//			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//			return url;
//		}
//		return null;
//
//	}
//
//	@Override
//	public String uploadFile(MultipartFile file, String data, String action, String path, String token,
//			String username) throws Exception {
//
//		AmazonS3 minioclient = authorize(token);
//
////		String delimiter = "/";
////	    if (!path.endsWith(delimiter)) {
////	    	path += delimiter;
////	    }
//		
//		path = "Files/";
//	    
//		ObjectMetadata mdata = new ObjectMetadata();
//		mdata.addUserMetadata("docType", "Music");
//
//		newFolder(path, token, username);
////		data.getUserMetaDataOf(filepath);
//		boolean check = minioclient.doesBucketExistV2(username);
//		if (check != true) {
//			minioclient.createBucket(username);
//		}
//
//		HttpHeaders headers = new HttpHeaders();
//
//		if (file != null && minioclient.doesObjectExist(username, path)) {
//			
//			File filee = convertMultiPartFileToFile(file);
//
//			if (minioclient.doesObjectExist(username, path + "src/test/sample.txt") && filee != null) {
//				deleteFile(path + "/object.txt", token, username);
//			}
//
//			String fileName = file.getOriginalFilename();
//			path = path + fileName;
//			InputStream targetStream = new FileInputStream(filee);
//			minioclient.putObject(username, path, targetStream, mdata);
////			minioclient.putObject(username, filepath, filee);
//
//				
//			String url = "http://127.0.0.1:9001" + "/" + username + "/" + path;
//
//			ObjectMetadata metadata = minioclient.getObjectMetadata(username, path);
////			minioclient.generatePresignedUrl(username, filepath, DateTime.Now.AddMinutes(5));
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			headers.add("Authorization", token);
//			headers.add("docType", "Music");
//			headers.add("url", url);
//			headers.add("size", getFileSize(file));
////			headers.addAll("lastModified", metadata.getLastModified());
//			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//			HttpEntity<String> entity = new HttpEntity<String>(data, headers);
//			System.out.println(headers);
////			System.out.println(docType);
//
//			ResponseEntity<String> loginResponse = restTemplate.postForEntity("http://localhost:8081/mongo/create",
//					entity, String.class, "");
//			System.out.println(loginResponse);
//			System.out.println(minioclient.getUrl(username, path));
//			
//			return url;
//		}
//		return null;
//
//	}
//}
