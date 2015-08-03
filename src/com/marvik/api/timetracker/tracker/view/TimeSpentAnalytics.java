package com.marvik.api.timetracker.tracker.view;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.marvik.api.timetracker.R;
import com.marvik.api.timetracker.activity.TimeTrackedActivity;
import com.marvik.api.timetracker.database.StatisticsDatabase;
import com.marvik.api.timetracker.provider.StatisticsProvider;
import com.marvik.api.timetracker.utils.MarvikUtils;
/** 
@author victor_mwenda
  
<p>Activity to show all Time spent on the app analytics</p>

You <b><em>SHOULD NOT</em></b> set a contentView when you extend this class.
It uses the library layout.

Example
<pre>
public class MyActivity extends TimeSpentAnalytics{

@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	//setContentView(R.layout.main);
	}
}
</pre>
 
 */
public class TimeSpentAnalytics extends TimeTrackedActivity {

	List<TimeAnalytics> iTimeSpent;
	MarvikUtils marvikUtils;
	ListView lvTimeSpentAnalytics;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analytics_timespent);
		
		marvikUtils = new MarvikUtils(this);
		lvTimeSpentAnalytics = (ListView)findViewById(R.id.analytics_timespent_listView_statistics);
		readTimeSpentStatistics();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	private void readTimeSpentStatistics() {
		// TODO Auto-generated method stub
		
		iTimeSpent = new ArrayList<TimeSpentAnalytics.TimeAnalytics>();
		long timeSpentIdle,timeSpentBusy,timeSpentTotal;String date;
		int launches;
		Cursor cursor = getContentResolver().query(StatisticsProvider.CONTENT_URI, null, null, null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			date = cursor.getString(cursor.getColumnIndex(StatisticsDatabase.COL_DATE));
			timeSpentIdle = cursor.getLong(cursor.getColumnIndex(StatisticsDatabase.COL_SPENT_TIME_IDLE));
			timeSpentBusy = cursor.getLong(cursor.getColumnIndex(StatisticsDatabase.COL_SPENT_TIME_BUSY));
			timeSpentTotal = cursor.getLong(cursor.getColumnIndex(StatisticsDatabase.COL_SPENT_TIME_TOTAL));
			launches = cursor.getInt(cursor.getColumnIndex(StatisticsDatabase.COL_APP_LAUNCHES));
			iTimeSpent.add(new TimeAnalytics(date, timeSpentIdle, timeSpentBusy, timeSpentTotal, launches));

		}
		
		lvTimeSpentAnalytics.setAdapter(new TimeAnalyticsAdapter());
	}

	class TimeAnalyticsAdapter extends ArrayAdapter<TimeAnalytics> {
		TimeAnalyticsAdapter() {
			super(TimeSpentAnalytics.this, R.layout.analytics_timespent_ui,
					iTimeSpent);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View analyticalView = convertView;
			
			if(analyticalView==null){
				analyticalView = getLayoutInflater().inflate(R.layout.analytics_timespent_ui, parent, false);
			}
			
			TimeAnalytics timeAnalytics  = iTimeSpent.get(position);
			
			TextView tvDate = (TextView)analyticalView.findViewById(R.id.analytics_timespent_ui_textView_date_value);
			TextView tvTimeIdle = (TextView)analyticalView.findViewById(R.id.analytics_timespent_ui_textView_time_spent_idle_value);
			TextView tvTimeBusy = (TextView)analyticalView.findViewById(R.id.analytics_timespent_ui_textView_time_spent_busy_value);
			TextView tvTimeTotal = (TextView)analyticalView.findViewById(R.id.analytics_timespent_ui_textView_time_spent_total_value);
			TextView tvLaunches = (TextView)analyticalView.findViewById(R.id.analytics_timespent_ui_textView_launches_value);
			
			tvDate.setText(timeAnalytics.getDate());
			tvTimeIdle.setText(marvikUtils.getElapsedTime(timeAnalytics.getTimeSpentIdle()));
			tvTimeBusy.setText(marvikUtils.getElapsedTime(timeAnalytics.getTimeSpentBusy()));
			tvTimeTotal.setText(marvikUtils.getElapsedTime(timeAnalytics.getTimeSpentTotal()));
			tvLaunches.setText(String.format("%d", timeAnalytics.getLaunches()));
			
			return analyticalView;
		}
	}

	class TimeAnalytics {

		long timeSpentIdle,timeSpentBusy,timeSpentTotal;
		String date;
		int launches;
		public TimeAnalytics(String date,long timeSpentIdle, long timeSpentBusy,
				long timeSpentTotal,  int launches) {
			super();
			this.timeSpentIdle = timeSpentIdle;
			this.timeSpentBusy = timeSpentBusy;
			this.timeSpentTotal = timeSpentTotal;
			this.date = date;
			this.launches = launches;
		}
		public long getTimeSpentIdle() {
			return timeSpentIdle;
		}
		public void setTimeSpentIdle(long timeSpentIdle) {
			this.timeSpentIdle = timeSpentIdle;
		}
		public long getTimeSpentBusy() {
			return timeSpentBusy;
		}
		public void setTimeSpentBusy(long timeSpentBusy) {
			this.timeSpentBusy = timeSpentBusy;
		}
		public long getTimeSpentTotal() {
			return timeSpentTotal;
		}
		public void setTimeSpentTotal(long timeSpentTotal) {
			this.timeSpentTotal = timeSpentTotal;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public int getLaunches() {
			return launches;
		}
		public void setLaunches(int launches) {
			this.launches = launches;
		}
		
	}

}
