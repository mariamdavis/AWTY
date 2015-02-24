package edu.washington.mhd94.arewethereyet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by mariam on 2/21/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private String message = "I'm Running";
    private String number = "";


    @Override
    public void onReceive(Context context, Intent intent) {
        // For our recurring task, we'll just display a message
         message = intent.getStringExtra("message");
         number = intent.getStringExtra("number");
         number = ("(" + number.substring(0,3) + ") " + number.substring(3,6) +"-"+ number.substring(6));
       //  String subNum = "(" + number.substring(0,3) + ")" + "-" +  number.substring(3,7) +
                // number.substring(7,9);

        Toast.makeText(context, number + ": " + message, Toast.LENGTH_SHORT).show();


    }

}