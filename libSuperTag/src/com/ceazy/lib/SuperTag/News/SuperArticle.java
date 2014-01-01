package com.ceazy.lib.SuperTag.News;

import android.os.Parcel;
import android.os.Parcelable;

public class SuperArticle implements Parcelable {
	
	String title, date, sourceURL, summary, author;
	
	public SuperArticle(String title, String date) {
		setTitle(title);
		setDate(date);
	}
	
	protected SuperArticle(Parcel in) {
		setTitle(in.readString());
		setDate(in.readString());
		setAuthor(in.readString());
		setSourceURL(in.readString());
		setSummary(in.readString());
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getSourceURL() {
		return sourceURL;
	}
	
	public String getSummary() {
		return summary;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getTitle());
		out.writeString(getDate());
		out.writeString(getAuthor());
		out.writeString(getSourceURL());
		out.writeString(getSummary());
	}
	
	Creator<SuperArticle> CREATOR = new Creator<SuperArticle>() {

		@Override
		public SuperArticle createFromParcel(Parcel in) {
			return new SuperArticle(in);
		}

		@Override
		public SuperArticle[] newArray(int size) {
			return new SuperArticle[size];
		}
		
	};

}
