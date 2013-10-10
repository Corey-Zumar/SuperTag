package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

class PreferenceManager {
	
	Context context;
	
	protected PreferenceManager(Context context) {
		setContext(context);
	}
	
	private void setContext(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	protected boolean superTagIsInstalled() {
		PackageManager pManager = getContext().getPackageManager();
		for(PackageInfo packageInfo : pManager.getInstalledPackages(0)) {
			if(packageInfo.packageName.equals("com.ceazy.SuperTag")) {
				return true;
			}
		}
		return false;
	}
	
	protected SuperPreference getPackagePreferences(String function) {
		PreferenceManager prefManager = new PreferenceManager(getContext());
		if(prefManager.superTagIsInstalled()) {
			getSuperTagPreferences(function);
		} else {
			return getDefaultPreferences(function);
		}
		return null;
	}
	
	protected SuperPreference getDefaultPreferences(String function) {
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
	
	protected SuperPreference getSuperTagPreferences(String function) {
		//Custom prefs, better ads, etc
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
				preferenceData.add(data);
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
