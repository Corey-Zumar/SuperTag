package com.ceazy.lib.SuperTag.Location;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LocationPhoto implements Parcelable {
	
	@SerializedName("photo_reference")
	String photoReference;
	
	protected LocationPhoto() {
		
	}
	
	protected LocationPhoto(Parcel in) {
		this.photoReference = in.readString();
	}
	
	protected String getPhotoReference() {
		return photoReference;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(photoReference);
	}
	
	Creator<LocationPhoto> CREATOR = new Creator<LocationPhoto>() {

		@Override
		public LocationPhoto createFromParcel(Parcel in) {
			return new LocationPhoto(in);
		}

		@Override
		public LocationPhoto[] newArray(int size) {
			return new LocationPhoto[size];
		}
		
	};

}
