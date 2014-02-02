package com.ceazy.lib.SuperTag;

import com.ceazy.lib.SuperTag.R;

import android.content.Context;

class RegexCreator {
	
	Context context;
	
	protected RegexCreator(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
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
		String[] miscAbbrevs = getContext().getResources().getStringArray(R.array.miscAbbrevs);
		return createRegexFromArrays(new String[][]{miscAbbrevs});
	}
	
	protected String getLocationRegex() {
		String[] directionAbbrevs = getContext().getResources().getStringArray(R.array.directionAbbrevs);
		String[] locationAbbrevs = getContext().getResources().getStringArray(R.array.locationAbbrevs);
		return createRegexFromArrays(new String[][]{directionAbbrevs, locationAbbrevs});
	}

}
