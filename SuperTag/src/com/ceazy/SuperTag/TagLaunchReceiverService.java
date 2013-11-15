package com.ceazy.SuperTag;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class TagLaunchReceiverService extends Service {
	
	LaunchReceiver receiver;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		registerReceiver();
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void registerReceiver() {
		receiver = new LaunchReceiver();
		registerReceiver(receiver, new IntentFilter("SUPER_TAG_LAUNCHED"));
	}
	
	class LaunchReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals("SUPER_TAG_LAUNCHED")) {
				Bundle extras = intent.getExtras();
				Intent iStartInfoActivity = new Intent(context, TagInfoActivity.class);
				iStartInfoActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				iStartInfoActivity.putExtras(extras);
				context.startActivity(iStartInfoActivity);
				Intent iNotifyUpdater = new Intent("UPDATE_MAIN_WITH_TAG");
				iNotifyUpdater.putExtras(extras);
				Log.d("GOT", "HERE");
				PendingIntent notifyPI = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), iNotifyUpdater, 0);
				try {
					notifyPI.send();
				} catch (CanceledException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
