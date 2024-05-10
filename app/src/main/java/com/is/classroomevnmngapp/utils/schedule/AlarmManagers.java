package com.is.classroomevnmngapp.utils.schedule;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.is.classroomevnmngapp.utils.Log1;

import java.util.Calendar;

/****
 *
 */
// FIXME: 22/03/2023 @Dev:Amro@Alkamali
public class AlarmManagers extends AppCompatActivity {

    private NotificationManager mNotificationManager;
    ToggleButton alarmToggle;
    Intent notifyIntent;
    private PendingIntent notifyPendingIntent;
    private AlarmManager alarmManager;

    private static final int NOTIFICATION_ID = 1;
    private static final String PRIMARY_CHANNEL_ID = "primary_notify_channel";
    private static final String TAG = "Lord";


    private void repeatOnceDay(int atHour){
        //notifyIntent = new Intent(this, AlarmFPReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, atHour);
        long repeatInterval = AlarmManager.INTERVAL_DAY;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeatInterval, notifyPendingIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_alarm);
        Log1.d(TAG, "onCreate: Started");

        //initialize mNotificationManager using getSystemService().
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //alarmToggle = findViewById(R.id.alarmToggle);

        //createNotificationChannel();

        //notifyIntent = new Intent(this, AlarmFPReceiver.class);
        //To check if the alarm is on or not. and updating the toggle button.
        boolean alarmUp = ifAlarmOn();
        alarmToggle.setChecked(alarmUp);

        notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toastMassage;

                if (isChecked) {
                    Log1.d(TAG, "onCheckedChanged: IsChecked");
                    toastMassage = "Stand Up Alarm On!";

                    ////If the Toggle is turned on, set the repeating alarm with a 1.5 minute interval
                    long repeatInterval1=100000L;
                    long repeatInterval = AlarmManager.INTERVAL_HALF_HOUR;
                    long triggerTime = SystemClock.elapsedRealtime() + repeatInterval1;
                    if (alarmManager != null) {
                        Log1.d(TAG, "onCheckedChanged: AlarmManager!=null - Start of alarm manager t:"+triggerTime);
                        //schedule alarm
                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval1, notifyPendingIntent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            calendar.set(Calendar.HOUR_OF_DAY, 8);
                            calendar.set(Calendar.MINUTE, 30);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),notifyPendingIntent);
                        }
                    }
                } else {
                    Log1.d(TAG, "onCheckedChanged: IsNotChecked");
                    toastMassage = "Stand Up Alarm Off!";
                    alarmManager.cancel(notifyPendingIntent);
                    mNotificationManager.cancelAll();
                }
                //Show a toast to say the alarm is turned on or off.
                Toast.makeText(AlarmManagers.this, toastMassage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean ifAlarmOn(){
        return  (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
    }

    private void checkDrawOverlay(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (!Settings.canDrawOverlays(getApplicationContext())){
                Intent intent=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));
                startActivity(intent);
            }
        }
    }
    /**
     * Creates a Notification channel, for OREO and higher.
     */
    private void createNotificationChannel() {
        Log1.d(TAG, "createNotificationChannel: MainActivity");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Signed Fp  Notification", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");

            mNotificationManager.createNotificationChannel(notificationChannel);
        }}




}
