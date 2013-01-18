package com.love.projects.findmyphone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.love.projects.findmyphone.globals.Globals;

public class MainActivity extends Activity {

	protected static final String TAG = "Main Activity";
	private EditText alarmAndVolume;
	private EditText volumeOnly;
	private Switch volumeOnlySwitch;
	private Switch alarmAndVolumeSwitch;
	private SharedPreferences sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sharedPref = this.getSharedPreferences(
				"lostSharedPreferences", Context.MODE_PRIVATE);

		alarmAndVolume = (EditText) findViewById(R.id.alarmAndVolume);
		alarmAndVolume.setText(sharedPref.getString(
				Globals.getGlobals().VolumeAndAlarmString,
				"Default For Alarm and Volume"));
		alarmAndVolume.setEnabled(sharedPref.getBoolean(
						com.love.projects.findmyphone.globals.Globals
								.getGlobals().volumeAndAlarmBoolean, true));

		volumeOnly = (EditText) findViewById(R.id.volume);
		volumeOnly.setText(sharedPref.getString(
				Globals.getGlobals().volumeOnlyString,
				"Default For Volume Only"));
		volumeOnly.setEnabled(sharedPref.getBoolean(
				Globals.getGlobals().volumeOnlyBoolean, true));

		alarmAndVolumeSwitch = (Switch) findViewById(R.id.switch1);
		alarmAndVolumeSwitch
				.setChecked(sharedPref.getBoolean(
						com.love.projects.findmyphone.globals.Globals
								.getGlobals().volumeAndAlarmBoolean, true));
		alarmAndVolumeSwitch
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						Log.i(TAG, "The button AlarmAndVolume is now "
								+ isChecked);
						setSwitchState(alarmAndVolume, alarmAndVolumeSwitch, isChecked, Globals.getGlobals().volumeAndAlarmBoolean);
					
					}
					
					
				});

		volumeOnlySwitch = (Switch) findViewById(R.id.Switch2);
		volumeOnlySwitch.setChecked(sharedPref.getBoolean(
				Globals.getGlobals().volumeOnlyBoolean, true));
		volumeOnlySwitch
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						Log.i(TAG, "The button VolumeOnly is now "
								+ isChecked);
						setSwitchState(volumeOnly, volumeOnlySwitch, isChecked, Globals.getGlobals().volumeOnlyBoolean);

					}
				});

		Button b = (Button) findViewById(R.id.update);

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, alarmAndVolume.getText().toString());
				Log.i(TAG, volumeOnly.getText().toString());
				SharedPreferences.Editor edit = sharedPref.edit();
				edit.putString(Globals.getGlobals().VolumeAndAlarmString,
						alarmAndVolume.getText().toString());
				edit.putString(Globals.getGlobals().volumeOnlyString,
						volumeOnly.getText().toString());
				edit.commit();
				Toast msg = Toast.makeText(MainActivity.this,
						"Messages Updated", Toast.LENGTH_SHORT);
				msg.show();
				Log.i(TAG, sharedPref.getString(
						Globals.getGlobals().volumeOnlyString, "DID NOT SAVE"));
				Log.i(TAG, sharedPref.getString(
						Globals.getGlobals().VolumeAndAlarmString,
						"DID NOT SAVE"));
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	void maxVolume() {
		AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		manager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 9,
				AudioManager.FLAG_SHOW_UI);
	}
	
	void setSwitchState(EditText et, Switch mSwitch, Boolean isSwitched, String sharedPrefsTag)
	{
		SharedPreferences.Editor editor = this.sharedPref.edit();
		editor.putBoolean(sharedPrefsTag, isSwitched);
		editor.commit();
		
		if(isSwitched)
		{
			Log.i(TAG, "Setting switch state to true");
			et.setClickable(true);
			et.setActivated(true);
			et.setEnabled(true);
		}
		else if(!isSwitched)
		{
			Log.i(TAG, "Setting switch state to false");
			et.setClickable(false);
			et.setActivated(false);
			et.setEnabled(false);
		}
	}
}
