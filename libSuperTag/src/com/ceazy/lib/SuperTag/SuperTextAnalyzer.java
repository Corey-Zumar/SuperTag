package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.content.Context;

/**<b>class SuperTextAnalyzer</b>
 * Analyzes and parses strings of text for SuperTags that they may contain
 */
public class SuperTextAnalyzer {
	
	Context context;
	List<String> hashTags, functions;
	Pattern pLocation, pMisc, pParser;
	PatternCreator pCreator;
	DataParser dataParser;
	
	/**<b>SuperTextAnalyzer class constructor</b>
	 */
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
		pCreator = new PatternCreator(getContext());
		pLocation = pCreator.createLocationPattern();
		pMisc = pCreator.createMiscPattern();
		pParser = pCreator.createParserPattern();
	}
	
	/**<b><i>public boolean containsHashTag(<code>String</code> text)</i></b>
	 * <p>Determines whether or not a string of text contains a hashtag. If a 
	 * string contains a hashtag, it contains at least one {@link SuperTag}
	 * @param text A string of text
	 */
	public boolean containsHashTag(String text) {
		return text.contains("#");
	}
	
	private List<SuperTag> getTags(String text, boolean single) {
		String originalMsgBody = text;
		List<SuperTag> tagsList = new ArrayList<SuperTag>();
		text = replaceData(new Pattern[]{pLocation, pMisc}, text);
		Matcher parseMatcher = pParser.matcher(text);
		while(parseMatcher.find()) {
			String group = parseMatcher.group(0);
			String hashTag = null;
			String function = null;
			String hashPhrase = null;
			int hashIndex = group.indexOf("#");
			if(group.contains(" ")) {
				hashTag = group.substring(hashIndex, group.indexOf(" ")).trim();
				function = dataParser.getFunctionForHashTag(hashTag);
				if(function.equals("genericSearch") && 
						!dataParser.getHashTags().contains(hashTag)) {
					hashPhrase = group.substring(group.indexOf(hashTag) + 1).trim();
				} else {
					hashPhrase = group.substring(group.indexOf(hashTag) + hashTag.length() + 1).trim();
				}
			} else {
				hashTag = "#";
				hashPhrase = group.substring(hashIndex + 1).trim();
				function = "genericSearch";
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
	
	/**<b><i>public <code>List</code><{@link SuperTag}> getAllTags(<code>String</code>
	 * text)</i></b>
	 * <p>Finds and compiles all {@link SuperTag SuperTags} in a string of text
	 * @param text A string of text
	 * @return A list of {@link SuperTag} objects
	 */
	public List<SuperTag> getAllTags(String text) {
		return getTags(text, false);
	}
	
	/**<b><i>public <code>List</code><{@link SuperTag}> getAllTags(<code>String</code>
	 * text)</i></b>
	 * <p>Finds the first {@link SuperTag} in a string of text. To be used when
	 * it can be assumed that a string only contains one SuperTag
	 * @param text A string of text
	 * @return A {@link SuperTag} object
	 */
	public SuperTag getFirstTag(String text) {
		try {
			return getTags(text, true).get(0);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
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
