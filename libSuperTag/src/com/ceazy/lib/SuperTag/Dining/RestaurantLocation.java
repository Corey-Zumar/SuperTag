package com.ceazy.lib.SuperTag.Dining;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RestaurantLocation implements Parcelable {
	
	List<String> address;
	String city;
	@SerializedName("postal_code")
	String postalCode;
	@SerializedName("state_code")
	String stateCode;
	
	protected RestaurantLocation() {
		
	}
	
	protected RestaurantLocation(Parcel in) {
		this.address = in.createStringArrayList();
		this.city = in.readString();
		this.postalCode = in.readString();
		this.stateCode = in.readString();
	}
	
	protected String getAddress() {
		StringBuilder builder = new StringBuilder(address.get(0));
		builder.append(" " + city + ", " + stateCode);
		builder.append(" " + postalCode);
		return builder.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeStringList(address);
		out.writeString(city);
		out.writeString(postalCode);
		out.writeString(stateCode);
	}
	
	Creator<RestaurantLocation> CREATOR = new Creator<RestaurantLocation>() {

		@Override
		public RestaurantLocation createFromParcel(Parcel in) {
			return new RestaurantLocation(in);
		}

		@Override
		public RestaurantLocation[] newArray(int size) {
			return new RestaurantLocation[size];
		}
		
	};

}
