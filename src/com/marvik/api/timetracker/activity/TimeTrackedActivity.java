package com.marvik.api.timetracker.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.marvik.api.timetracker.constants.ReceiverConsts;
import com.marvik.api.timetracker.services.TrackerService;
import com.marvik.api.timetracker.utils.MarvikUtils;
import com.marvik.api.timetracker.utils.TrackerUtils;

/**
 * <p>Activity that allows the tracking of time.<br />
 * Extend this class instead of the Activity 
 * class to track all the time spent on an activity.<br /></p>
 * 
 * Ensure that you Override the methods below:<br />
 * <ul><li>onCreate</li><li>onResume</li><li>onPause</li><li>onDestroy</li></ul>
 * @author victor_mwenda
 *
 */
		
public class TimeTrackedActivity extends Activity implements ITimeTracker {

	MarvikUtils marvikUtils;
	TrackerUtils trackerUtils;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		super.onCreate(savedInstanceState);
		onActivityCreated();
		marvikUtils = new MarvikUtils(this);
		trackerUtils = new TrackerUtils(this);
		
		
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		super.onPause();
		onActivityPaused();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
		onActivityResumed();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	
		super.onDestroy();
		onActivityDestroyed();

	}

	@Override
	public void onActivityCreated() {
		// TODO Auto-generated method stub
		
		startService(new Intent(getApplicationContext(), TrackerService.class));
		
	}

	@Override
	public void onActivityResumed() {
		// TODO Auto-generated method stub
		trackerUtils.broadcast(ReceiverConsts.ACTIVITY_ACTION_RESUME);
	}

	@Override
	public void onActivityPaused() {
		// TODO Auto-generated method stub
		trackerUtils.broadcast(ReceiverConsts.ACTIVITY_ACTION_PAUSE);
	}

	@Override
	public void onActivityDestroyed() {
		// TODO Auto-generated method stub
		trackerUtils.broadcast(ReceiverConsts.ACTIVITY_ACTION_DESTROY);
		stopService(new Intent(getApplicationContext(), TrackerService.class));
	}

	
}
