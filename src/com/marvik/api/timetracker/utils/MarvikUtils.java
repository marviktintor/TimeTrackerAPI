package com.marvik.api.timetracker.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;

public class MarvikUtils {
	Context context;

	public MarvikUtils(Context context) {
		this.context = context;
	}
	public String getHumanReadableTime(long systemTime) {
		// TODO Auto-generated method stub
		return new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date(systemTime)).toString();
		
	}
	public String getDate(long currentTimeMillis) {
		// TODO Auto-generated method stub
		return new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(new Date(currentTimeMillis)).toString();
	}

	public String getDateToday() {
		// TODO Auto-generated method stub
		return new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(new Date(System.currentTimeMillis())).toString();
	}

	public String getElapsedTime(long elapsedTime) {
		// TODO Auto-generated method stub
		int seconds = (int) (elapsedTime/1000);
		int minutes = (int)(seconds/60);
		int hours = (int)( minutes/60);
		
		seconds = seconds %60;
		minutes = minutes% 60;
		
		String hour = " hrs ";
		String second = " secs ";
		String minute = " mins ";
		
		if(hours==1){
			hour=" hr ";
		}
		if(minutes==1){
			minute = " min ";
		}
		if(seconds==1){
			second = " sec ";
		}
		
		if(seconds!=0&&(minutes==0&&hours==0)){
			return ""+seconds+second;
		}
		if(minutes==0&&hours==0){
			return ""+seconds+second;
		}
		if(seconds==0&&hours==0){
			return ""+minutes+minute;
		}
		if(hours!=0&&(minutes==0&&seconds==0)){
			return ""+hours+hour;
		}
		if(minutes==0&&(hours!=0&&seconds!=0)){
			return ""+hours+hour+seconds+second;
		}
		if(hours==0){
			return ""+minutes+minute+seconds+second;
		}
		
		
		
		return ""+hours+hour+minutes+minute+seconds+second;
	}

}
