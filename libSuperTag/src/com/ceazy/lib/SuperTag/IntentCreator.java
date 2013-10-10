package com.ceazy.lib.SuperTag;

import java.util.HashMap;
import java.util.Map;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

class IntentCreator {
	
	Context context;
	
	protected IntentCreator(Context context) {
		setContext(context);
	}
	
	private void setContext(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	protected Map<String, Intent> createGenericSearchIntents(String hashPhrase) {
		Uri uri = Uri.parse("http://www.google.com/#q="+hashPhrase);
		Intent mainAction = createIntent(Intent.ACTION_VIEW, uri, null, -1, hashPhrase, false);
		Intent secondaryAction = createIntent(Intent.ACTION_SEARCH, null, "com.twitter.android", 
				Intent.FLAG_ACTIVITY_NEW_TASK, hashPhrase, true);		
		return getIntentsMap(mainAction, secondaryAction);
	}
	
	protected Map<String, Intent> createPhoneCallIntents(String hashPhrase) {
		Intent mainAction = null;
		String uriString = hashPhrase.replaceAll("[^0-9|\\+]", "").trim();
		if(uriString.length() < 3) {
			mainAction = createIntent(Intent.ACTION_SEARCH, null, "com.android.contacts", -1, hashPhrase, true);
		} else {
			Uri uri = Uri.parse("tel:" + uriString);
			mainAction = createIntent(Intent.ACTION_CALL, uri, null, -1, hashPhrase, false);
		}
		return getIntentsMap(mainAction, null);
	}
	
	protected Map<String, Intent> createMapSearchIntents(String hashPhrase) {

		Uri uri = Uri.parse("geo:0,0?q="+hashPhrase);
		Intent mainAction = createIntent(Intent.ACTION_VIEW, uri, "com.google.android.apps.maps", -1, hashPhrase, false);
		Uri secondaryUri = Uri.parse("google.navigation:q="+hashPhrase);
		Intent secondaryAction = createIntent(Intent.ACTION_VIEW, secondaryUri, null, -1, hashPhrase, false);
		return getIntentsMap(mainAction, secondaryAction);
		
	}
	
	protected Map<String, Intent> createAppSearchIntents(SuperPreference preference, String hashPhrase) {
		
		String primaryPkg = preference.getPrimaryPkg();
		String secondaryPkg = preference.getSecondaryPkg();
		
		Intent mainAction = createIntent(Intent.ACTION_SEARCH, null, primaryPkg, Intent.FLAG_ACTIVITY_NEW_TASK, hashPhrase, true);
		Intent secondaryAction = createIntent(Intent.ACTION_SEARCH, null, secondaryPkg, Intent.FLAG_ACTIVITY_NEW_TASK, hashPhrase, 
				true);
		return getIntentsMap(mainAction, secondaryAction);
		
	}
	
	protected Map<String, Intent> createIntentsForFunction(String function, String hashPhrase) {
		if(function.equals("googleSearch")) {
			return createGenericSearchIntents(hashPhrase);
		} else if(function.equals("location")) {
			return createMapSearchIntents(hashPhrase);
		} else if(function.equals("phoneCall")) {
			return createPhoneCallIntents(hashPhrase);
		} else if(function.equals("event")) {
			//Panic
		} else {
			PreferenceManager preferenceManager = new PreferenceManager(getContext());
			SuperPreference pref = preferenceManager.getPackagePreferences(function);
			return createAppSearchIntents(pref, hashPhrase);
		}
		return null;
	}
	
	private Intent createIntent(String action, Uri uri, String pkgName, int flags, String hashPhrase, boolean putSearchQuery) {
		Intent intent;
		if(uri != null) {
			intent = new Intent(Intent.ACTION_VIEW, uri);
		} else {
			intent = new Intent(action);
		}
		if(pkgName != null) {
			intent.setPackage(pkgName);
			intent = setClasses(intent, pkgName);
		}
		if(flags != -1) {
			intent.setFlags(flags);
		}
		if(putSearchQuery) {
			intent.putExtra(SearchManager.QUERY, hashPhrase);
		}
		Bundle data = new Bundle();
		data.putString("hashPhrase", hashPhrase);
		intent.putExtras(data);
		
		return intent;
	}
	
	private Map<String, Intent> getIntentsMap(Intent mainAction, Intent secondaryAction) {
		Map<String, Intent> intentsMap = new HashMap<String, Intent>();
		intentsMap.put("mainAction", mainAction);
		intentsMap.put("secondaryAction", secondaryAction);
		return intentsMap;
	}
	
	protected Intent setClasses(Intent intent, String pkgName) {
		String[] classes = getContext().getResources().getStringArray(R.array.classes);
		for(String classInfo : classes) {
			int commaIndex = classInfo.indexOf(",");
			if(classInfo.substring(0, commaIndex).equals(pkgName)) {
				intent = intent.setClassName(pkgName, classInfo.substring(commaIndex + 1, classInfo.length()).trim());
				break;
			}
		}
		return intent;
	}
	
}
