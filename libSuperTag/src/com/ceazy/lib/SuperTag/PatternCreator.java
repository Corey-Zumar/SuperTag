package com.ceazy.lib.SuperTag;

import java.util.regex.Pattern;

import android.content.Context;

class PatternCreator {
	
	RegexCreator regexCreator;
	
	protected PatternCreator(Context context) {
		regexCreator = new RegexCreator(context);
	}
	
	private RegexCreator getRegexCreator() {
		return regexCreator;
	}
	
	protected Pattern createParserPattern() {
		String parserRegex = "#[^\\.\\?\\!#]*";
		return Pattern.compile(parserRegex);
	}
	
	protected Pattern createMiscPattern() {
		String miscRegex = getRegexCreator().getMiscRegex();
		return Pattern.compile(miscRegex);
	}
	
	protected Pattern createLocationPattern() {
		String locationRegex = getRegexCreator().getLocationRegex();
		return Pattern.compile(locationRegex);
	}
	
	
	

}
