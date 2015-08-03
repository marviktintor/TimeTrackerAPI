package com.marvik.api.timetracker.utils;

import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.URL;
import java.net.URLConnection;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.marvik.api.timetracker.constants.TimeTrackType;
import com.marvik.api.timetracker.constants.TrackerServiceConsts;
import com.marvik.api.timetracker.database.StatisticsDatabase;
import com.marvik.api.timetracker.prefs.PrefsHandler;
import com.marvik.api.timetracker.provider.StatisticsProvider;

public class TrackerUtils {

	Context context;
	MarvikUtils marvikUtils;
	PrefsHandler prefsHandler;
	
	public TrackerUtils(Context context) {
		this.context = context;
		marvikUtils = new MarvikUtils(context);
		prefsHandler = new PrefsHandler(context);
	}
	

	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}

	public long getBusyTime() {
		return prefsHandler.getActivityResumeTime() < prefsHandler.getActivityPauseTime()?
				prefsHandler.getActivityPauseTime() - prefsHandler.getActivityResumeTime() :0;
	}

	public long getIdleTime() {
		return 
				prefsHandler.getActivityPauseTime()<prefsHandler.getActivityResumeTime()?
						prefsHandler.getActivityResumeTime() - prefsHandler.getActivityPauseTime():0;
	}

	public long getTotalTime() {
		return prefsHandler.getActivityDestroyTime()- prefsHandler.getActivityCreateTime();
	}

	public void logTimeSpent(final TimeTrackType timeTrackType) {

		new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				ContentValues values = new ContentValues();
				values.put(StatisticsDatabase.COL_DATE, marvikUtils.getDate(System.currentTimeMillis()));
				values.put(StatisticsDatabase.COL_APP_LAUNCHES,getLaunches(marvikUtils.getDateToday()));
				values.put(StatisticsDatabase.COL_SPENT_TIME_BUSY,getTimeSpentBusy(marvikUtils.getDateToday()) );
				values.put(StatisticsDatabase.COL_SPENT_TIME_IDLE,getTimeSpentIdle(marvikUtils.getDateToday()) );
				values.put(StatisticsDatabase.COL_SPENT_TIME_TOTAL,getTimeSpentTotal(marvikUtils.getDateToday()) );
				
				switch (timeTrackType) {
				case ONCREATE:
					values.put(StatisticsDatabase.COL_APP_LAUNCHES,(getLaunches(marvikUtils.getDateToday())+1 ));
					break;
				case ONPAUSE:
					long busyTime = prefsHandler.getActivityPauseTime()- prefsHandler.getActivityResumeTime();
					Log.i("TIME_DIFF", "ONPAUSE -> " +(prefsHandler.getActivityPauseTime()- prefsHandler.getActivityResumeTime()));
					values.put(StatisticsDatabase.COL_SPENT_TIME_BUSY,busyTime +getTimeSpentBusy(marvikUtils.getDateToday()));
					break;
				case ONRESUME:
					Log.i("TIME_DIFF", "ONRESUME -> " +( prefsHandler.getActivityResumeTime() - prefsHandler.getActivityPauseTime()));
					long idleTime = prefsHandler.getActivityResumeTime() - prefsHandler.getActivityPauseTime();
					values.put(StatisticsDatabase.COL_SPENT_TIME_IDLE,idleTime +getTimeSpentIdle(marvikUtils.getDateToday()));
					break;
				case ONDESTROY:
					Log.i("TIME_DIFF", "ONDESTROY -> " +(System.currentTimeMillis() - prefsHandler.getActivityCreateTime()));
					long totalTime = System.currentTimeMillis() - prefsHandler.getActivityCreateTime();
					values.put(StatisticsDatabase.COL_SPENT_TIME_TOTAL, totalTime + getTimeSpentTotal(marvikUtils.getDateToday()) );
					break;
				}
				
				/*Log.i("LAUNCHES", "Today "+(getLaunches(marvikUtils.getDateToday())+1 ));
				
				if(isTodayLogged()){ 
					int rows = getContext().getContentResolver().update(StatisticsProvider.CONTENT_URI, values, StatisticsDatabase.COL_DATE+"='"+marvikUtils.getDateToday()+"'", null);
					Log.i("UPDATED", ""+rows +" rows ");
				}else{
					getContext().getContentResolver().insert(StatisticsProvider.CONTENT_URI, values);
					Log.i("INSERTED", " TODAY STATS ");
				}*/
				
				
			}
		}.run();
	}
	public int getLaunches(String dateToday) {
		// TODO Auto-generated method stub
		int launches=0;
		
		Cursor cursor = getContext().getContentResolver().query(StatisticsProvider.CONTENT_URI, null, StatisticsDatabase.COL_DATE+"='"+dateToday+"'", null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			launches = cursor.getInt(cursor.getColumnIndex(StatisticsDatabase.COL_APP_LAUNCHES));	
		}
		cursor.close();
		return launches;
	}

	public long getTimeSpentBusy(String dateToday) {
		// TODO Auto-generated method stub
		long timeSpent=0;
		
		Cursor cursor = getContext().getContentResolver().query(StatisticsProvider.CONTENT_URI, null, StatisticsDatabase.COL_DATE+"='"+dateToday+"'", null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			timeSpent = cursor.getLong(cursor.getColumnIndex(StatisticsDatabase.COL_SPENT_TIME_BUSY));	
		}
		cursor.close();
		return timeSpent;
	}

	public long getTimeSpentIdle(String dateToday) {
		// TODO Auto-generated method stub
		long timeSpent=0;
		
		Cursor cursor = getContext().getContentResolver().query(StatisticsProvider.CONTENT_URI, null, StatisticsDatabase.COL_DATE+"='"+dateToday+"'", null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			timeSpent = cursor.getLong(cursor.getColumnIndex(StatisticsDatabase.COL_SPENT_TIME_IDLE));	
		}
		cursor.close();
		return timeSpent;
	}

	public long getTimeSpentTotal(String dateToday) {
		
		long timeSpent=0;
		
		Cursor cursor = getContext().getContentResolver().query(StatisticsProvider.CONTENT_URI, null, StatisticsDatabase.COL_DATE+"='"+dateToday+"'", null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			timeSpent = cursor.getLong(cursor.getColumnIndex(StatisticsDatabase.COL_SPENT_TIME_TOTAL));	
		}
		cursor.close();
		return timeSpent;
	}

	public boolean isTodayLogged() {
		// TODO Auto-generated method stub
		boolean logged = false;
		
		Cursor cursor = getContext().getContentResolver().query(StatisticsProvider.CONTENT_URI, new String []{StatisticsDatabase.COL_DATE}, StatisticsDatabase.COL_DATE+"='"+marvikUtils.getDateToday()+"'", null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			if(cursor.getString(cursor.getColumnIndex(StatisticsDatabase.COL_DATE)).equals(marvikUtils.getDate(System.currentTimeMillis()))){
				logged=true;
			}
		}
		cursor.close();
		return logged;
	}

	public void setActivityCreateTime(long activityStartTime) {
		// TODO Auto-generated method stub
		prefsHandler.setActivityCreateTime(activityStartTime);
		broadcast(TrackerServiceConsts.ACTIVITY_ACTION_CREATED);
	}

	public void setActivityResumeTime(long activityResumeTime) {
		// TODO Auto-generated method stub
		prefsHandler.setActivityResumeTime(activityResumeTime);
		broadcast(TrackerServiceConsts.ACTIVITY_ACTION_RESUMED);
	}

	public void setActivityPauseTime(long activityPauseTime) {
		// TODO Auto-generated method stub
		prefsHandler.setActivityPauseTime(activityPauseTime);
		broadcast(TrackerServiceConsts.ACTIVITY_ACTION_PAUSED);
	}

	public void setActivityDestroyTime(long activityDestroyTime) {
		// TODO Auto-generated method stub
		prefsHandler.setActivityDestroyTime(activityDestroyTime);
		broadcast(TrackerServiceConsts.ACTIVITY_ACTION_DESTROYED);
	}
	public void toast(String string, int length) {
		// TODO Auto-generated method stub
		Toast.makeText(context, string, length).show();
		Log.i("Utilities", "Make a TOAST");
	}

	public void broadcast(String action) {
		Log.i("Utilities", "broadcast -  action only");
		Intent intent = new Intent(action);
		context.sendBroadcast(intent);
	}

	public void broadcast(String action, Bundle extras) {
		Log.i("Utilities", "broadcast bundled action");
		Intent intent = new Intent(action);
		intent.putExtras(extras);
		context.sendBroadcast(intent);
	}

	public void broadcast(String action, int flags) {
		Log.i("Utilities", "broadcast flagged action");
		Intent intent = new Intent(action);
		intent.setFlags(flags);
		context.sendBroadcast(intent);
	}

	public void broadcast(String action, int flags, Bundle extras) {
		Log.i("Utilities", "broadcast bundled,flagged action");
		Intent intent = new Intent(action);
		intent.setFlags(flags);
		intent.putExtras(extras);
		context.sendBroadcast(intent);
	}

	public void broadcast(String action, int flags, Bundle extras,
			String permission) {
		Log.i("Utilities", "broadcast permission required,bundled,flagged action");
		Intent intent = new Intent(action);
		intent.setFlags(flags);
		intent.putExtras(extras);
		context.sendBroadcast(intent, permission);
	}

	public void startActivity(String className) {
		Log.i("Utilities", "startActivity - stringClassname");
		Intent intent = new Intent(className);
		context.startActivity(intent);

	}
	public void startActivity(String className,String action) {
		Log.i("Utilities", "startActivity - stringClassname");
		Intent intent = new Intent(className);
		intent.setAction(action);
		context.startActivity(intent);

	}

	public void startActivity(String className, int flags) {
		Log.i("Utilities", "startActivity - flaged stringClassname");
		Intent intent = new Intent(className);
		intent.setFlags(flags);
		context.startActivity(intent);
	}
	public void startActivity(String className, int flags,String action) {
		Log.i("Utilities", "startActivity - flaged stringClassname");
		Intent intent = new Intent(className);
		intent.setAction(action);
		intent.setFlags(flags);
		context.startActivity(intent);
	}

	public void startActivity(String className, Bundle extras) {
		Log.i("Utilities", "startActivity - bundled stringClassname");
		Intent intent = new Intent(className);
		intent.putExtras(extras);
		context.startActivity(intent);
	}
	public void startActivity(String className, Bundle extras,String action) {
		Log.i("Utilities", "startActivity - bundled stringClassname");
		Intent intent = new Intent(className);
		intent.putExtras(extras);
		intent.setAction(action);
		context.startActivity(intent);
	}

	public void startActivity(String className, int flags, Bundle extras) {
		Log.i("Utilities", "startActivity - bundled,flaged,stringClassname");
		Intent intent = new Intent(className);
		intent.setFlags(flags);
		intent.putExtras(extras);
		context.startActivity(intent);
	}
	public void startActivity(String className, int flags, Bundle extras,String action) {
		Log.i("Utilities", "startActivity - bundled,flaged,stringClassname");
		Intent intent = new Intent(className);
		intent.setAction(action);
		intent.setFlags(flags);
		intent.putExtras(extras);
		context.startActivity(intent);
	}

	public void startActivity(Class<?> className) {
		Log.i("Utilities", "startActivity - className");
		Intent intent = new Intent(context, className);
		context.startActivity(intent);
	}
	public void startActivity(Class<?> className,String action) {
		Log.i("Utilities", "startActivity - className");
		Intent intent = new Intent(context, className);
		intent.setAction(action);
		context.startActivity(intent);
	}

	public void startActivity(Class<?> className, int flags) {
		Log.i("Utilities", "startActivity - flagged,className");
		Intent intent = new Intent(context, className);
		intent.setFlags(flags);
		context.startActivity(intent);
	}
	public void startActivity(Class<?> className, int flags,String action) {
		Log.i("Utilities", "startActivity - flagged,className");
		Intent intent = new Intent(context, className);
		intent.setAction(action);
		intent.setFlags(flags);
		context.startActivity(intent);
	}
	public void startActivity(Class<?> className, Bundle extras) {
		Log.i("Utilities", "startActivity - bundled className");
		Intent intent = new Intent(context, className);
		intent.putExtras(extras);
		context.startActivity(intent);
	}
	public void startActivity(Class<?> className, Bundle extras,String action) {
		Log.i("Utilities", "startActivity - bundled className");
		Intent intent = new Intent(context, className);
		intent.setAction(action);
		intent.putExtras(extras);
		context.startActivity(intent);
	}
	public void startActivity(Class<?> className, int flags, Bundle extras) {
		Log.i("Utilities", "startActivity - bundled,flagged,className");
		Intent intent = new Intent(context, className);
		intent.setFlags(flags);
		intent.putExtras(extras);
		context.startActivity(intent);
	}
	public void startActivity(Class<?> className, int flags, Bundle extras,String action) {
		Log.i("Utilities", "startActivity - bundled,flagged,className");
		Intent intent = new Intent(context, className);
		intent.setFlags(flags);
		intent.setAction(action);
		intent.putExtras(extras);
		context.startActivity(intent);
	}

	public String getString(EditText editText) {
		// TODO Auto-generated method stub
		Log.i("Utilities", "getEditTextString"+editText.getId());
		
		return editText.getText().toString();
	}

	public Bitmap getBitmap(final String bitmapUrl) {
		Log.i("Utilities", "Download Images");
		
		new AsyncTask<String, Void, Bitmap>(){
	
			@Override
			protected Bitmap doInBackground(String... params) {
				// TODO Auto-generated method stub
				try {
					URL url = new URL(bitmapUrl);
					URLConnection connection = url.openConnection();
					Bitmap bitmap = BitmapFactory.decodeStream(connection
							.getInputStream());
					return bitmap;
				} catch (MalformedParameterizedTypeException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		}.execute();
		
		return null;
		
		
	}


}
