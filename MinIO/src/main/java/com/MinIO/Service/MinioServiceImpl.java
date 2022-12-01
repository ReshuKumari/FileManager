//package com.MinIO.Service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.MinIO.Configuration.MinioConfig;
//
//import Model.MinioModel;
//import io.minio.BucketExistsArgs;
//import io.minio.GetObjectArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//import io.minio.RemoveObjectArgs;
//import io.minio.errors.ErrorResponseException;
//import io.minio.errors.InsufficientDataException;
//import io.minio.errors.InternalException;
//import io.minio.errors.InvalidResponseException;
//import io.minio.errors.ServerException;
//import io.minio.errors.XmlParserException;
//
//@Service
//public class MinioServiceImpl implements MinioService {
//
//	@Autowired
//	private MinioConfig minioConfig;
//
//	@Autowired
//	private MinioClient client;
//
//	@Override
//	public String uploadFile(MultipartFile file, MinioModel music) throws Exception {
////		// Create buckets 
//		boolean createFlag = makeBucket(client, minioConfig.getBucketName());
////		// Failed to create bucket 
////		if (createFlag == false) {
////			return "Bucket failed to create";
////		}
//		Map<String, String> song = new HashMap<>();
//		song.put("title", music.getSongTitle());
//		song.put("artist", music.getArtist());
//		song.put("genre", music.getGenre());
//		try {
//		String fileName = file.getOriginalFilename();
//		PutObjectArgs args = PutObjectArgs.builder().bucket(minioConfig.getBucketName()).object("test/"+fileName)
//				.stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).userMetadata(song).build();
//		client.putObject(args); //Uploads given stream as object in bucket.
////		client.uploadObject(
////			    UploadObjectArgs.builder()
////			        .bucket("mymusic").object(fileName).filename(fileName).build());
//		return minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + fileName;
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return "error";
//			}
//	}
//	
//	public boolean bucketExist(MinioClient client,String bucketName) {
//		try {
//			boolean found =
//					  client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//			if (found) {
//				  System.out.println("my-bucketname exists");
//				  return true;
//				}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return false;
//	}
//
//	
//	/** * Create buckets * * @param bucketName Bucket name */
//	public boolean makeBucket(MinioClient client,String bucketName) {
//		try {
//			boolean flag = bucketExist(client , bucketName);
//			// If the bucket does not exist, create a bucket
//			if (!flag) {
//				client.makeBucket(
//					    MakeBucketArgs.builder()
//					        .bucket(bucketName)
//					        .build());
//			}
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//    public byte[] getFile(String key) {
//
//    	try (InputStream stream = client.getObject(
//    			  GetObjectArgs.builder()
//    			  .bucket("mymusic")
//    			  .object(key)
//    			  .build())) {
//    			  // Read data from stream
//    		byte[] content = IOUtils.toByteArray(stream);
//            stream.close();
//            return content;
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        return null;
//    }
//    
//    public void deleteFile(String songTitle) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
//    	client.removeObject(
//    		    RemoveObjectArgs.builder().bucket("mymusic").object(songTitle).build());
//    }
//}