package com.is.classroomevnmngapp.utils.schedule;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.Timer;
import java.util.TimerTask;

public  class TimerSchedules {
    private Handler mHandler;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean mIsWork1;
    private long delay=10000L,period=10000L;

    public void startTimer23() {
        if (mTimer != null) {
            return;
        }
        //-3
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (mIsWork1) stopTimer23();
            }
        };
        //-2
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
             }
        };
        //-1
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, delay, period);
    }

    public void stopTimer23() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public void setIsWork1(boolean mIsWork1) {
        this.mIsWork1 = mIsWork1;
    }




    public static void start(Context context) {

    }
}
