package com.ceazy.SuperTag.Movie;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
	
	private String title, year, mpaaRating;
	int criticRating;
	String poster;
	
	public Movie(Parcel in) {
		this.title = in.readString();
		this.year = in.readString();
		this.mpaaRating = in.readString();
		this.criticRating = in.readInt();
		if(in.dataSize() > 0) {
			this.poster = in.readString();
		}
	}
	
	public Movie(String title, String year, String mpaaRating, int criticRating) {
		this.title = title;
		this.year = year;
		this.mpaaRating = mpaaRating;
		this.criticRating = criticRating;
	}
	
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getYear() {
		return year;
	}
	
	public String getMPAARating() {
		return mpaaRating;
	}
	
	public int getCriticRating() {
		return criticRating;
	}
	
	public String getPoster() {
		return poster;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeString(getTitle());
		out.writeString(getYear());
		out.writeString(getMPAARating());
		out.writeInt(getCriticRating());
		if(poster != null) {
			out.writeString(getPoster());
		}
	}
	
	public static Creator<Movie> CREATOR = new Creator<Movie>() {

		@Override
		public Movie createFromParcel(Parcel in) {
			return new Movie(in);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
		
	};
}
