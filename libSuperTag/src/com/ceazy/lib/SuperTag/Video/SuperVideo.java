package com.ceazy.lib.SuperTag.Video;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.os.Parcel;
import android.os.Parcelable;

public class SuperVideo implements Parcelable {
	
	String title, date, videoURL, description, thumbnailURL, channelTitle, channelURL;
	
	public SuperVideo(String title, String date) {
		setTitle(title);
		setDate(date);
	}
	
	protected SuperVideo(Parcel in) {
		setTitle(in.readString());
		setDate(in.readString());
		setChannelTitle(in.readString());
		setDescription(in.readString());
		setVideoURL(in.readString());
		setChannelURL(in.readString());
		setThumbnailURL(in.readString());
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setVideoURL(String videoId) {
		try {
			this.videoURL = URLEncoder.encode("http://www.youtube.com/watch?v=" + videoId,
					"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void setChannelURL(String channelId) {
		try {
			this.channelURL = URLEncoder.encode("http://www.youtube.com/channel/" +
					channelId, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getChannelTitle() {
		return channelTitle;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getVideoURL() {
		return videoURL;
	}
	
	public String getChannelURL() {
		return channelURL;
	}
	
	public String getThumbnailURL() {
		return thumbnailURL;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getTitle());
		out.writeString(getDate());
		out.writeString(getChannelTitle());
		out.writeString(getDescription());
		out.writeString(getVideoURL());
		out.writeString(getChannelURL());
		out.writeString(getThumbnailURL());
	}
	
	Creator<SuperVideo> CREATOR = new Creator<SuperVideo>() {

		@Override
		public SuperVideo createFromParcel(Parcel in) {
			return new SuperVideo(in);
		}

		@Override
		public SuperVideo[] newArray(int size) {
			return new SuperVideo[size];
		}
		
	};
	
	

}
