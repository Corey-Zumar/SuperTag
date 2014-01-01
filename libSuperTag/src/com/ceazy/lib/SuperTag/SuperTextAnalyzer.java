package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

public class SuperTextAnalyzer {
	
	Context context;
	List<String> hashTags, functions;
	Pattern pLocation, pMisc, pParser;
	PatternCreator pCreator;
	DataParser dataParser;
	
	public SuperTextAnalyzer(Context context) {
		setContext(context);
		initialize();
	}
	
	private void setContext(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	private void initialize() {
		dataParser = new DataParser(getContext());
		hashTags = dataParser.getHashTags();
		functions = dataParser.getFunctions();
		pCreator = new PatternCreator(getContext());
		pLocation = pCreator.createLocationPattern();
		pMisc = pCreator.createMiscPattern();
		pParser = pCreator.createParserPattern();
	}
	
	public boolean containsHashTag(String msgBody) {
		return msgBody.contains("#");
	}
	
	private List<SuperTag> getTags(String msgBody, boolean single) {
		String originalMsgBody = msgBody;
		List<SuperTag> tagsList = new ArrayList<SuperTag>();
		msgBody = replaceData(new Pattern[]{pLocation, pMisc}, msgBody);
		Matcher parseMatcher = pParser.matcher(msgBody);
		while(parseMatcher.find()) {
			String group = parseMatcher.group(0);
			String hashTag = null;
			String function = null;
			String hashPhrase = null;
			int hashIndex = group.indexOf("#");
			if(group.contains(" ")) {
				hashTag = group.substring(hashIndex, group.indexOf(" ")).trim();
				function = dataParser.getFunctionForHashTag(hashTag, hashTags, functions);
				if(function.equals("googleSearch")) {
					hashPhrase = group.substring(group.indexOf(hashTag) + 1).trim();
				} else {
					hashPhrase = group.substring(group.indexOf(hashTag) + hashTag.length() + 1).trim();
				}
			} else {
				hashTag = "#";
				hashPhrase = group.substring(hashIndex + 1).trim();
				if(hashPhrase.contains(" ")) {
					function = "googleSearch";
				} else {
					hashPhrase = "#" + hashPhrase;
					function = "twitterSearch";
				}
			}
			SuperTag newTag = new SuperTag(hashTag, hashPhrase, function);
			int startIndex = originalMsgBody.indexOf(group);
			newTag.setStartIndex(startIndex);
			newTag.setEndIndex(startIndex + group.length());
			tagsList.add(newTag);
			if(single) {
				break;
			}
		}
		return tagsList;
	}
	
	public List<SuperTag> getAllTags(String msgBody) {
		return getTags(msgBody, false);
	}
	
	public SuperTag getSingleTag(String phrase) {
		return getTags(phrase, true).get(0);
	}
	
	private String replaceData(Pattern[] patterns, String text) {
		for(Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(text);
			while(matcher.find()) {
				String group = matcher.group(0);
				text = text.replace(group, group.replaceAll("\\.", ""));
			}
		}
		return text;
	}
	
	
	
	
	

}
