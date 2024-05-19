package com.is.classroomevnmngapp.utils;


import android.util.Log;


public class Log1 {
    private static final boolean isDebug=true;
    private static final boolean isDebug1=true;

    public static void i(String tag, String msg) {
        if (isDebug)
        Log.println(Log.INFO, tag, "" + msg);
    }
    public static void v(String tag, String msg) {
         if (isDebug)
            Log.println(Log.VERBOSE, tag, "" + msg);
    }
    public static void w(String tag, String msg) {
        if (isDebug)
        Log.w(tag, "" + msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
        Log.e(tag, "" + msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
        Log.println(Log.DEBUG, tag, "" + msg);
    }

    public static void PrintFileEvent(String tag, String msg) {
      //  if (isDebug1)
       LogManager.getInstance().log_event_app3(tag + " " + msg);
       d(tag,msg);
    }

    public static void PrintFileError(String tag, String msg) {
        //if (isDebug1)
       LogManager.getInstance().log_errors_logs(tag + " " + msg);
       e(tag,msg);
    }

}
