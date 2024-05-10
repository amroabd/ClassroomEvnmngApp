package com.is.classroomevnmngapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

public class ScreenStatus {


    @SuppressLint("NewApi")
    public static  boolean  isScreenOn(@NotNull Context context){
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return powerManager.isInteractive();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static  boolean  isScreenOn0(@NotNull Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getState() != Display.STATE_OFF;
    }

}
