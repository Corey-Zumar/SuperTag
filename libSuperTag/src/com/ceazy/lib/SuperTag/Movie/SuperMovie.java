package com.ceazy.lib.SuperTag.Movie;

import android.os.Parcel;
import android.os.Parcelable;

public class SuperMovie implements Parcelable {
	
	String title, posterURL, rtURL, rating, synopsis;
	int year, criticScore, audienceScore, runtime;
	String[] cast;
	
	public SuperMovie(String title, int year) {
		setTitle(title);
		setYear(year);
	}
	
	protected SuperMovie(Parcel in) {
		setTitle(in.readString());
		setYear(in.readInt());
		setRating(in.readString());
		setCriticScore(in.readInt());
		setAudienceScore(in.readInt());
		setRuntime(in.readInt());
		setPosterURL(in.readString());
		setRottenTomatoesURL(in.readString());
		setSynopsis(in.readString());
		setCast(in.createStringArray());
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public void setCriticScore(int criticScore) {
		this.criticScore = criticScore;
	}
	
	public void setAudienceScore(int audienceScore) {
		this.audienceScore = audienceScore;
	}
	
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	
	public void setPosterURL(String posterURL) {
		this.posterURL = posterURL;
	}
	
	public void setRottenTomatoesURL(String rtURL) {
		this.rtURL = rtURL;
	}
	
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	public void setCast(String[] cast) {
		this.cast = cast;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getRating() {
		return rating;
	}
	
	public int getCriticScore() {
		return criticScore;
	}
	
	public int getAudienceScore() {
		return audienceScore;
	}
	
	public int getRuntime() {
		return runtime;
	}
	
	public String getPosterURL() {
		return posterURL;
	}
	
	public String getRottenTomatoesURL() {
		return rtURL;
	}
	
	public String getSynopsis() {
		return synopsis;
	}
	
	public String[] getCast() {
		return cast;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getTitle());
		out.writeInt(getYear());
		out.writeString(getRating());
		out.writeInt(getCriticScore());
		out.writeInt(getAudienceScore());
		out.writeInt(getRuntime());
		out.writeString(getPosterURL());
		out.writeString(getRottenTomatoesURL());
		out.writeString(getSynopsis());
		out.writeStringArray(getCast());
	}
	
	Creator<SuperMovie> CREATOR = new Creator<SuperMovie>() {

		@Override
		public SuperMovie createFromParcel(Parcel in) {
			return new SuperMovie(in);
		}

		@Override
		public SuperMovie[] newArray(int size) {
			return new SuperMovie[size];
		}
		
	};

}
