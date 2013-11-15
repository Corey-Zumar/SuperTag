package com.ceazy.SuperTag;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity {
	
	ServiceConnection ntServiceConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		startServices();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void startServices() {
		ntServiceConnection = new ServiceConnection() {

			@Override
			public void onServiceConnected(ComponentName name, IBinder binder) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				
			}
			
		};
		Messenger ntMessenger = new Messenger(new NewTagHandler());
		Intent iStartUpdater = new Intent(this, TagListUpdaterService.class);
		iStartUpdater.putExtra("ntMessenger", ntMessenger);
		bindService(iStartUpdater, ntServiceConnection, BIND_AUTO_CREATE);
		if(!receiverServiceStarted()) {
			Intent iStartReceiverService = new Intent(this, TagLaunchReceiverService.class);
			startService(iStartReceiverService);
		}
	}
	
	private boolean receiverServiceStarted() { // CREDITS---STACKOVERFLOW USER:
		// geekQ
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (service.service.getClassName().equals("com.ceazy.SuperTag.TagLaunchReceiverService")) {
				return true;
			}
		}
		return false;
	}
	
	
	
	@Override
	protected void onDestroy() {
		try {
			unbindService(ntServiceConnection);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}



	class NewTagHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			Log.d("LAUNCHED PKG", data.getString("launchedPkg"));
			super.handleMessage(msg);
		}
		
	}

}
