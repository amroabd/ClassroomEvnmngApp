package com.is.classroomevnmngapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class Permissions {

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

    public static void checkAPPLICATION_SETTING(Context context) {
        try {
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS/*, Uri.parse("package:" + context.getPackageName())*/);
            context.startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}
