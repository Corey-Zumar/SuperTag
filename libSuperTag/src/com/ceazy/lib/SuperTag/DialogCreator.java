package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

class DialogCreator {
	
	Context context;
	Messenger messenger;
	
	protected DialogCreator(Context context) {
		this.context = context;
	}
	
	protected void setKillMessenger(Messenger messenger) {
		this.messenger = messenger;
	}

	private Context getContext() {
		return context;
	}
	
	private Messenger getMessenger() {
		return messenger;
	}
	
	private void killActivity() {
		if(getMessenger() != null) {
			try {
				getMessenger().send(Message.obtain());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private LayoutInflater getLayoutInflater() {
		return (LayoutInflater) getContext().getSystemService(FragmentActivity.LAYOUT_INFLATER_SERVICE);
	}
	
	private ContextThemeWrapper getThemeWrapper() {
		return new ContextThemeWrapper(getContext(), DialogFragment.STYLE_NO_TITLE);
	}
	
	private Typeface getBariolFont() {
		FontManager fontManager = new FontManager(getContext());
		return fontManager.getFontFromResource(R.raw.bariol_regular);
	}
	
	Dialog senderDialog;
	
	protected Dialog createSenderDialog(final Map<String, List<String>> tagsAndFunctions, final EditText etMsgBox) {
		View dialogView = getLayoutInflater().inflate(R.layout.chooser_layout, null);
		TextView tvTitleText = (TextView) dialogView.findViewById(R.id.tvTitleText);
		TextView tvInstructions = (TextView) dialogView.findViewById(R.id.tvInstructions);
		tvInstructions.setText("Choose a category...");
		final List<String> functions = new ArrayList<String>(tagsAndFunctions.keySet());
		ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, functions);
		ListView lvOptions = (ListView) dialogView.findViewById(R.id.lvOptions);
		lvOptions.setAdapter(adapter);
		lvOptions.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				createTagChooserDialog(tagsAndFunctions.get(functions.get(position)), etMsgBox);
				senderDialog.dismiss();
			}
			
		});
		senderDialog = new AlertDialog.Builder(getThemeWrapper())
		.setView(dialogView)
		.setNegativeButton("Cancel", null)
		.create();
		return senderDialog;
	}
	
	Dialog tagChooserDialog;
	
	private Dialog createTagChooserDialog(final List<String> tags, final EditText msgBox) {
		View dialogView = getLayoutInflater().inflate(R.layout.chooser_layout, null);
		TextView tvTitleText = (TextView) dialogView.findViewById(R.id.tvTitleText);
		TextView tvInstructions = (TextView) dialogView.findViewById(R.id.tvInstructions);
		tvInstructions.setText("Choose a hashtag...");
		ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, tags);
		ListView lvOptions = (ListView) dialogView.findViewById(R.id.lvOptions);
		lvOptions.setAdapter(adapter);
		lvOptions.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				msgBox.setText(msgBox.getText() + " " + tags.get(position));
				tagChooserDialog.dismiss();
			}
			
		});
		tagChooserDialog = new AlertDialog.Builder(getThemeWrapper())
		.setView(dialogView)
		.setNegativeButton("Cancel", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				senderDialog.show();
			}
			
		})
		.create();
		return tagChooserDialog;
	}
	
	private Dialog createMsgDialog(String msgText, boolean download, final Intent downloadIntent, String negButtonText) {
		View dialogView = getLayoutInflater().inflate(R.layout.msg_layout, null);
		TextView msgTitleText = (TextView) dialogView.findViewById(R.id.tvMsgTitleText);
		TextView msgBodyText = (TextView) dialogView.findViewById(R.id.tvMsgBody);
		msgBodyText.setText(msgText);
		Typeface tfBariol = getBariolFont();
		msgTitleText.setTypeface(tfBariol);
		msgBodyText.setTypeface(tfBariol);
		ContextThemeWrapper dialogThemeWrapper = new ContextThemeWrapper(getContext(), DialogFragment.STYLE_NO_TITLE);
		AlertDialog.Builder builder = new AlertDialog.Builder(dialogThemeWrapper)
		.setView(dialogView)
		.setNegativeButton(negButtonText, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				killActivity();
			}
			
		});
		if(download) {
			builder.setPositiveButton("Download", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					try {
						getContext().startActivity(downloadIntent);
						killActivity();
					} catch(ActivityNotFoundException e) {
						createPlayStoreNotInstalledDialog();
					}
				
				}
			
			});
		}
		return builder.create();
	}
	
	private Dialog createPlayStoreNotInstalledDialog() {
		String msg = "Please download the Google Play Store application to install apps!";
		return createMsgDialog(msg, false, null, "OK");
	}
	
	protected Dialog createAppNotInstalledDialog(String appName, String pkgName) {
		String msg = "Please download the '" + appName + "' application to perform this action!";
		Log.d("PKGNAME", pkgName);
		Uri downloadUri = Uri.parse("market://details?id="+pkgName);
		Intent downloadIntent = new Intent(Intent.ACTION_VIEW, downloadUri);
		return createMsgDialog(msg, true, downloadIntent, "Cancel");
		
	}
	
	protected Dialog createSuperTagAdDialog() {
		String msg = "Please download the Super Tag application to access more features!";
		Uri stUri = Uri.parse("market://details?id=com.ceazy.SuperTag");
		Intent stIntent = new Intent(Intent.ACTION_VIEW, stUri);
		return createMsgDialog(msg, true, stIntent, "Cancel");
	}
	
	
}
