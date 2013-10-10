package com.ceazy.lib.SuperTag;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class SuperAutoAnalyzer {
	
	private String msgBody;
	private Context context;
	
	public SuperAutoAnalyzer(String msgBody, Context context) {
		setMsgBody(msgBody);
		setContext(context);
	}
	
	private void setContext(Context context) {
		this.context = context;
	}
	
	private void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	
	private Context getContext() {
		return context;
	}
	
	private String getMsgBody() {
		return msgBody;
	}
	
	public List<SuperTag> getAllSuperTags() {
		List<SuperTag> finalTags = new ArrayList<SuperTag>();
		SuperTagAnalyzer tagAnalyzer = new SuperTagAnalyzer(getContext());
		if(tagAnalyzer.containsHashTag(getMsgBody())) {
			List<SuperTag> tagsList = tagAnalyzer.getAllTags(getMsgBody());
			SuperIntentCreator intentCreator = new SuperIntentCreator(tagsList.get(0), getContext());
			for(int i = 0; i < tagsList.size(); i++) {
				SuperTag superTag = tagsList.get(i);
				String function = superTag.getFunction();
				if(i > 0) {
					intentCreator.setTag(superTag);
				}
				superTag = intentCreator.createIntentsForFunction(function);
				finalTags.add(superTag);
			}
		}
		return finalTags;
	}
	
	

}
