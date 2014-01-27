package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ceazy.lib.SuperTag.R;

import android.content.Context;
import android.content.res.Resources;

class DataParser {
	
	Resources resources;
	Context context;
	Map<String, String> tagsAndFunctionsMap;
	
	protected DataParser(Context context) {
		this.context = context;
		createTagsAndFunctionsMap();
	}
	
	private Context getContext() {
		return context;
	}
	
	protected void createTagsAndFunctionsMap() {
		tagsAndFunctionsMap = new LinkedHashMap<String, String>();
		for(String tagAndFunction : getTagsAndFunctionsArray()) {
			tagsAndFunctionsMap.put(getHashTag(tagAndFunction), getFunction(
					tagAndFunction));
		}
	}
	
	protected Map<String, String> getTagsAndFunctionsMap() {
		return tagsAndFunctionsMap;
	}
	
	protected String getFunctionForHashTag(String hashTag) {
		Map<String, String> tagsAndFuncs = getTagsAndFunctionsMap();
		for(String tag : tagsAndFuncs.keySet()) {
			if(tag.equalsIgnoreCase(hashTag)) {
				return tagsAndFuncs.get(tag);
			}
		}
		return "genericSearch";
	}
	
	protected String[] getTagsAndFunctionsArray() {
		return getContext().getResources().getStringArray(R.array.hashTags);
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
		return new ArrayList<String>(getTagsAndFunctionsMap().keySet());
	}
	
	protected List<String> getFunctions() {
		List<String> functions = new ArrayList<String>();
		for(String function : getTagsAndFunctionsMap().values()) {
			if(!functions.contains(function)) {
				functions.add(function);
			}
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
	
	protected String getMusicSubFunction(String subTag) {
		for(String subTagAndFunction : getContext().getResources()
				.getStringArray(R.array.musicMediaSubtags)) {
			int commaIndex = subTagAndFunction.indexOf(",");
			String subTagToCompare = subTagAndFunction.substring(0, commaIndex);
			if(subTagToCompare.equals(subTag)) {
				return subTagAndFunction.substring(commaIndex + 1).trim();
			}
		}
		return "track";
	}
}