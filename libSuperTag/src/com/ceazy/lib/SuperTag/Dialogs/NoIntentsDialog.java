package com.ceazy.lib.SuperTag.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.DialogFragment;

public class NoIntentsDialog extends DialogFragment {
	
	public NoIntentsDialog newInstance(Messenger canceledMessenger) {
		Bundle arguments = new Bundle();
		arguments.putParcelable("messenger", canceledMessenger);
		NoIntentsDialog dialog = new NoIntentsDialog();
		dialog.setArguments(arguments);
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Messenger canceledMessenger = getArguments().getParcelable("messenger");
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
		.setTitle("Error")
		.setMessage("Cannot search for this content")
		.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
				sendCanceledMessage(canceledMessenger);
			}
			
		})
		.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				sendCanceledMessage(canceledMessenger);
			}
			
		});
		return builder.create();
	}
	
	private void sendCanceledMessage(Messenger messenger) {
		Message message = Message.obtain();
		message.obj = "Dialog canceled";
		try {
			messenger.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	

}
