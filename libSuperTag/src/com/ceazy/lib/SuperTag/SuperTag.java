package com.ceazy.lib.SuperTag;

public class SuperTag {
	
	private String hashTag, hashPhrase, function;
	private int startIndex, endIndex;
	private SuperIntent superIntent;
	
	public SuperTag(String hashTag, String hashPhrase, String function) {
		setHashTag(hashTag);
		setHashPhrase(hashPhrase);
		setFunction(function);
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
	
}
