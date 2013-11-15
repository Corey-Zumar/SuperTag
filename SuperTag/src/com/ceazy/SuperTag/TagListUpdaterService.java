package com.ceazy.SuperTag;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class TagListUpdaterService extends Service {
	
	UpdateTagReceiver utReceiver;
	Messenger ntMessenger;

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("START", "TED");
		initializeReceiver();
		setMessenger((Messenger) intent.getParcelableExtra("ntMessenger"));
		return null;
	}
	
	private void setMessenger(Messenger ntMessenger) {
		this.ntMessenger = ntMessenger;
	}
	
	private Messenger getMessenger() {
		return ntMessenger;
	}
	
	private void initializeReceiver() {
		utReceiver = new UpdateTagReceiver();
		registerReceiver(utReceiver, new IntentFilter("UPDATE_MAIN_WITH_TAG"));
	}
	
	private void updateMainActivity(Bundle data) {
		Message message = Message.obtain();
		message.setData(data);
		try {
			getMessenger().send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void onDestroy() {
		try {
			unregisterReceiver(utReceiver); 
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}



	class UpdateTagReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals("UPDATE_MAIN_WITH_TAG")) {
				Log.d("HERE", "TOO");
				updateMainActivity(intent.getExtras());
			}
		}
		
	}

}
