package edu.washington.mhd94.arewethereyet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

        private PendingIntent pendingIntent;
        private Intent alarmIntent;
        private static final String TAG = MainActivity.class.getSimpleName();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        /* Retrieve a PendingIntent that will perform a broadcast */
            alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);


        }

        public void setAlarm(View v) {


            EditText text = (EditText) findViewById(R.id.message);
            String message = text.getText().toString();

            EditText numberText = (EditText) findViewById(R.id.number);
            String number = numberText.getText().toString();

            EditText intervalText = (EditText) findViewById(R.id.interval);
            String intervalString = intervalText.getText().toString();

            int interval = 0;
            try {
                interval = Integer.parseInt(intervalString);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please Enter a number", Toast.LENGTH_SHORT);
            }

            alarmIntent.putExtra("message", message);
            alarmIntent.putExtra("number", number);


            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
            Button b = (Button) findViewById(R.id.button);
            if(b.getText().toString().equals("Start")) {
                if(message!= null || number!= null || intervalString!= null) {
                    if (interval > 0 && number.length() == 9) {
                        start(v);
                        b.setText("Stop");
                    }
                } else {
                    Toast.makeText(this, "Make sure all fields have accurate information", Toast.LENGTH_SHORT);
                }
            } else if(b.getText().toString().equals("Stop")) {
                b.setText("Start");
                cancel(v);
            }

        }

        public void start(View v) {

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            EditText intervalText = (EditText) findViewById(R.id.interval);
            int interval;

            String intervalString = intervalText.getText().toString();

            try {
              interval = Integer.parseInt(intervalString);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please Enter a number", Toast.LENGTH_SHORT);
            }
               interval = Integer.parseInt(intervalString) * 1000;

            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
            Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
        }

        public void cancel(View v) {
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            pendingIntent.cancel();
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }


    }