package com.is.classroomevnmngapp.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

public class HandelRequest {
    private final SampleHandle mHandle;
    private int count;

    public HandelRequest() {
        HandlerThread thread = new HandlerThread("ParseThread", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mHandle = new SampleHandle(thread.getLooper());
        count=0;
    }

    public void start() {
         mHandle.startSampleThread();
         SystemClock.elapsedRealtime();
    }

    public void stop() {
        mHandle.stopSampleThread();
    }


    //////////////////////////////
    private final class SampleHandle extends Handler {
        private static final long SAMPLE_TIME = 2000;
        private static final int MSG_START = 1;

        public SampleHandle(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START:
                    //add action here
                    //
                    sendEmptyMessageDelayed(MSG_START,SAMPLE_TIME);
                    Log.i("Handler","MSG_START"+count+",elapsedRealtime "+SystemClock.elapsedRealtime()
                            +",uptimeMillis "+SystemClock.uptimeMillis());
                    count++;
                    if (count==1000)stopSampleThread();
                    break;
                default:throw new IllegalArgumentException("Unknown what="+msg.what);
            }
        }

        void startSampleThread() {
            sendEmptyMessage(MSG_START);
        }

        void stopSampleThread() {
            removeMessages(MSG_START);
        }

    }

}
