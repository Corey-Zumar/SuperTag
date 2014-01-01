package com.ceazy.lib.SuperTag;

import java.util.List;

import com.ceazy.lib.SuperTag.Location.SuperLocation;
import com.ceazy.lib.SuperTag.Movie.SuperMovie;
import com.ceazy.lib.SuperTag.News.SuperArticle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class SuperLinkifyActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTheme();
		String data = getIntent().getData().toString();
		String phrase = data.substring(data.indexOf("#"));
		parsePhraseAndLaunch(phrase);

	}

	protected void setTheme() {
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			//setTheme(android.R.style.Theme_Holo_Light);
		}
	}

	protected void parsePhraseAndLaunch(String phrase) {
		SuperTextAnalyzer analyzer = new SuperTextAnalyzer(this);
		SuperTag launchTag = analyzer.getSingleTag(phrase);
		SuperIntentCreator creator = new SuperIntentCreator(this);
		launchTag = creator.updateTagWithIntents(launchTag);
		launchTag.getIntent().performLaunch(this, new Messenger(new DestroyHandler()));
		Handler testHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				String key = msg.getData().getString("key");
				
				super.handleMessage(msg);
			}
			
		};
		launchTag.getJSON(this, new Messenger(testHandler), true);
	}
	
	class DestroyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			finish();
			super.handleMessage(msg);
		}
		
	}
}
