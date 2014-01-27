package com.ceazy.lib.SuperTag.Video;

import com.google.gson.annotations.SerializedName;

public class VideoSnippet {
	
	@SerializedName("publishedAt")
	String date;
	String channelId, title, description, channelTitle;
	VideoThumbnails thumbnails;
	
	public VideoSnippet() {
		
	}
	
	protected String getDate() {
		return date;
	}
	
	protected String getTitle() {
		return title;
	}
	
	protected String getChannelId() {
		return channelId;
	}
	
	protected String getDescription() {
		return description;
	}
	
	protected String getChannelTitle() {
		return channelTitle;
	}
	
	protected String getThumbnailURL() {
		return thumbnails.getMediumThumbnail().getURL();
	}
	
	
	class VideoThumbnails {
		MediumThumbnail medium;
		
		protected MediumThumbnail getMediumThumbnail() {
			return medium;
		}
	}
	
	class MediumThumbnail {
		String url;
		
		protected String getURL() {
			return url;
		}
	}

}
