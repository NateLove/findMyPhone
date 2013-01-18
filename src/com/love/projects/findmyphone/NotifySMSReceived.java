package com.love.projects.findmyphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

public class NotifySMSReceived extends Activity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       displayAlert();
    }

    private void displayAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Phone Finder to the Rescue!!").setCancelable(
                false).setPositiveButton("Dismiss",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    	Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                		Ringtone r = RingtoneManager.getRingtone(NotifySMSReceived.this, notification);
                		r.stop();
                		android.os.Process.killProcess(android.os.Process.myPid());
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}