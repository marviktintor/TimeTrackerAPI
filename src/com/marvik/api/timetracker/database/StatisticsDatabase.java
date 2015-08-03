package com.marvik.api.timetracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class StatisticsDatabase {

	// Database
	public static final String DATABASE_NAME = "UsageAnalytics";
	public static final int DATABASE_VERSION = 1;

	// Tables

	// TimeSpentTable
	public static final String TIME_SPENT_TABLE="TimeAnalytics";
	public static final String COL_ID = "c_id";
	public static final String COL_DATE = "c_date";
	public static final String COL_SPENT_TIME_TOTAL = "c_time_total";
	public static final String COL_SPENT_TIME_IDLE = "c_time_idle";
	public static final String COL_SPENT_TIME_BUSY = "c_time_busy";
	public static final String COL_APP_LAUNCHES = "c_launches_counter";

	private Statistics statistics;
	private SQLiteDatabase sqLiteDatabase;
	private Context context;
	private CursorFactory factory;

	public StatisticsDatabase(Context context) {
		this.context = context;
		factory = null;

	}

	public void openDatabase() {
		statistics = new Statistics(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}
	public SQLiteDatabase getDatabase() {
		// TODO Auto-generated method stub
		return sqLiteDatabase;
	}
	public void getReadableDatabase() {
		if (statistics == null) {
			openDatabase();
		}
		sqLiteDatabase = statistics.getReadableDatabase();
	}

	public void getWritableDatabase() {
		if (statistics == null) {
			openDatabase();
		}
		sqLiteDatabase = statistics.getWritableDatabase();
	}

	public void closeDatabase() {
		if (statistics == null) {
			return;
		}
		sqLiteDatabase.close();
		statistics.close();
	}

	private String createTimeSpentTable(){
		return "CREATE TABLE "+TIME_SPENT_TABLE+
				"("+COL_ID+" integer primary key autoincrement," 
				+COL_DATE +" text not null, " 
				+COL_SPENT_TIME_BUSY+" long not null, "
				+COL_SPENT_TIME_IDLE+" long not null, "+
				COL_SPENT_TIME_TOTAL+" long not null, "+
				COL_APP_LAUNCHES+" integer not null); ";
	}
	private String dropTimeSpentTable(){
		return "DROP TABLE "+TIME_SPENT_TABLE;
	}

	private class Statistics extends SQLiteOpenHelper {

		public Statistics(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			createAllTables(db);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			dropAllTables(db);

		}

		private void createAllTables(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(createTimeSpentTable());
		}

		private void dropAllTables(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(dropTimeSpentTable());
		}
	}

	
}