package com.ceazy.lib.SuperTag;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class SuperIntent implements Parcelable {
	
	private Intent mainAction, secondaryAction;
	private SuperPreference preference;
	
	public SuperIntent(Intent mainAction, SuperPreference preference) {
		setMainAction(mainAction);
		setPreference(preference);
	}
	
	protected SuperIntent(Parcel in) {
		setMainAction((Intent) in.readParcelable(null));
		setPreference((SuperPreference) in.readParcelable(SuperPreference.class.getClassLoader()));
		if(in.dataAvail() > 0) {
			setSecondaryAction((Intent) in.readParcelable(null));
		}
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
	
	public SuperPreference getPreference() {
		return preference;
	}
	
	public Intent getMainAction() {
		return mainAction;
	}
	
	private Intent getSecondaryAction() {
		return secondaryAction;
	}
	
	private String getHashPhrase() {
		return getMainAction().getExtras().getString("hashPhrase");
	}
	
	protected Bundle compileSuperIntentData(Context context) {
		Bundle data = new Bundle();
		data.putString("hashPhrase", getHashPhrase());
		data.putParcelable("mainAction", getMainAction());
		data.putParcelable("secondaryAction", getSecondaryAction());
		data.putParcelable("preference", getPreference());
		data.putString("launchedPkg", context.getPackageName());
		return data;
	}
	
	private boolean noSecondaryPreference(SuperPreference preference) {
		return preference.getSecondaryPkg().equals("none");
	}
	
	
	public void launch(Context context) {
		AdManager adManager = new AdManager(context);
		PreferenceManager prefManager = new PreferenceManager(context);
		if(prefManager.superTagIsInstalled()) {
			Intent iLaunchST = new Intent("SUPER_TAG_LAUNCHED");
			iLaunchST.putExtras(compileSuperIntentData(context));
			PendingIntent superTagPI = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), iLaunchST, 0);
			try {
				superTagPI.send();
			} catch (CanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
				try {
					context.startActivity(getMainAction());
				} catch(ActivityNotFoundException e) {
					SuperPreference preference = getPreference();
					FragmentActivity act = (FragmentActivity) context;
					DialogFragment nID = NotInstalledDialog.newInstance(preference.getPrimaryName(), 
							preference.getPrimaryPkg());
					nID.show(act.getSupportFragmentManager(), "not_installed_dialog");
				}
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeParcelable(getMainAction(), 0);
		out.writeParcelable(getPreference(), 0);
		Intent secondaryAction = getSecondaryAction();
		if(secondaryAction != null) {
			out.writeParcelable(secondaryAction, 0);
		}
		
	}
	
	public static Creator<SuperIntent> CREATOR = new Creator<SuperIntent>() {

		@Override
		public SuperIntent createFromParcel(Parcel in) {
			return new SuperIntent(in);
		}

		@Override
		public SuperIntent[] newArray(int size) {
			// TODO Auto-generated method stub
			return new SuperIntent[size];
		}
		
	};

}