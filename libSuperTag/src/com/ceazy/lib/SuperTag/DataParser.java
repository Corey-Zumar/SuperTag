package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;

public class DataParser {
	
	Resources resources;
	
	protected DataParser(Context context) {
		setResources(context.getResources());
	}
	
	private void setResources(Resources resources) {
		this.resources = resources;
	}
	
	private Resources getResources() {
		return resources;
	}
	
	protected String getFunctionForHashTag(String hashTag, List<String> hashTags, List<String> functions) {
		for(String tag : hashTags) {
			if(tag.equals(hashTag)) {
				return functions.get(hashTags.indexOf(tag)).trim();
			}
		}
		return "googleSearch";
	}
	
	protected String[] getTagsAndFunctionsArray() {
		return getResources().getStringArray(R.array.hashTags);
	}
	
	protected String getHashTag(String hashTagAndFunction) {
		int commaIndex = hashTagAndFunction.indexOf(",");
		return hashTagAndFunction.substring(0, commaIndex).trim();
	}
	
	protected String getFunction(String hashTagAndFunction) {
		int commaIndex = hashTagAndFunction.indexOf(",");
		return hashTagAndFunction.substring(commaIndex + 1, hashTagAndFunction.length()).trim();
	}
	
	protected List<String> getHashTags() {
		List<String> hashTags = new ArrayList<String>();
		for(String hashTagAndFunction : getTagsAndFunctionsArray()) {
			hashTags.add(getHashTag(hashTagAndFunction));
		}
		return hashTags;
	}
	
	protected List<String> getFunctions() {
		List<String> functions = new ArrayList<String>();
		for(String hashTagAndFunction : getTagsAndFunctionsArray()) {
			functions.add(getFunction(hashTagAndFunction));
		}
		return functions;
	}
	
	protected List<String> getAllHashTagsForFunction(String function) {
		List<String> hashTags = new ArrayList<String>();
		for(String hashTagAndFunction : getTagsAndFunctionsArray()) {
			if(getFunction(hashTagAndFunction).equals(function)) {
				hashTags.add(getHashTag(hashTagAndFunction));
			}
		}
		return hashTags;
	}
}