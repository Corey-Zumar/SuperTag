package com.ceazy.lib.SuperTag;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class NotInstalledDialog extends DialogFragment {
	
	public static NotInstalledDialog newInstance(String appName, String pkgName) {
		NotInstalledDialog dialog = new NotInstalledDialog();
		Bundle arguments = new Bundle();
		arguments.putString("appName", appName);
		arguments.putString("pkgName", pkgName);
		dialog.setArguments(arguments);
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
		String appName = args.getString("appName");
		String pkgName = args.getString("pkgName");
		DialogCreator creator = new DialogCreator(getActivity());
		return creator.createAppNotInstalledDialog(appName, pkgName);
	}

}
