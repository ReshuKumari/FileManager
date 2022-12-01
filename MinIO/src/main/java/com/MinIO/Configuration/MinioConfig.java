//package com.MinIO.Configuration;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import io.minio.MinioClient;
//
//@Configuration
//@ConfigurationProperties(prefix = "minio")
//public class MinioConfig {
//	
////      Service address
//    private String url;
////      user name
//    private String accessKey;
////      password
//    private String secretKey;
////      Bucket name
// 
//	@Value("${minio.bucket.name}")
//    String bucketName;
//    
//    public MinioConfig() {
//		super();
//	}
//    
//    public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public String getAccessKey() {
//		return accessKey;
//	}
//
//	public void setAccessKey(String accessKey) {
//		this.accessKey = accessKey;
//	}
//
//	public String getSecretKey() {
//		return secretKey;
//	}
//
//	public void setSecretKey(String secretKey) {
//		this.secretKey = secretKey;
//	}
//
//	public String getBucketName() {
//		return bucketName;
//	}
//
//	public void setBucketName(String bucketName) {
//		this.bucketName = bucketName;
//	}
//	
//
//	public MinioConfig(String url, String accessKey, String secretKey, String bucketName) {
//		super();
//		this.url = url;
//		this.accessKey = accessKey;
//		this.secretKey = secretKey;
//		this.bucketName = bucketName;
//	}
//
//	// Omit get and set methods
////
////	@Bean
////    public MinioClient getMinioClient()
////    {
////        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
////    }
////
////    
//}
