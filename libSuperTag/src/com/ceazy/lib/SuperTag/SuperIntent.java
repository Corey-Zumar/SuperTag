package com.ceazy.lib.SuperTag;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;


public class SuperIntent {
	
	private Intent mainAction, secondaryAction;
	private SuperPreference preference;
	
	public SuperIntent(Intent mainAction, SuperPreference preference) {
		setMainAction(mainAction);
		setPreference(preference);
	}
	
	public void setMainAction(Intent mainAction) {
		this.mainAction = mainAction;
	}
	
	public void setSecondaryAction(Intent secondaryAction) {
		this.secondaryAction = secondaryAction;
	}
	
	public void setPreference(SuperPreference preference) {
		this.preference = preference;
	}
	
	private SuperPreference getPreference() {
		return preference;
	}
	
	private Intent getMainAction() {
		return mainAction;
	}
	
	private Intent getSecondaryAction() {
		return secondaryAction;
	}
	
	private String getHashPhrase() {
		return getMainAction().getExtras().getString("hashPhrase");
	}
	
	private void getCurrentPrefs() {
		
	}
	
	private boolean preferencesAreEqual(SuperPreference firstPref, SuperPreference secondPref) {
		return firstPref.getPrimaryPkg().equals(secondPref.getPrimaryPkg()) && firstPref.getSecondaryPkg()
				.equals(secondPref.getSecondaryPkg());
	}
	
	private boolean noSecondaryPreference(SuperPreference preference) {
		return preference.getSecondaryPkg().equals("none");
	}
	
	private ChooserDialog createChooserDialog(SuperPreference preference, Context context) {
		ListView chooserView = new ListView(context);
	}
	
	private void updateIntent(SuperPreference preference, Context context) {
		IntentCreator intentCreator = new IntentCreator(context);
		Map<String, Intent> intentsMap = intentCreator.createIntentsForFunction(preference.getFunction(), getHashPhrase());
		Intent mainAction = intentsMap.get("mainAction");
		Intent secondaryAction = intentsMap.get("secondaryAction");
		setMainAction(mainAction);
		setSecondaryAction(secondaryAction);
		setPreference(preference);
	}
	
	public void launch(Context context) {
		AdManager adManager = new AdManager(context);
		context.startActivity(getMainAction());
		PreferenceManager prefManager = new PreferenceManager(context);
		SuperPreference storedPreference = getPreference();
		SuperPreference currentPreference = prefManager.getPackagePreferences(storedPreference.getFunction());
		if(!preferencesAreEqual(storedPreference, currentPreference)) {
			updateIntent(currentPreference, context);
		}
		if(noSecondaryPreference(storedPreference)) {
			context.startActivity(getMainAction());
			adManager.showAds();
		} else {
			
			
		}
	}

}
