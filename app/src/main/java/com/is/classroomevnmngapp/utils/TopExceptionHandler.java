package com.is.classroomevnmngapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;




public class TopExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Thread.UncaughtExceptionHandler defaultUEH;
    @SuppressLint("StaticFieldLeak")
    private static Activity app;
    static FileOutputStream trace;

    //


    public TopExceptionHandler(Context app1) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        app = (Activity) app1;


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public  void uncaughtException(Thread t, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        StringBuilder report = new StringBuilder(e.toString() + "\n\n");
        report.append("--------- Stack trace ---------\n\n");

        for (StackTraceElement stackTraceElement : arr) {
            report.append("    ").append(stackTraceElement.toString()).append("\n");
        }
        report.append("-------------------------------\n\n");
        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        report.append("--------- Cause ---------\n\n");
        Throwable cause = e.getCause();
        if(cause != null) {
            report.append(cause.toString()).append("\n\n");
            arr = cause.getStackTrace();
            for (StackTraceElement stackTraceElement : arr) {
                report.append("    ").append(stackTraceElement.toString()).append("\n");
            }
        }
        report.append("----------------------\n\n Date:").append(DateUtils.getCurrentDTime());
        try {
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(app.openFileOutput("stack.trace",Context.MODE_PRIVATE)));
             trace = app.openFileOutput("stack.trace", Context.MODE_PRIVATE);
            trace.write(report.toString().getBytes());
            trace.close();
            //save crash in file
            new LogManager().log_errors_logs("Rep:"+ report);
                // sendEmialCrash();
        } catch(Exception ioe) {
            // ...
        }

        defaultUEH.uncaughtException(t, e);
    }


   /*
   fun send email to developer report Error
    */
    public static void sendEmialCrash() throws URISyntaxException {

        StringBuilder trace1 = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(app.openFileInput("stack.trace")));
            String line;
            while((line = reader.readLine()) != null) {
                trace1.append(line).append("\n");
            }
        } catch(Exception fnfe) {
            // ...
        }


        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        String subject = "Error report";
        String body = "Mail this to alkamaliamro@gmail.com: " + "\n" + trace1 + "\n";

        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"alkamaliamro@gmail.com"});
        sendIntent.putExtra(Intent.EXTRA_TEXT, body);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
      //  sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath() ));
        sendIntent.setType("message/rfc822");

        app.startActivity(sendIntent);

       // app.deleteFile("stack.trace");

    }
}