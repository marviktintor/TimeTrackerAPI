package com.marvik.api.timetracker.receiver;

import com.marvik.api.timetracker.constants.ReceiverConsts;
import com.marvik.api.timetracker.constants.TimeTrackType;
import com.marvik.api.timetracker.services.TrackerService;
import com.marvik.api.timetracker.utils.TrackerUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Receiver extends BroadcastReceiver {
	String TAG = getClass().getSimpleName();
	String create = ReceiverConsts.ACTIVITY_ACTION_CREATE;
	String resume = ReceiverConsts.ACTIVITY_ACTION_RESUME;
	String pause = ReceiverConsts.ACTIVITY_ACTION_PAUSE;
	String destroy = ReceiverConsts.ACTIVITY_ACTION_DESTROY;

	TrackerUtils trackerUtils;
	Context context;

	String action;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		init(context, intent);
		handleReceivedBroadcast();

	}

	private void init(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;

		trackerUtils = new TrackerUtils(context);
		action = intent.getAction();

		Log.i(TAG, action);

	}

	public Context getContext() {
		return context;
	}

	private void handleReceivedBroadcast() {
		// TODO Auto-generated method stub
		if (action.equals(create)) {
			trackerUtils.setActivityCreateTime(System.currentTimeMillis());
		}
		if (action.equals(resume)) {
			trackerUtils.setActivityResumeTime(System.currentTimeMillis());
		}
		if (action.equals(pause)) {
			trackerUtils.setActivityPauseTime(System.currentTimeMillis());
		}
		if (action.equals(destroy)) {
			trackerUtils.setActivityDestroyTime(System.currentTimeMillis());
		}
		
	}
}
