package com.ceazy.lib.SuperTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

public class SuperSender {
	
	Context context;
	
	public SuperSender(Context context) {
		setContext(context);
	}
	
	private void setContext(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	public Map<String, List<String>> getAllTagsAndFunctions() {
		Map<String, List<String>> tagsAndFunctions = new HashMap<String, List<String>>();
		DataParser dataParser = new DataParser(getContext());
		for(String function : dataParser.getFunctions()) {
			tagsAndFunctions.put(function, dataParser.getAllHashTagsForFunction(function));
		}
		return tagsAndFunctions;
	}
	
	//Provide code snippets for creating a list of functions (map.keySet())
	//and for creating a list of corresponding hashtags when a function is clicked...simple array adapter

}
