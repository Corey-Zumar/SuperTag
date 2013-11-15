package com.ceazy.lib.SuperTag;

import android.os.Parcel;
import android.os.Parcelable;

public class SuperPreference implements Parcelable {
	
	private String primaryPkg, primaryName, secondaryPkg, secondaryName, function;
	
	public SuperPreference(String function, String primaryPkg, String primaryName) {
		setPrimaryPkg(primaryPkg);
		setPrimaryName(primaryName);
		setFunction(function);
	}
	
	protected SuperPreference(Parcel in) {
		setFunction(in.readString());
		setPrimaryPkg(in.readString());
		setPrimaryName(in.readString());
		String secondaryPkg = in.readString();
		if(!secondaryPkg.equals("none")) {
			setSecondaryPkg(secondaryPkg);
			setSecondaryName(in.readString());
		}
	}
	
	public void setFunction(String function) {
		this.function = function;
	}
	
	public void setPrimaryPkg(String primaryPkg) {
		this.primaryPkg = primaryPkg;
	}
	
	public void setSecondaryPkg(String secondaryPkg) {
		this.secondaryPkg = secondaryPkg;
	}
	
	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}
	
	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}
	
	public String getFunction() {
		return function;
	}
	
	public String getPrimaryPkg() {
		return primaryPkg;
	}
	
	public String getSecondaryPkg() {
		if(secondaryPkg == null) {
			return "none";
		} else {
			return secondaryPkg;
		}
	}
	
	public String getPrimaryName() {
		return primaryName;
	}
	
	public String getSecondaryName() {
		return secondaryName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeString(getFunction());
		out.writeString(getPrimaryPkg());
		out.writeString(getPrimaryName());
		String secondaryPkg = getSecondaryPkg();
		out.writeString(secondaryPkg);
		if(!secondaryPkg.equals("none")) {
			out.writeString(getSecondaryName());
		}
		
	}
	
	public static Creator<SuperPreference> CREATOR = new Creator<SuperPreference>() {

		@Override
		public SuperPreference createFromParcel(Parcel in) {
			return new SuperPreference(in);
		}

		@Override
		public SuperPreference[] newArray(int size) {
			return new SuperPreference[size];
		}
		
	};

}
