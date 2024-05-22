package com.is.classroomevnmngapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class CheckInternetConnection {
    private final Context context;

    public CheckInternetConnection(Context paramContext) {
        this.context = paramContext;
    }

    public boolean isConnectingToInternet() {
        @SuppressLint("WrongConstant")
        ConnectivityManager connectivityManager = (ConnectivityManager)this.context.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
      /*  assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(0).getState()==NetworkInfo.State.CONNECTED
                ||connectivityManager.getNetworkInfo(0).getState()==NetworkInfo.State.CONNECTING){
       return true;
        }*/
        return false;
    }
}

