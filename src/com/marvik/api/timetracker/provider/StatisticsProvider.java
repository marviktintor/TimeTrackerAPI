package com.marvik.api.timetracker.provider;

import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.marvik.api.timetracker.database.StatisticsDatabase;

public class StatisticsProvider extends ContentProvider {
	String TAG = getClass().getSimpleName();
	StatisticsDatabase statisticsDatabase;
	
	private static final String STATISCTICS_PROVIDER_AUTHORITY ="com.marvik.api.timetracker.provider.StatisticsProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://"+STATISCTICS_PROVIDER_AUTHORITY +"/"+ StatisticsDatabase.TIME_SPENT_TABLE);
	private static final int MATCHER_STATISCTICS_DATABASE=1;
	private static final UriMatcher matcher;
	
	static{
		matcher = new UriMatcher(UriMatcher.NO_MATCH);
		matcher.addURI(STATISCTICS_PROVIDER_AUTHORITY, StatisticsDatabase.TIME_SPENT_TABLE, MATCHER_STATISCTICS_DATABASE); //TimeSpentTable
	}
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCreate");
		statisticsDatabase = new StatisticsDatabase(getContext());
		
		return statisticsDatabase!=null;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		Log.i(TAG, "GET TYPE -> " +uri.toString());
		statisticsDatabase.getReadableDatabase();
		
		List<String>pathSegments = uri.getPathSegments();
		if(pathSegments.size()>=2){
			
		}
		switch(matcher.match(uri)){
		case MATCHER_STATISCTICS_DATABASE:
			
			break;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		Log.i(TAG, "INSERT(" +uri.toString()+")");
		statisticsDatabase.getWritableDatabase();
		
		switch(matcher.match(uri)){
		case MATCHER_STATISCTICS_DATABASE:
			statisticsDatabase.getDatabase().insert(StatisticsDatabase.TIME_SPENT_TABLE,null,values);
			break;
		}
		return uri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Log.i(TAG, "QUERY -> "+uri.toString()+"\n"+selection);
		statisticsDatabase.getWritableDatabase();
		
		Cursor cursor = null;
		switch(matcher.match(uri)){
		case MATCHER_STATISCTICS_DATABASE:
			cursor = statisticsDatabase.getDatabase().query(StatisticsDatabase.TIME_SPENT_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		}
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		Log.i(TAG, "UPDATE -> "+uri.toString()+"\n"+selection);
		statisticsDatabase.getWritableDatabase();
		
		int numRows = 0;
		
		switch(matcher.match(uri)){
		case MATCHER_STATISCTICS_DATABASE:
			numRows=statisticsDatabase.getDatabase().update(StatisticsDatabase.TIME_SPENT_TABLE, values, selection, selectionArgs);
			break;
		}
		
		return numRows;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		Log.i(TAG, "DELETE -> "+uri.toString()+"\n"+selection);
		statisticsDatabase.getWritableDatabase();
		
		int numRows = 0;
		
		switch(matcher.match(uri)){
		case MATCHER_STATISCTICS_DATABASE:
			numRows=statisticsDatabase.getDatabase().delete(StatisticsDatabase.TIME_SPENT_TABLE, selection, selectionArgs);
			break;
		}
		
		return numRows;
	}

}
