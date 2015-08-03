package com.marvik.api.timetracker.prefs;

import com.marvik.api.timetracker.constants.PrefsConsts;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrefsHandler implements ITrackerPrefs {

	Context context;
	Editor editor;
	SharedPreferences prefs;

	public PrefsHandler(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		editor = prefs.edit();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	public SharedPreferences getPrefs() {
		return prefs;
	}

	public void setPrefs(SharedPreferences prefs) {
		this.prefs = prefs;
	}
	
	@Override
	public long getCurrentTime() {
		// TODO Auto-generated method stub
		return prefs.getLong(PrefsConsts.PREFS_CURRENT_TIME, System.currentTimeMillis());
	}
	
	@Override
	public long getCurrentSystemTime() {
		// TODO Auto-generated method stub
		return System.currentTimeMillis();
	}

	@Override
	public void setCurrentTime(long currentTime) {
		// TODO Auto-generated method stub
		editor.putLong(PrefsConsts.PREFS_CURRENT_TIME, currentTime);
		editor.commit();
	}

	@Override
	public long getActivityCreateTime() {
		// TODO Auto-generated method stub
		return prefs.getLong(PrefsConsts.PREFS_ACTIVITY_CREATE_TIME, getCurrentSystemTime());
	}

	@Override
	public void setActivityCreateTime(long activityCreateTime) {
		// TODO Auto-generated method stub
		editor.putLong(PrefsConsts.PREFS_ACTIVITY_CREATE_TIME,activityCreateTime );
		editor.commit();
		Log.i("ACTIVITY_LOGS", "CREATE_TIME " +activityCreateTime);
	}

	@Override
	public long getActivityPauseTime() {
		// TODO Auto-generated method stub
		return prefs.getLong(PrefsConsts.PREFS_ACTIVITY_PAUSE_TIME, getCurrentSystemTime());
	}

	@Override
	public void setActivityPauseTime(long activityPauseTime) {
		// TODO Auto-generated method stub
		editor.putLong(PrefsConsts.PREFS_ACTIVITY_PAUSE_TIME,activityPauseTime );
		editor.commit();
		Log.i("ACTIVITY_LOGS", "PAUSE_TIME " +activityPauseTime);
	}

	@Override
	public long getActivityResumeTime() {
		// TODO Auto-generated method stub
		return prefs.getLong(PrefsConsts.PREFS_ACTIVITY_RESUME_TIME, getCurrentSystemTime());
	}

	@Override
	public void setActivityResumeTime(long activityResumeTime) {
		// TODO Auto-generated method stub
		editor.putLong(PrefsConsts.PREFS_ACTIVITY_RESUME_TIME, activityResumeTime);
		editor.commit();
		Log.i("ACTIVITY_LOGS", "RESUME_TIME " +activityResumeTime);
	}

	@Override
	public long getActivityDestroyTime() {
		// TODO Auto-generated method stub
		return prefs.getLong(PrefsConsts.PREFS_ACTIVITY_DESTROY_TIME, getCurrentSystemTime());
	}

	@Override
	public void setActivityDestroyTime(long activityDestroyTime) {
		// TODO Auto-generated method stub
		editor.putLong(PrefsConsts.PREFS_ACTIVITY_DESTROY_TIME, activityDestroyTime);
		editor.commit();
		Log.i("ACTIVITY_LOGS", "DESTROY_TIME " +activityDestroyTime);
		
		//Clear All:
		setBusyTime(0);
		setIdleTime(0);
		setBusyTime(0);
		
	}

	@Override
	public long getBusyTime() {
		// TODO Auto-generated method stub
		return prefs.getLong(PrefsConsts.PREFS_ACTIVITY_BUSY_TIME, 0);
	}

	@Override
	public void setBusyTime(long busyTime) {
		// TODO Auto-generated method stub
		editor.putLong(PrefsConsts.PREFS_ACTIVITY_BUSY_TIME,busyTime + getBusyTime());
		editor.commit();
	}

	@Override
	public long getIdleTime() {
		// TODO Auto-generated method stub
		return prefs.getLong(PrefsConsts.PREFS_ACTIVITY_IDLE_TIME, 0);
	}

	@Override
	public void setIdleTime(long idleTime) {
		// TODO Auto-generated method stub
		editor.putLong(PrefsConsts.PREFS_ACTIVITY_IDLE_TIME,idleTime +getIdleTime() );
		editor.commit();
	}

	@Override
	public long getTotalTime() {
		// TODO Auto-generated method stub
		return prefs.getLong(PrefsConsts.PREFS_ACTIVITY_SPEND_TIME, 0);
	}

	@Override
	public void setTotalTime(long totalTime) {
		// TODO Auto-generated method stub
		editor.putLong(PrefsConsts.PREFS_ACTIVITY_SPEND_TIME, totalTime +getTotalTime());
		editor.commit();
	}

}
