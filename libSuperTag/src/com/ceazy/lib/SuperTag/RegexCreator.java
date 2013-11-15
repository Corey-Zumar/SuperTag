package com.ceazy.lib.SuperTag;

import android.content.Context;
import android.content.res.Resources;

class RegexCreator {
	
	Context context;
	Resources resources;
	
	protected RegexCreator(Context context) {
		setResources(context);
	}
	
	private void setResources(Context context) {
		this.resources = context.getResources();
	}
	
	private Resources getResources() {
		return resources;
	}
	
	private String createRegexFromArrays(String[][] params) {
		StringBuilder builder = new StringBuilder();
		builder.append("(?i)\\s(");
		for(String[] array : params) {
			for(String abbrev : array) {
				builder.append("("+abbrev+")|");
			}
		}
		builder.deleteCharAt(builder.lastIndexOf("|"));
		builder.append(")*\\.");
		return builder.toString();
	}
	
	protected String getMiscRegex() {
		String[] miscAbbrevs = getResources().getStringArray(R.array.miscAbbrevs);
		return createRegexFromArrays(new String[][]{miscAbbrevs});
	}
	
	protected String getLocationRegex() {
		String[] directionAbbrevs = getResources().getStringArray(R.array.directionAbbrevs);
		String[] locationAbbrevs = getResources().getStringArray(R.array.locationAbbrevs);
		return createRegexFromArrays(new String[][]{directionAbbrevs, locationAbbrevs});
	}

}
