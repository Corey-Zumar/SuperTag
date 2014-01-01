package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;

public class SuperIntent implements Parcelable {
	
	List<Intent> intentsList;
	
	public SuperIntent(List<Intent> intentsList) {
		this.intentsList = intentsList;
	}
	
	protected SuperIntent(Parcel in) {
		this.intentsList = in.readArrayList((new ArrayList<Intent>())
				.getClass().getClassLoader());
	}
	
	private List<Intent> getIntentsList() {
		return intentsList;
	}
	
	protected void performLaunch(Context context, Messenger destroyMessenger) {
		List<Intent> intentsList = getIntentsList();
		int size = intentsList.size();
		if(size == 0) {
			//Do nothing...
		} else {
			List<Intent> newIntents = new ArrayList<Intent>();
			List<String> includedPkgs = new ArrayList<String>();
			int index = 0;
			int browserIndex = -1;
			boolean include = false;
			for(Intent intent : intentsList) {
				List<ResolveInfo> resolvedList = context.getPackageManager()
						.queryIntentActivities(intent, 0);
				for(ResolveInfo info : resolvedList) {
					String pkgName = info.activityInfo.packageName;
					if(!pkgName.equals("com.android.browser") && !includedPkgs.contains(pkgName)) {
						Intent intentCopy = new Intent(intent);
						intentCopy.setPackage(pkgName);
						newIntents.add(intentCopy);
						includedPkgs.add(pkgName);
						index += 1;
					} else if(intent.getStringExtra("type") != null &&
							pkgName.equals("com.android.browser")) {
						Intent intentCopy = new Intent(intent);
						intentCopy.setPackage(pkgName);
						newIntents.add(intentCopy);
						browserIndex = index;
						index += 1;
						if(intent.getStringExtra("include") != null) {
							include = true;
						}
					}
				}
				
			}
			if(newIntents.size() > 1 && !include && browserIndex > -1) {
				newIntents.remove(browserIndex);
			}
			Intent[] intents = new Intent[newIntents.size() - 1];
			for(int i = 1; i < newIntents.size(); i++) {
				intents[i-1] = newIntents.get(i);
			}
			Intent chooserIntent = Intent.createChooser(newIntents.get(0), "Choose an option...");
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
			try {
				context.startActivity(chooserIntent);
			} catch(ActivityNotFoundException e) {
				//No apps installed error!
			}
			if(destroyMessenger != null) {
				((Activity) context).finish();
			}
		}
	}
	
	public void launch(Context context) {
		performLaunch(context, null);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeList(getIntentsList());
	}
	
	public static Creator<SuperIntent> CREATOR = new Creator<SuperIntent>() {

		@Override
		public SuperIntent createFromParcel(Parcel in) {
			return new SuperIntent(in);
		}

		@Override
		public SuperIntent[] newArray(int size) {
			return new SuperIntent[size];
		}
		
	};
	
	

}
