package com.is.classroomevnmngapp.core.light_control_bt;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.util.Log;

public class ActivityHelper {
    private static final String TAG = "ActivityHelper";
    public static void initialize(Activity activity) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);

        String orientation = prefs.getString("prefOrientation", "Null");
        Log.d(TAG,"orientation :"+orientation);
        if ("Landscape".equals(orientation)) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if ("Portrait".equals(orientation)) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            Log.d(TAG,"SCREEN_ORIENTATION_FULL_SENSOR");
        }
    }
}
