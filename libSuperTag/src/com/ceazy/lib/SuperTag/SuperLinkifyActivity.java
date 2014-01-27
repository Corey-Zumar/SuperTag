package com.ceazy.lib.SuperTag;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.FragmentActivity;

public class SuperLinkifyActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		String data = getIntent().getData().toString();
		String phrase = data.substring(data.indexOf("#"));
		parsePhraseAndLaunch(phrase);
	}

	protected void parsePhraseAndLaunch(String phrase) {
		SuperTextAnalyzer analyzer = new SuperTextAnalyzer(this);
		SuperTag launchTag = analyzer.getFirstTag(phrase);
		launchTag.getIntent(this, null).launch(this, new Messenger(new DestroyHandler()));
	}
	
	class DestroyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			finish();
			super.handleMessage(msg);
		}
		
	}
}
