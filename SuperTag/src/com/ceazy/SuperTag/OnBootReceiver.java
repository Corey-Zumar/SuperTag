package com.ceazy.SuperTag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent iStartLaunchReceiverService = new Intent(context, TagLaunchReceiverService.class);
		context.startService(iStartLaunchReceiverService);
	}

}
