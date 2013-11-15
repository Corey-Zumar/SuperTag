package com.ceazy.lib.SuperTag;

import android.os.Parcel;
import android.os.Parcelable;

public class SuperTag implements Parcelable {
	
	private String hashTag, hashPhrase, function;
	private int startIndex, endIndex;
	private SuperIntent superIntent;
	
	public SuperTag(String hashTag, String hashPhrase, String function) {
		setHashTag(hashTag);
		setHashPhrase(hashPhrase);
		setFunction(function);
	}
	
	protected SuperTag(Parcel in) {
		setFunction(in.readString());
		setHashTag(in.readString());
		setHashPhrase(in.readString());
		if(in.dataAvail() > 0) {
			setIntent((SuperIntent) in.readParcelable(SuperIntent.class.getClassLoader()));
			if(in.dataAvail() > 0) {
				setStartIndex(in.readInt());
				setEndIndex(in.readInt());
			}
		}
	}
	
	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}
	
	public void setHashPhrase(String hashPhrase) {
		this.hashPhrase = hashPhrase;
	}
	
	public void setFunction(String function) {
		this.function = function;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	public void setIntent(SuperIntent superIntent) {
		this.superIntent = superIntent;
	}
	
	public String getHashTag() {
		return hashTag;
	}
	
	public String getHashPhrase() {
		return hashPhrase;
	}
	
	public String getFunction() {
		return function;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public int getEndIndex() {
		return endIndex;
	}
	
	public SuperIntent getIntent() {
		return superIntent;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeString(getFunction());
		out.writeString(getHashTag());
		out.writeString(getHashPhrase());
		out.writeParcelable(getIntent(), 0);
		out.writeInt(getStartIndex());
		out.writeInt(getEndIndex());
	}
	
	public static final Creator<SuperTag> CREATOR = new Creator<SuperTag>() {

		@Override
		public SuperTag createFromParcel(Parcel in) {
			return new SuperTag(in);
		}

		@Override
		public SuperTag[] newArray(int size) {
			return new SuperTag[size];
		}
		
	};
	
}
