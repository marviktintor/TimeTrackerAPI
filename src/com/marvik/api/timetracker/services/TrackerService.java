package com.marvik.api.timetracker.services;

import com.marvik.api.timetracker.constants.ReceiverConsts;
import com.marvik.api.timetracker.constants.TrackerServiceConsts;
import com.marvik.api.timetracker.constants.TimeTrackType;
import com.marvik.api.timetracker.utils.TrackerUtils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TrackerService extends Service {
	TrackerServiceReceiver receiver;
	TrackerUtils trackerUtils;
	String TAG = getClass().getSimpleName();
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		trackerUtils = new TrackerUtils(getApplicationContext());
		receiver = new TrackerServiceReceiver();
		Toast.makeText(getApplicationContext(), "Tracker Service Activated", Toast.LENGTH_SHORT).show();
		trackerUtils.broadcast(ReceiverConsts.ACTIVITY_ACTION_CREATE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		//RegisterReceivers
		registerReceiver(receiver, new IntentFilter(TrackerServiceConsts.ACTIVITY_ACTION_CREATED));
		registerReceiver(receiver, new IntentFilter(TrackerServiceConsts.ACTIVITY_ACTION_RESUMED));
		registerReceiver(receiver, new IntentFilter(TrackerServiceConsts.ACTIVITY_ACTION_PAUSED));
		registerReceiver(receiver, new IntentFilter(TrackerServiceConsts.ACTIVITY_ACTION_DESTROYED));
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//unregisterReceiver(receiver);
		Toast.makeText(getApplicationContext(), "Tracker Service Deactivated", Toast.LENGTH_SHORT).show();
	}

	private class TrackerServiceReceiver extends BroadcastReceiver {

	
		String create = TrackerServiceConsts.ACTIVITY_ACTION_CREATED;
		String resume = TrackerServiceConsts.ACTIVITY_ACTION_RESUMED;
		String pause = TrackerServiceConsts.ACTIVITY_ACTION_PAUSED;
		String destroy = TrackerServiceConsts.ACTIVITY_ACTION_DESTROYED;

		
		Context context;

		String action;
		@Override
		public void onReceive(Context context, Intent intent) {
			
			init(context, intent);
			handleReceivedBroadcast();
	
		}

		private void init(Context context, Intent intent) {
			// TODO Auto-generated method stub
			this.context = context;

		
			action = intent.getAction();

			Log.i(TAG, action);

		}

		public Context getContext() {
			return context;
		}

		private void handleReceivedBroadcast() {
			// TODO Auto-generated method stub
			if (action.equals(create)) {
				trackerUtils.logTimeSpent(TimeTrackType.ONCREATE);
			}
			if (action.equals(resume)) {
				trackerUtils.logTimeSpent(TimeTrackType.ONRESUME);
			}
			if (action.equals(pause)) {
				trackerUtils.logTimeSpent(TimeTrackType.ONPAUSE);
			}
			if (action.equals(destroy)) {
				trackerUtils.logTimeSpent(TimeTrackType.ONDESTROY);
			}
		}
	}
	
}
