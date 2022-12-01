package com.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Video")
public class Video_Model {
    private String video_title;
	private String genre;
    private String size;
	private String url;

    public Video_Model(String video_title, String genre, String size, String url) {
		super();
		this.video_title = video_title;
		this.genre = genre;
		this.size = size;
		this.url = url;
	}

    
    public Video_Model() {
		super();
	}


	public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
