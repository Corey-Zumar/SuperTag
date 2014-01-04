package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/** A compilation of multiple {@link android.content.Intent intent's}
 * associated with a {@link SuperTag SuperTag's} {@link SuperTag#hashPhrase hashphrase} and 
 * {@link SuperTag#function function} that can be launched. 
 * <br></br>
 * A SuperIntent is typically created using a {@link SuperIntentCreator}
 */
public class SuperIntent implements Parcelable {
	
	List<Intent> intentsList;
	
	/** {@link SuperIntent} class constructor
	 * 
	 * @param intentsList A list of intents that can be used to search for a {@link SuperTag
	 * SuperTag's} content
	 */
	public SuperIntent(List<Intent> intentsList) {
		this.intentsList = intentsList;
	}
	
	protected SuperIntent(Parcel in) {
		this.intentsList = in.createTypedArrayList(Intent.CREATOR);
	}
	
	private List<Intent> getIntentsList() {
		return intentsList;
	}
	
	protected void performLaunch(Context context, Messenger messenger) {
		List<Intent> intentsList = getIntentsList();
		int size = intentsList.size();
		if(size == 0) {
			if(messenger != null) {
				sendMessage(messenger, "Error: No intents have been defined");
			}
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
			Intent chooserIntent = Intent.createChooser(newIntents.get(0), 
					"Select an option");
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
			try {
				context.startActivity(chooserIntent);
			} catch(ActivityNotFoundException e) {
				sendMessage(messenger, "Error: No activity found to handle intent" +
						" (user must at least have a compatible browser installed)");
			} catch(Exception e) {
				sendMessage(messenger, "Error: An unexpected error occurred while launching"
						+ " intent");
			}
			if(messenger != null) {
				sendMessage(messenger, "Intent launched successfully");
			}
		}
	}
	
	private void sendMessage(Messenger messenger, String msgObj) {
		if(messenger != null) {
			Message msg = Message.obtain();
			msg.obj = msgObj;
			try {
				messenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**<b><i> public void launch({@link android.content.Context Context} context,
	 * {@link android.os.Messenger Messenger} messenger)</i></b>
	 * <p> Launches the SuperIntent. If the list of intents with which the SuperIntent
	 * was constructed contains multiple intents, a {@link android.content.Intent#createChooser(Intent, CharSequence)
	 * chooser}
	 * intent will be created.
	 * @param context
	 * @param messenger A {@link android.os.Messenger messenger} through which information about
	 * the progress of the launch process can be sent (may be null).
	 */
	public void launch(Context context, Messenger messenger) {
		performLaunch(context, messenger);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(getIntentsList());
	}
	
	Creator<SuperIntent> CREATOR = new Creator<SuperIntent>() {

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
