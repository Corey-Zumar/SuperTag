package com.ceazy.lib.SuperTag;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class SuperIntentCreator {
	
	Context context;
	PreferenceManager preferenceManager;
	
	public SuperIntentCreator(Context context) {
		this.context = context;
		this.preferenceManager = new PreferenceManager(context);
	}
	
	private Context getContext() {
		return context;
	}
	
	private PreferenceManager getPreferenceManager() {
		return preferenceManager;
	}
	
	public SuperTag updateTagWithIntents(SuperTag tag) {
		String function = tag.getFunction();
		Intent rawIntent = createIntentForTagData(tag.getHashTag(), tag.getHashPhrase(), function);
		SuperIntent superIntent = new SuperIntent(rawIntent, getPreferenceManager().getPackagePreferences(function));
		tag.setIntent(superIntent);
		return tag;
	}
	
	public SuperIntent createGenericSuperIntent(String hashTag, String hashPhrase) {
		Intent intent = createGenericSearchIntent(hashTag, hashPhrase);
		SuperPreference preference = getPreferenceManager().getPackagePreferences("genericSearch");
		return new SuperIntent(intent, preference);
	}
	
	public SuperIntent createLocationSuperIntent(String hashPhrase) {
		Intent intent = createMapSearchIntent(hashPhrase);
		SuperPreference preference = getPreferenceManager().getPackagePreferences("location");
		return new SuperIntent(intent, preference);
	}
	
	public SuperIntent createSuperIntentForTagData(String hashTag, String hashPhrase, String function) {
		Intent intent = createIntentForTagData(hashTag, hashPhrase, function);
		SuperPreference preference = getPreferenceManager().getPackagePreferences(function);
		return new SuperIntent(intent, preference);
	}
	
	protected Intent createIntentForTagData(String hashTag, String hashPhrase, String function) {
		if(function.equals("googleSearch")) {
			return createGenericSearchIntent(hashTag, hashPhrase);
		} else if(function.equals("location")) {
			return createMapSearchIntent(hashPhrase);
		} else {
			PreferenceManager preferenceManager = new PreferenceManager(getContext());
			SuperPreference preference = preferenceManager.getPackagePreferences(function);
			return createAppSearchIntent(preference.getPrimaryPkg(), hashPhrase);
		}
	}
	
	protected Intent createGenericSearchIntent(String hashTag, String hashPhrase) {
		Intent action = null;
		if(hashPhrase.contains(" ") || !hashTag.equals("#")) {
			Uri uri = Uri.parse("http://www.google.com/#q="+hashPhrase);
			action = createIntent(Intent.ACTION_VIEW, uri, null, -1, hashPhrase, false);
		} else {
			action = createIntent(Intent.ACTION_SEARCH, null, "com.twitter.android", 
				Intent.FLAG_ACTIVITY_NEW_TASK, hashPhrase, true);
		}
		return action;
	}
	
	protected Intent createMapSearchIntent(String hashPhrase) {

		Uri uri = Uri.parse("geo:0,0?q="+hashPhrase);
		Intent action = createIntent(Intent.ACTION_VIEW, uri, "com.google.android.apps.maps", -1, hashPhrase, false);
		return action;
		
	}
	
	protected Intent createAppSearchIntent(String packageName, String hashPhrase) {
		
		Intent action = createIntent(Intent.ACTION_SEARCH, null, packageName, Intent.FLAG_ACTIVITY_NEW_TASK, hashPhrase, true);
		return action;
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