package com.MinIO.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.MinIO.models.Data_Model;
import com.MinIO.models.Payload;
import com.MinIO.models.Preview;
import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;


public interface MinioService {
	/**
	 * File upload interface
	 * 
	 * @param file Uploaded files
	 * @return Access address
	 * @throws Exception
	 */
//	public String uploadFile(MultipartFile file,String data, String action, String path,String token,String username) throws Exception;
//	public String deleteFile(String filepath,String token,String username);
//	public void pasteobj(String filepath,String filepath2,String token,String username);
//	public byte[] getFile(String key,String token,String username);
//	public File convertMultiPartFileToFile(final MultipartFile multipartFile);
//	public void copyFile(MultipartFile file,String filepath,String token);
//	public void newFolder(String filepath,String token,String username);
//	public AmazonS3 authorize(String token);
//	public String getTemp();
//	public void setTemp(String temp);
//	public String getName();
//	public void setName(String filename);
//	public boolean getbool();
//	public void setbool(boolean copycut);
//	public String getFileSize(MultipartFile file);
//	public List<?> getData(String docType, String token);
//	public List<String> listKeysInDirectory(String bucketName, String prefix, String token);
//	public String uploadFile1(MultipartFile file,String docType, String filepath,String token,String username) throws Exception;
	
	
	
	
	
	public Preview preview(String action, String path, boolean showHiddenItems, List<Data_Model> data, String token, String username)throws JsonMappingException, JsonProcessingException;
	public Preview newFolder(String action, String path, String name, List<Data_Model> data, Data_Model targetData,String token, String username)throws JsonMappingException, JsonProcessingException;
	public ResponseEntity<Data_Model> uploadFile(MultipartFile file, String action, String data, String path, String token,
			String username) throws Exception;
	public Preview delete(String action, String path, String[] names, List<Data_Model> data, String token, String username)
			throws JsonMappingException, JsonProcessingException;
//	public byte[] download(HashMap<String,Object> downloadInput, String token, String username) throws JsonMappingException, JsonProcessingException;
	public byte[] download(String names[], String path, String token, String filterId, String username);
	public Preview search(String action, String path, String searchString, boolean b, List<Data_Model> data,
			String token, String username);
	public Preview details(String action, String path, String[] names, List<Data_Model> data, String token,
			String username);
	public Preview rename(String action, String path, String name, String newName, List<Data_Model> data, String token,
			String username) throws JsonMappingException, JsonProcessingException;
	Preview copy(String action, String[] names, String path, String targetPath, List<Data_Model> data,
			Data_Model targetData, String[] renameFiles, String token, String username, String newName) throws JsonMappingException, JsonProcessingException;
	public Preview move(String action, String[] names, String path, String targetPath, List<Data_Model> data,
			Data_Model targetData, String[] renameFiles, String token, String username, String newName) throws JsonMappingException, JsonProcessingException;
	//public void getPresignedUrl(S3Presigner presigner,String bucketName, String keyName);
	public String presignurl(String token, String username, String path, String filename) throws IOException;
}