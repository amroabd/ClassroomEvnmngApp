package com.is.classroomevnmngapp.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;




public class LogManager {

    private static final String TAG = LogManager.class.getSimpleName();

    @SuppressLint("NewApi")
    private static final String FILE_NAME = "app_logs";
    private static final String FILE_NAME2 = "errors_logs";
    private static final String FILE_NAME3 = "app_events";
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy_MM_dd__HH");
    FileOutputStream fileOutputStream;
    File logFile;


    public LogManager() {
        createFile();
    }

    public static LogManager getInstance() {
        return new LogManager();
    }

    private void createFile() {
    }

    public void log_errors_logs(String txt){
        String namefile = FILE_NAME2+ "_" + simpleDateFormat1.format(new Date())+".is";
        try {
            File path = new File(GlobalData.F.path_Full_Error_Folder);
            if (!path.exists())
                if (path.mkdirs())Log.i("create folder","logs2");
            logFile = new File(path, namefile);
            log_(logFile,txt);
        } catch (Exception e) { e.printStackTrace();}

    }
    public void log_event_app3(String txt){
        String namefile = FILE_NAME3+ "_" + simpleDateFormat1.format(new Date())+".is";
        try {
            File path = new File(GlobalData.F.path_Full_Event_Folder);
            if (!path.exists())
                if (path.mkdirs())Log.i("create folder ","logs3");
            logFile = new File(path, namefile);
            log_(logFile,txt);
        } catch (Exception e) { e.printStackTrace();}

    }

    private void log_(File logFile, String text) {
        String log =   " \n =======" + DateUtils.getCurrentDTime()+"======\n"+text ;
        try {
            fileOutputStream = new FileOutputStream(logFile, true);
            fileOutputStream.write(Objects.requireNonNull(System.getProperty("line.separator")).getBytes());
            fileOutputStream.write(log.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void log(String text) {
        String log =  text + "  " + DateUtils.getCurrentDTime() ;
        try {
            fileOutputStream = new FileOutputStream(logFile, true);
            fileOutputStream.write(Objects.requireNonNull(System.getProperty("line.separator")).getBytes());
            fileOutputStream.write(log.getBytes());
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printLogs() {
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(logFile));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, text.toString());
    }
    public void deleteLog() {
        Log.d(TAG, "Log File deleted: " + logFile.delete());
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }



}