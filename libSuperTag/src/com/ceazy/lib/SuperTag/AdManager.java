package com.ceazy.lib.SuperTag;

import android.app.AlertDialog;
import android.content.Context;

class AdManager {
	
	Context context;
	
	protected AdManager(Context context) {
		setContext(context);
	}
	
	private void setContext(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	protected void showAds() {
		PreferenceManager preferenceManager = new PreferenceManager(getContext());
		if(preferenceManager.superTagIsInstalled()) {
			
		} else {
			showSuperTagAd();
		}
	}
	
	private void showSuperTagAd() {
		AlertDialog d = new AlertDialog.Builder(getContext())
		.setTitle("Download SuperTag!")
		.setMessage("Please download the SuperTag application")
		.create();
		d.show();
	}

}
