package com.marvik.api.timetracker.activity;

public interface ITimeTracker {

	public void onActivityCreated();

	public void onActivityResumed();

	public void onActivityPaused();
	
	public void onActivityDestroyed();

}
