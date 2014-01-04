package com.ceazy.lib.SuperTag.Food;

import android.os.Parcel;
import android.os.Parcelable;

public class SuperRestaurant implements Parcelable {
	
	String name, url, photoURL, phoneNumber, internationalNumber;
	String[] addressInfo, categories;
	long rating, distance;
	boolean isClosed;
	
	public SuperRestaurant(String name) {
		setName(name);
	}
	
	protected SuperRestaurant(Parcel in) {
		setName(in.readString());
		setAddressInfo(in.createStringArray());
		setRating(in.readLong());
		setURL(in.readString());
		setPhotoURL(in.readString());
		setInternationalPhoneNumber(in.readString());
		setPhoneNumber(in.readString());
		setDistance(in.readLong());
		setClosedStatus(in.readByte() != 0);
		setCategories(in.createStringArray());
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddressInfo(String[] addressInfo) {
		this.addressInfo = addressInfo;
	}
	
	public void setRating(long rating) {
		this.rating = rating;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	
	public void setInternationalPhoneNumber(String internationalNumber) {
		this.internationalNumber = internationalNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setDistance(long distance) {
		this.distance = distance;
	}
	
	public void setClosedStatus(boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getAddressInfo() {
		return addressInfo;
	}
	
	public long getRating() {
		return rating;
	}
	
	public String getURL() {
		return url;
	}
	
	public String getPhotoURL() {
		return photoURL;
	}
	
	public String getInternationalPhoneNumber() {
		return internationalNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public long getDistance() {
		return distance;
	}
	
	public boolean getClosedStatus() {
		return isClosed;
	}
	
	public String[] getCategories() {
		return categories;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getName());
		out.writeStringArray(getAddressInfo());
		out.writeLong(getRating());
		out.writeString(getURL());
		out.writeString(getPhotoURL());
		out.writeString(getInternationalPhoneNumber());
		out.writeString(getPhoneNumber());
		out.writeLong(getDistance());
		out.writeByte((byte) (getClosedStatus() ? 1 : 0));
		out.writeStringArray(getCategories());
	}
	
	Creator<SuperRestaurant> CREATOR = new Creator<SuperRestaurant>() {

		@Override
		public SuperRestaurant createFromParcel(Parcel in) {
			return new SuperRestaurant(in);
		}

		@Override
		public SuperRestaurant[] newArray(int size) {
			return new SuperRestaurant[size];
		}
		
	};
	

}
