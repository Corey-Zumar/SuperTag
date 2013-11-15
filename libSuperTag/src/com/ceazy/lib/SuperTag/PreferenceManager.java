package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

class PreferenceManager {
	
	Context context;
	PackageManager pManager;
	
	protected PreferenceManager(Context context) {
		setContext(context);
		pManager = getContext().getPackageManager();
	}
	
	private void setContext(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	protected boolean superTagIsInstalled() {
		for(PackageInfo packageInfo : pManager.getInstalledPackages(0)) {
			if(packageInfo.packageName.equals("com.ceazy.SuperTag")) {
				return true;
			}
		}
		return false;
	}
	
	protected SuperPreference getPackagePreferences(String function) {
		String[] defaultPrefs = getContext().getResources().getStringArray(R.array.default_preferences);
		for(String prefString : defaultPrefs) {
			int firstCommaIndex = prefString.indexOf(",");
			if(prefString.substring(0, firstCommaIndex).equals(function)) {
				prefString = prefString.substring(firstCommaIndex + 1, prefString.length());
				return parsePreferenceData(function, prefString);
			}
		}
		return null;
	}
	
	protected SuperPreference parsePreferenceData(String function, String data) {
		List<String> preferenceData = new ArrayList<String>();
		while(data.length() > 0) {
			if(data.contains(",")) {
				int commaIndex = data.indexOf(",");
				preferenceData.add(data.substring(0, commaIndex).trim());
				data = data.substring(commaIndex + 1, data.length());
			} else {
				preferenceData.add(data.trim());
				data = "";
			}
		}
		SuperPreference superPreference = new SuperPreference(function, preferenceData.get(0), preferenceData.get(1));
		if(preferenceData.size() > 2) {
			superPreference.setSecondaryPkg(preferenceData.get(2));
			superPreference.setSecondaryName(preferenceData.get(3));
		}
		return superPreference;
	}

}
