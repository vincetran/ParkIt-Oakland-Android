package edu.pitt.designs1635.ParkIt;

import kankan.wheel.R;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Timer extends Activity
{
	//private Spinner spinner, spinner2, spinner3;
	private int timerVal;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

		final WheelView meterHours = (WheelView) findViewById(R.id.meter_hour);
		meterHours.setViewAdapter(new NumericWheelAdapter(this, 0, 23));
	
		
		final WheelView meterMins = (WheelView) findViewById(R.id.meter_mins);
		meterMins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		meterMins.setCyclic(true);

		
		final WheelView alarmHours = (WheelView) findViewById(R.id.alarm_hour);
		alarmHours.setViewAdapter(new NumericWheelAdapter(this, 0, 23));
	
		final WheelView alarmMins = (WheelView) findViewById(R.id.alarm_mins);
		alarmMins.setViewAdapter(new NumericWheelAdapter(this, 0, 59, "%02d"));
		alarmMins.setCyclic(true);
		
		/*
		OnWheelClickedListener click = new OnWheelClickedListener() {
            public void onItemClicked(WheelView wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex, true);
                Log.i("Timer Activity", wheel.getTag() + " / " + itemIndex);
            }
        };
        */
		
		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				
			}
			
			public void onScrollingFinished(WheelView wheel) {
				
				boolean incorrectAlarm = false;
				
				if(alarmHours.getCurrentItem() > meterHours.getCurrentItem())
				{
					alarmHours.setCurrentItem(meterHours.getCurrentItem(), true);
					incorrectAlarm = true;
				}
				if(alarmHours.getCurrentItem() >= meterHours.getCurrentItem() &&
				   alarmMins.getCurrentItem() > meterMins.getCurrentItem())
				{
					alarmMins.setCurrentItem(meterMins.getCurrentItem(), true);
					incorrectAlarm = true;
				}
				
				if (incorrectAlarm)
					Toast.makeText(getApplicationContext(), "Alarm cannot be greater than the time on the meter.", Toast.LENGTH_SHORT).show();
					
				
			}
		};
		
		
		
        meterHours.setTag("Meter Hours");
        meterMins.setTag("Meter Mins");
        alarmHours.setTag("Alarm Hours");
        alarmMins.setTag("Alarm Mins");
		
        //meterHours.addClickingListener(click);
        //meterMins.addClickingListener(click);
        //alarmHours.addClickingListener(click);
        //alarmMins.addClickingListener(click);
		
        meterHours.addScrollingListener(scrollListener);
        meterMins.addScrollingListener(scrollListener);
        alarmHours.addScrollingListener(scrollListener);
        alarmMins.addScrollingListener(scrollListener);
        
        
		Button cancel = (Button) findViewById(R.id.alarmCancelButton);
        Button start = (Button) findViewById(R.id.alarmStartButton);
	
        cancel.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
        
        start.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				Log.i("Timer Activity", "Meter: " + meterHours.getCurrentItem() + ":"
						+ meterMins.getCurrentItem() + "  -- Alarm: " + 
						alarmHours.getCurrentItem() + ":"
						+ alarmMins.getCurrentItem());
			}
		});
    
    }
	
	
	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
	    	case android.R.id.home:
	    		Intent intent = new Intent(this, ParkItActivity.class);
	    		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    		startActivity(intent);
	    		return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
	    		
	    	}
	    }
}