package com.ceazy.SuperTag;
import com.ceazy.SuperTag.Movie.Movie;
import com.ceazy.SuperTag.Movie.MovieDialog;
import com.ceazy.lib.SuperTag.SuperPreference;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class TagInfoActivity extends FragmentActivity {
	
	JSONParser parser;
	ProgressDialog progDialog;

	@Override
	protected void onCreate(Bundle arg0) {
		setTheme();
		showLoadingDialog();
		Bundle data = getIntent().getExtras();
		initialize(data);
		super.onCreate(arg0);
	}
	
	private void setTheme() {
		if(android.os.Build.VERSION.SDK_INT >= 11) {
			setTheme(android.R.style.Theme_Holo_Light_Dialog);
		}
	}
	
	private boolean functionIsSupported(String function) {
		String[] supportedFuncs = getResources().getStringArray(R.array.supported_functions);
		for(String func : supportedFuncs) {
			if(func.equals(function)) {
				return true;
			}
		}
		return false;
	}
	
	private void showLoadingDialog() {
		progDialog = new ProgressDialog(this);
		progDialog.setTitle("Please wait...");
		progDialog.setMessage("Loading your content, nigga...");
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.show();
	}
	
	private void initialize(Bundle data) {
		SuperPreference pref = data.getParcelable("preference");
		String function = pref.getFunction();
		if(functionIsSupported(function)) {
			parser = new JSONParser(this);
			retrieveJSON(data, function);
		} else {
			Intent action = data.getParcelable("mainAction");
			startActivity(action);
			finish();
		}
	}
	
	private void retrieveJSON(Bundle data, String function) {
		JSONRetriever retriever = new JSONRetriever(this, new Messenger(new JSONHandler()), function,
				data.getString("hashPhrase"));
		retriever.retrieveJSONInfo();
	}
	
	private void createFragment(String key, Object object) {
		if(object == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Oops!");
			builder.setMessage("Something went poorly! Check your internet connection, nigguh!");
			progDialog.dismiss();
			builder.create().show();
			return;
		}
		if(key.equals("detailed_place")) {
			
		} else if(key.equals("news")) {
			
		} else if(key.equals("movie_rt")) {
			Movie movie = (Movie) object;
			DialogFragment frag = MovieDialog.newInstance(movie);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			progDialog.dismiss();
			frag.show(ft, "movie_dialog");
		}
	}
	
	class JSONHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			String key = data.getString("key");
			Object obj = parser.parseJSONForKey(key, data.getString("result"));
			createFragment(key, obj);
			super.handleMessage(msg);
		}
		
	}
	
	

}
