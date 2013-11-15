package com.ceazy.lib.SuperTag;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class SuperLinkifyActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//setTheme();
		String data = getIntent().getData().toString();
		String phrase = data.substring(data.indexOf("#"));
		parsePhraseAndLaunch(phrase);

	}

	protected void setTheme() {
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			setTheme(android.R.style.Theme_Holo_Light_Dialog);
		}
	}

	protected void parsePhraseAndLaunch(String phrase) {
		DataParser dataParser = new DataParser(this);
		String hashTag = null;
		String hashPhrase = null;
		String function = null;
		if (!phrase.contains(" ")) { // ADD AN OR
			hashTag = "#";
			hashPhrase = phrase.substring(1);
			function = "googleSearch";
		} else {
			hashTag = phrase.substring(0, phrase.indexOf(" "));
			function = dataParser.getFunctionForHashTag(hashTag,
					dataParser.getHashTags(), dataParser.getFunctions());
			if (function.equals("googleSearch")) {
				hashPhrase = phrase.substring(1);
			} else {
				hashPhrase = phrase.substring(phrase.indexOf(hashTag)
						+ hashTag.length() + 1);
			}
			Log.d(hashTag, function);
		}
		SuperTag launchTag = new SuperTag(hashTag, hashPhrase, function);
		SuperIntentCreator creator = new SuperIntentCreator(this);
		launchTag = creator.updateTagWithIntents(launchTag);
		SuperIntent launchIntent = launchTag.getIntent();
		PreferenceManager pManager = new PreferenceManager(this);
		if (pManager.superTagIsInstalled()) {
			Intent iLaunchST = new Intent("SUPER_TAG_LAUNCHED");
			iLaunchST.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			iLaunchST.putExtras(launchIntent.compileSuperIntentData(this));
			PendingIntent superTagPI = PendingIntent.getBroadcast(this,
					(int) System.currentTimeMillis(), iLaunchST, 0);
			try {
				superTagPI.send();
				finish();
			} catch (CanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				startActivity(launchIntent.getMainAction());
				finish();
			} catch (ActivityNotFoundException e) {
				NotInstalledDialog nID = NotInstalledDialog.newInstance(
						launchIntent.getPreference().getPrimaryName(),
						launchIntent.getPreference().getPrimaryPkg());
				nID.show(getSupportFragmentManager(), "nID");
			}
		}
	}
}
