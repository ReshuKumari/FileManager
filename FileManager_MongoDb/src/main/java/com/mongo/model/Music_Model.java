package com.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Music")
public class Music_Model extends Data_Model{

	private String songTitle;
	private String artist;
	private String genre;
//	private String docType;
	private String size;
	private String url;
//	private String LastModified;

	public Music_Model() {
		super();
	}

	public Music_Model(String songTitle, String artist, String genre, String size, String url) {
		super();
		this.songTitle = songTitle;
		this.artist = artist;
		this.genre = genre;
		this.size = size;
		this.url = url;
	}

	public String getSongTitle() {
		return songTitle;
	}

	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
