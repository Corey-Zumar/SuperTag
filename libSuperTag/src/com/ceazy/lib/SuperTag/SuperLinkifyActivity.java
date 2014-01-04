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
		setTheme();
		String data = getIntent().getData().toString();
		String phrase = data.substring(data.indexOf("#"));
		parsePhraseAndLaunch(phrase);
		SuperTag tag = new SuperTag("herpes", "dur", "videoMedia");
		
		tag.setIntent(null);
	}

	protected void setTheme() {
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			//setTheme(android.R.style.Theme_Holo_Light);
		}
	}

	protected void parsePhraseAndLaunch(String phrase) {
		SuperTextAnalyzer analyzer = new SuperTextAnalyzer(this);
		SuperTag launchTag = analyzer.getSingleTag(phrase);
		launchTag.getIntent(this).launch(this, new Messenger(new DestroyHandler()));
		/*Handler testHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				String key = msg.getData().getString("key");
				//Log.d("RESULT", msg.getData().getString("result"));
				if(key.equals("food")) {
					List<SuperRestaurant> restaurantList = msg.getData()
							.getParcelableArrayList("result");
					for(SuperRestaurant restaurant : restaurantList) {
						Log.d(restaurant.getName(), 
								restaurant.getAddressInfo()[0]);
					}
					
				}
				super.handleMessage(msg);
			}
			
		};
		launchTag.getJSON(this, new Messenger(testHandler), true);*/
	}
	
	class DestroyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			finish();
			super.handleMessage(msg);
		}
		
	}
}
