package com.is.classroomevnmngapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.is.classroomevnmngapp.utils.CheckInternetConnection;
import com.is.classroomevnmngapp.utils.SharePerf;
import com.is.classroomevnmngapp.utils.widget.custom.CustomDialog;

import static com.is.classroomevnmngapp.utils.widget.custom.CustomDialog.setDialogCallback;

public class MyApplication extends Application {
    public static  MyApplication instance;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);

    }

    public static MyApplication getInstance() {
        if (instance==null)instance=new MyApplication();
        return instance;
    }

    public static boolean isConnectedNetToast(Context context) {
        if (new CheckInternetConnection(context).isConnectingToInternet()) {
            return true;
        }
        Toast.makeText(context, "لايوجد اتصال ب الانترنت!!", Toast.LENGTH_LONG).show();
        return false;
    }
    public static boolean isNotConnectedNetToast(Context context) {
        if (!(new CheckInternetConnection(context).isConnectingToInternet())) {
            Toast.makeText(context, "لايوجد اتصال ب الانترنت!!", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public static boolean isConnectedNet(Context context) {
        return new CheckInternetConnection(context).isConnectingToInternet();
    }

    public void logout(Context context) {
        setDialogCallback(context,new CustomDialog.NoteWithCancelAlertDialogFactory(() -> {
            if (SharePerf.getInstance(context).LogoutUser()) {
                Intent intent = new Intent(context, StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        }), "تسجيل خروج", "هل انت متاكد من تسجيل خروج ");
    }

    public void popupWindow(View ancherView,Object data,Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popView = layoutInflater.inflate(R.layout.custom_notify, null);
        //---
        TextView textView=popView.findViewById(R.id.tv_content);
        PopupWindow popupWindow = new PopupWindow(popView, ancherView.getWidth(), ancherView.getHeight(), false);
        popupWindow.showAtLocation(ancherView, Gravity.TOP, 0, 0);
        popupWindow.setAnimationStyle(R.animator.shr_next_button_state_list_anim);
         textView.setText(String.format("%s", data));

        popView.setOnClickListener(view -> popupWindow.dismiss());

    }

}
