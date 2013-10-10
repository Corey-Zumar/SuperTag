package com.ceazy.lib.SuperTag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ceazy.lib.SuperTag.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class SuperTagAnalyzer {
	
	private Context context;
	List<String> ignoredPhrases = new ArrayList<String>();
	List<String> hashTags = new ArrayList<String>();
	List<String> functions = new ArrayList<String>();
	Resources resources;
	
	public SuperTagAnalyzer(Context context) {
		setContext(context);
		resources = getContext().getResources();
		createTagAndFunctionLists();
	}
	
	private void setContext(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	private void createTagAndFunctionLists() {
		//Created supported hashtags and functions lists
		DataParser dataParser = new DataParser(getContext());
		hashTags = dataParser.getHashTags();
		functions = dataParser.getFunctions();
		//Create ignored tags list
		String[] ignoredTagsArray = getContext().getResources().getStringArray(R.array.ignored_tags_array);
		for(String ignoredTag : ignoredTagsArray) {
			ignoredPhrases.add(ignoredTag);
		}
	}
	
	public String getFunction(String hashTag) {
		for(String tag : hashTags) {
			if(tag.equals(hashTag)) {
				return functions.get(hashTags.indexOf(tag)).trim();
			}
		}
		return null;
	}
	
	public boolean containsHashTag(String msgBody) {
		if(msgBody.contains("#")) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<SuperTag> getAllTags(String msgBody) {
		String originalMsgBody = msgBody;
		List<SuperTag> tagsList = new ArrayList<SuperTag>();
		String hashPhrase = null;
		String[] bodyArray = msgBody.split("\\s+");
		for(int i = 0; i < bodyArray.length; i++) {
			String term = bodyArray[i].toLowerCase().replaceFirst("^[^#]+", "");
			boolean containsTerm = hashTags.contains(term);
			Log.d("TERM", term);
			if(containsTerm || term.startsWith("#") && !containsTerm && term.length() > 1) {
				String function = null;
				int hashTagEndPosition = 0;
				int startIndex = msgBody.toLowerCase().indexOf(term, 0);
				if(containsTerm) {
					function = getFunction(term);
					hashTagEndPosition  = startIndex + term.length();
				} else {
					function = "googleSearch";
					hashTagEndPosition = startIndex + 1;
				}
				msgBody = msgBody.substring(hashTagEndPosition, msgBody.length());
				hashPhrase = msgBody;
				int hashPhraseEndPosition = getClosestPhraseEnder(msgBody, function);
				if(hashTagEndPosition != 0) {
					hashPhrase = hashPhrase.substring(0, hashPhraseEndPosition);
					msgBody = msgBody.substring(hashPhraseEndPosition, msgBody.length());
				}
				hashPhrase = hashPhrase.trim();
				SuperTag newTag = new SuperTag(term, hashPhrase, function);
				newTag.setStartIndex(originalMsgBody.toLowerCase().indexOf(term));
				newTag.setEndIndex(originalMsgBody.toLowerCase().indexOf(hashPhrase) + hashPhrase.length());
				tagsList.add(newTag);
			}
		}
		return tagsList;
	}
	
	private Integer getClosestPhraseEnder(String body, String function) {
		String originalBody = body; //Preserve the original for position of hashtag
		int removedIndexLength = 0;
		body = body.toLowerCase(); //greater ease in locating abbreviations
		//Replace abbreviations to avoid premature stops concerning periods
		if(function.equals("location")) { //Credits - US Postal Service
		String[] abbrevs = getContext().getResources().getStringArray(R.array.directionAbbrevs);
			for(String abbrev : abbrevs) {
				if(body.contains(abbrev)) {
					body = body.replace(abbrev, "");
					removedIndexLength += abbrev.length();
				}
		}
		} else if(function.equals("eventComponent")) {
			String[] abbrevs = new String[]{"a.m.", "p.m."};
			for(String abbrev : abbrevs) {
				if(body.contains(abbrev)) {
					body = body.replace(abbrev.substring(0, abbrev.length() - 1), "");
					removedIndexLength += abbrev.length() - 1;
				}
			}
		}
		String[] titleAbbrevs = getContext().getResources().getStringArray(R.array.titleAbbrevs);
		for(String abbrev : titleAbbrevs) {
			if(body.contains(abbrev)) {
				body = body.replace(abbrev, "");
				removedIndexLength += abbrev.length();
			}
		}
		//Filter out decimals in numbers
		String filterBody = body.replaceAll("[^0-9.]", " ");
		String[] filterArray = filterBody.split("\\s+");
		for(String filterString : filterArray) {
			filterString = filterString.trim();
			if(!filterString.equals(".") && filterString.contains(".")) {
			body = body.replace(filterString.toString(), "");
			removedIndexLength += filterString.length();
			}
		}
		
		//Proceed to determine the closest hash phrase "ender"
		List<Integer> indexList = new ArrayList<Integer>();
		if(body.contains("#")) {
			indexList.add(body.indexOf("#"));
		}
		if(body.contains(".")) {
			indexList.add(body.indexOf("."));
		}
		if(body.contains("?")) {
			indexList.add(body.indexOf("?"));
		}
		if(body.contains("!")) {
			indexList.add(body.indexOf("!"));
		}
		String[] ignoredSmilies = getContext().getResources().getStringArray(R.array.ignoredSmilies);
		for(String ignoredSmily : ignoredSmilies) {
			if(body.contains(ignoredSmily)) {
				indexList.add(body.indexOf(ignoredSmily));
				break;
			}
		}
		
		Collections.sort(indexList);
		if(indexList.size() > 0) {
			//Perform length check to avoid skipping over hashtags
			if(!body.contains("#") || indexList.get(0) + removedIndexLength <= body.indexOf("#")) {
				return indexList.get(0) + removedIndexLength;
			} else {
				return originalBody.indexOf("#");
			}
		}
		return body.length();
	}
}
