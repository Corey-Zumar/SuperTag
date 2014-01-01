package com.ceazy.lib.SuperTag.Location;

import android.os.Parcel;
import android.os.Parcelable;

public class SuperLocation implements Parcelable {
	
	String name, reference, photoReference;
	String[] types;
	long rating = -1;
	int priceLevel = -1;
	
	public SuperLocation(String name) {
		setName(name);
	}
	
	protected SuperLocation(Parcel in) {
		setName(in.readString());
		setRating(in.readLong());
		setPriceLevel(in.readInt());
		setPhotoReference(in.readString());
		setReference(in.readString());
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRating(long rating) {
		this.rating = rating;
	}
	
	public void setPriceLevel(int priceLevel) {
		this.priceLevel = priceLevel;
	}
	
	public void setPhotoReference(String photoReference) {
		this.photoReference = photoReference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getName() {
		return name;
	}
	
	public long getRating() {
		return rating;
	}
	
	public int getPriceLevel() {
		return priceLevel;
	}
	
	public String getPhotoReference() {
		return photoReference;
	}
	
	public String getReference() {
		return reference;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getName());
		out.writeLong(getRating());
		out.writeInt(getPriceLevel());
		out.writeString(getReference());
		out.writeString(getPhotoReference());
	}
	
	Creator<SuperLocation> CREATOR = new Creator<SuperLocation>() {

		@Override
		public SuperLocation createFromParcel(Parcel in) {
			return new SuperLocation(in);
		}

		@Override
		public SuperLocation[] newArray(int size) {
			return new SuperLocation[size];
		}
		
	};

}
