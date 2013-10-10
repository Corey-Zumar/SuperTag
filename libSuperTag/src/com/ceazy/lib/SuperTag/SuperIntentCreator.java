package com.ceazy.lib.SuperTag;

import java.util.Map;

import android.content.Context;
import android.content.Intent;

public class SuperIntentCreator {

	//NOTE TO SELF: Write a function that checks for installation of the "SuperTag" application for routing...
	
	private String hashPhrase;
	private SuperTag superTag;
	private Context context;
	
	public SuperIntentCreator(SuperTag superTag, Context context) {
		setTag(superTag);
		this.context = context;
	}
	
	public void setTag(SuperTag superTag) {
		this.superTag = superTag;
		this.hashPhrase = superTag.getHashPhrase();
	}
	
	public String getHashPhrase() {
		return hashPhrase;
	}
	
	public SuperTag createGenericSearchIntents() {
		
		IntentCreator intentCreator = new IntentCreator(context);
		Map<String, Intent> intentsMap = intentCreator.createGenericSearchIntents(getHashPhrase());
		SuperPreference preference = getPackagePreferences("googleSearch");
		return updateTag(superTag, intentsMap.get("mainAction"), intentsMap.get("secondaryAction"), preference);
		
	}
	
	public SuperTag createPhoneCallIntents() {
		
		IntentCreator intentCreator = new IntentCreator(context);
		Map<String, Intent> intentsMap = intentCreator.createPhoneCallIntents(getHashPhrase());
		SuperPreference preference = getPackagePreferences("phoneCall");
		return updateTag(superTag, intentsMap.get("mainAction"), intentsMap.get("secondaryAction"), preference);
		
	}
	
	public SuperTag createMapSearchIntents() {
		
		IntentCreator intentCreator = new IntentCreator(context);
		Map<String, Intent> intentsMap = intentCreator.createMapSearchIntents(getHashPhrase());
		SuperPreference preference = getPackagePreferences("location");
		return updateTag(superTag, intentsMap.get("mainAction"), intentsMap.get("secondaryAction"), preference);
		
	}
	
	public SuperTag createAppSearchIntents(SuperPreference preference) {
		
		IntentCreator intentCreator = new IntentCreator(context);
		Map<String, Intent> intentsMap = intentCreator.createAppSearchIntents(preference, getHashPhrase());
		return updateTag(superTag, intentsMap.get("mainAction"), intentsMap.get("secondaryAction"), preference);
		
	}
	
	public SuperTag createIntentsForFunction(String function) {
		
		IntentCreator intentCreator = new IntentCreator(context);
		Map<String, Intent> intentsMap = intentCreator.createIntentsForFunction(function, getHashPhrase());
		SuperPreference preference = getPackagePreferences(function);
		return updateTag(superTag, intentsMap.get("mainAction"), intentsMap.get("secondaryAction"), preference);
		
	}
	
	private SuperTag updateTag(SuperTag tag, Intent mainAction, Intent secondaryAction, SuperPreference preference) {
		SuperIntent newIntent = new SuperIntent(mainAction, preference);
		newIntent.setSecondaryAction(secondaryAction);
		tag.setIntent(newIntent);
		return tag;
	}
	
	public SuperPreference getPackagePreferences(String function) {
		PreferenceManager preferenceManager = new PreferenceManager(context);
		return preferenceManager.getPackagePreferences(function);
	}

}
