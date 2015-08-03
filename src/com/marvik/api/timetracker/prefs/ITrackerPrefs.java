package com.marvik.api.timetracker.prefs;

public interface ITrackerPrefs {

	/*long currentTime, activityStartTime, activityPauseTime, activityResumeTime,
	activityDestroyTime;

	long busyTime,idleTime,totalTime;*/

	/**
	 * @return the currentTime
	 */
	@Deprecated
	public long getCurrentTime() ;
	

	/**
	 * @return the currentSystemTime
	 */
	long getCurrentSystemTime();

	/**
	 * @param currentTime the currentTime to set
	 */
	public void setCurrentTime(long currentTime);

	/**
	 * @return the activityStartTime
	 */
	public long getActivityCreateTime();

	/**
	 * @param activityStartTime the activityStartTime to set
	 */
	public void setActivityCreateTime(long activityCreateTime);

	/**
	 * @return the activityPauseTime
	 */
	public long getActivityPauseTime();
	/**
	 * @param activityPauseTime the activityPauseTime to set
	 */
	public void setActivityPauseTime(long activityPauseTime);

	/**
	 * @return the activityResumeTime
	 */
	public long getActivityResumeTime() ;

	/**
	 * @param activityResumeTime the activityResumeTime to set
	 */
	public void setActivityResumeTime(long activityResumeTime) ;

	/**
	 * @return the activityDestroyTime
	 */
	public long getActivityDestroyTime();
	/**
	 * @param activityDestroyTime the activityDestroyTime to set
	 */
	public void setActivityDestroyTime(long activityDestroyTime) ;

	/**
	 * @return the busyTime
	 */
	public long getBusyTime();
	/**
	 * @param busyTime the busyTime to set
	 */
	public void setBusyTime(long busyTime);

	/**
	 * @return the idleTime
	 */
	public long getIdleTime();

	/**
	 * @param idleTime the idleTime to set
	 */
	public void setIdleTime(long idleTime) ;

	/**
	 * @return the totalTime
	 */
	public long getTotalTime();

	/**
	 * @param totalTime the totalTime to set
	 */
	public void setTotalTime(long totalTime);


	
	
}
