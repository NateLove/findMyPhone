package com.love.projects.findmyphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.love.projects.findmyphone.globals.Globals;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	private static final String TAG = "FindMyPhone-SMSBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "Intent recieved: " + intent.getAction());

		final SharedPreferences sharedPref = context.getSharedPreferences("lostSharedPreferences", Context.MODE_PRIVATE);

		if (intent.getAction() == SMS_RECEIVED) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				
				for (int i = 0; i < pdus.length; i++) {
					String message  = SmsMessage.createFromPdu((byte[]) pdus[i]).getMessageBody();
					if (message.equals(sharedPref.getString(Globals.getGlobals().VolumeAndAlarmString, " ")) 
							&& sharedPref.getBoolean(Globals.getGlobals().volumeAndAlarmBoolean, false)) {
						Log.i(TAG,"Received Special Message: "
										+ message
										+ " So I will now increase volume to full blast and play alarm");
						maxVolume(context);
						playAlarm(context);
						this.abortBroadcast();
						displayAlert(context);
						
					}
					else if (message.equals(sharedPref.getString(Globals.getGlobals().volumeOnlyString, " "))
								&& sharedPref.getBoolean(Globals.getGlobals().volumeOnlyBoolean, false)) {
						Log.i(TAG,"Received Special Message: "
								+ message
								+ " So I will now increase volume to full blast");
						this.abortBroadcast();
						maxVolume(context);
				
					}
				}
			}
		}
				
				
			
		}

		private void displayAlert(Context context)
	    {
			
			Intent i = new Intent();
	        i.setClassName("com.love.projects.findmyphone", "com.love.projects.findmyphone.NotifySMSReceived");
	        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(i);
	        
	    }
	
	
	void maxVolume(Context context)
	{
		Log.i(TAG, "Setting Volume to the MAX!!11!");
		
		AudioManager manager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		manager.setStreamVolume(
				AudioManager.STREAM_NOTIFICATION, 9,
				AudioManager.FLAG_SHOW_UI);
	}
	
	void playAlarm(Context context)
	{
		Log.i(TAG, "Playing ringtone to the MAX!!11!");

		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		Ringtone r = RingtoneManager.getRingtone(context, notification);
		r.play();
	}

}