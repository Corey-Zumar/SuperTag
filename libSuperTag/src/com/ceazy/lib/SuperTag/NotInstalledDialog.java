package com.ceazy.lib.SuperTag;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
public class NotInstalledDialog extends DialogFragment {
	
	private Messenger messenger;

	protected static NotInstalledDialog newInstance(ContextThemeWrapper wrapper,
			String appName, String pkgName) {
		NotInstalledDialog dialog = new NotInstalledDialog();
		Bundle arguments = new Bundle();
		arguments.putString("appName", appName);
		arguments.putString("pkgName", pkgName);
		dialog.setArguments(arguments);
		return dialog;
	}
	
	protected static NotInstalledDialog newInstance(ContextThemeWrapper wrapper, 
			String appName, String pkgName, Messenger messenger) {
		NotInstalledDialog dialog = new NotInstalledDialog();
		Bundle arguments = new Bundle();
		arguments.putString("appName", appName);
		arguments.putString("pkgName", pkgName);
		arguments.putParcelable("messenger", messenger);
		dialog.setArguments(arguments);
		dialog.setRetainInstance(true);
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
		Messenger messenger = args.getParcelable("messenger");
		this.messenger = messenger;
		String appName = args.getString("appName");
		String pkgName = args.getString("pkgName");
		DialogCreator creator = new DialogCreator(getActivity());
		if(messenger != null) {
			creator.setKillMessenger(messenger);
		}
		return creator.createAppNotInstalledDialog(appName, pkgName);
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		try {
			messenger.send(Message.obtain());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onCancel(dialog);
	}
	
	

}
