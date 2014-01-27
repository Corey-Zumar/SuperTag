package com.ceazy.lib.SuperTag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.content.Context;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.TextView;

/**<b>class SuperLinkifier</b>
 * <p>Uses Android's {@link android.text.util.Linkify linkify} utility to identify 
 * and create hyperlinks for all {@link SuperTag SuperTags} in a string</p>
 * <p>The SuperLinkifier is an alternative to manually parsing strings using
 * the {@link SuperTextAnalyzer}.</p>
 * 
 *
 */
public class SuperLinkifier {
	
	private Pattern pLocation, pMisc, pParser;
	
	/**<b>SuperLinkifier class constructor</b>
	 * 
	 * @param context
	 */
	public SuperLinkifier(Context context) {
		PatternCreator pCreator = new PatternCreator(context);
		pLocation = pCreator.createLocationPattern();
		pMisc = pCreator.createMiscPattern();
		pParser = pCreator.createParserPattern();
	}
	
	/**<b><i>public void Linkify({@link android.widget.TextView TextView} textView,
	 * <code>int[]</code> linkifyParams, <code>String</code> scheme)</i></b>
	 * <p>Creates hyperlinks for all instances of SuperTags within strings 
	 * displayed through TextViews.
	 * @param textView The TextView to which the Linkify utility should be applied
	 * @param linkifyParams Other Linkify bit fields and masks for which hyperlinks
	 * should be created (Linkify.EMAIL_ADDRESSES, Linkify.ALL, etc), may be null
	 * @param scheme The URL scheme for all SuperTag hyperlinks that are created
	 * (defaults to "supertag://" if null)
	 */
	public void Linkify(TextView textView, int[] linkifyParams, String scheme) {
		String text = textView.getText().toString();
		String updatedText = replaceData(text);
		textView.setText(updatedText);
		if(scheme == null) {
			scheme = "supertag://";
		}
		if(linkifyParams != null ) {
			for(int parameter : linkifyParams) {
				try {
					Linkify.addLinks(textView, parameter);
				} catch (Exception e) {
					Log.d("Invalid parameter: " ,String.valueOf(parameter));
				}
			}
		}
		Linkify.addLinks(textView, getPattern("parser"), scheme);
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
