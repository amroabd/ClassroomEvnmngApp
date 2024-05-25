package com.is.classroomevnmngapp.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AppPermissions {
    private static final int PERMISSION_REQUESTS_CODE = 1;
    private static final String[] REQUIRED_RUNTIME_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final String TAG = AppPermissions.class.getSimpleName();

    //====================================================//

    public static boolean checkDrawOverlay(Context context) {
        boolean isDraw = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                isDraw = false;
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
            }
        }
        return isDraw;
    }

    public static void checkApplicationSetting(Context context) {
        try {
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS/*, Uri.parse("package:" + context.getPackageName())*/);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: " + permission);
            return true;
        }
        Log.i(TAG, "Permission NOT granted: " + permission);
        return false;
    }

    /**
     * FEATURE_DEVICE_ADMIN,FEATURE_LOCATION_NETWORK
     *,FEATURE_LOCATION_GPS and more...,
     * @param context current context
     * @return status has found feature or false
     */
    public static boolean hasSystemFeature(Context context, String feature) {
        // Check whether your app is running on a device that has a front-facing camera.
        String inFeature = PackageManager.FEATURE_CAMERA_FRONT;
        if (feature != null) inFeature = feature;
        //true : Continue with the part of your app's workflow that requires a front-facing camera.
        //false : Gracefully degrade your app experience.
        return context.getApplicationContext().getPackageManager().hasSystemFeature(inFeature);
    }


}
