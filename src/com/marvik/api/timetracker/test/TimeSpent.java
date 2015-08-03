package com.marvik.api.timetracker.test;

import android.os.Bundle;
import android.util.Log;

import com.marvik.api.timetracker.R;
import com.marvik.api.timetracker.activity.TimeTrackedActivity;
import com.marvik.api.timetracker.utils.MarvikUtils;

public class TimeSpent extends TimeTrackedActivity {

	long createTime = 0;
	long pauseTime = 0;
	long resumeTime = 0;
	long destroyTime = 0;
	long busyTime = 0;
	long idleTime = 0;
	long totalTime = 0;

	MarvikUtils utils;

	enum TrackType {
		ONCREATE, ONPAUSE, ONRESUME, ONDESTROY, BUSY, IDLE, TOTAL
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		utils = new MarvikUtils(getApplicationContext());

		createTime = System.currentTimeMillis();
		trackActivity(TrackType.ONCREATE);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		pauseTime = System.currentTimeMillis();
		trackActivity(TrackType.ONPAUSE);

		busyTime = busyTime + (pauseTime - resumeTime);
		trackActivity(TrackType.BUSY);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		resumeTime = System.currentTimeMillis();
		trackActivity(TrackType.ONRESUME);

		if (resumeTime > pauseTime) {
			idleTime = idleTime + (resumeTime - pauseTime);
		}
	
		trackActivity(TrackType.IDLE);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		destroyTime = System.currentTimeMillis();
		trackActivity(TrackType.ONDESTROY);

		totalTime = destroyTime - createTime;
		trackActivity(TrackType.TOTAL);
	}

	void trackActivity(TrackType trackType) {
		
		switch (trackType) {
		case ONCREATE:
			Log.i("TIME", "ONCREATE -> " + createTime);
			break;
		case ONRESUME:
			Log.i("TIME", "ONRESUME -> " + resumeTime);
			break;

		case ONPAUSE:
			Log.i("TIME", "ONPAUSE -> " + pauseTime);
			break;

		case ONDESTROY:
			Log.i("TIME", "ONDESTROY -> " + destroyTime);
			break;

		case BUSY:
			Log.i("TIME", "BUSY_TIME -> " + utils.getElapsedTime(busyTime));
			break;

		case IDLE:
			
			if (idleTime == System.currentTimeMillis()) {
				idleTime = 0;
			}
			if (idleTime < 1) {
				idleTime = 0;
			}
			Log.wtf("TIME"," RESUME > PAUSE = "+ String.valueOf(resumeTime > pauseTime));
			Log.i("TIME", "IDLE_TIME -> " + utils.getElapsedTime(idleTime));
			break;

		case TOTAL:
			Log.i("TIME", "TOTAL_TIME -> " + utils.getElapsedTime(totalTime));
			break;
		}
	}
}
