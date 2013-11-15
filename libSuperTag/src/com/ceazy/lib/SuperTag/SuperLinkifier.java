package com.ceazy.lib.SuperTag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.util.Linkify;
import android.widget.TextView;

public class SuperLinkifier {
	
	private Pattern pLocation, pMisc, pParser;
	
	public SuperLinkifier(Context context) {
		PatternCreator pCreator = new PatternCreator(context);
		pLocation = pCreator.createLocationPattern();
		pMisc = pCreator.createMiscPattern();
		pParser = pCreator.createParserPattern();
	}
	
	public void Linkify(TextView textView) {
		String text = textView.getText().toString();
		String updatedText = replaceData(text);
		textView.setText(updatedText);
		String URL = "supertag://";
		Linkify.addLinks(textView, getPattern("parser"), URL);
	}
	
	private String replaceData(String text) {
		if(text.contains("#")) {
			text = replaceLocationData(text);
			text = replaceMiscData(text);
		}
		return text;
	}
	
	private String replaceMiscData(String text) {
		Matcher miscMatcher = getPattern("misc").matcher(text);
		while(miscMatcher.find()) {
			String group = miscMatcher.group(0);
			text = text.replace(group, group.replaceAll("\\.", ""));
		}
		return text;
	}
	
	private String replaceLocationData(String text) {
		Matcher locationMatcher = getPattern("location").matcher(text);
		while(locationMatcher.find()) {
			String group = locationMatcher.group(0);
			text = text.replace(group, group.replaceAll("\\.", ""));
		}
		return text;
	}
	
	private Pattern getPattern(String key) {
		if(key.equals("location")) {
			return pLocation;
		} else if(key.equals("misc")) {
			return pMisc;
		} else if(key.equals("parser")) {
			return pParser;
		}
		return null;
	}

}
