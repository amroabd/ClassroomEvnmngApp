package com.is.classroomevnmngapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;

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

    public void logout(Context context) {
        setDialogCallback(context,new CustomDialog.NoteWithCancelAlertDialogFactory(() -> {
            if (SharePerf.getInstance(context).LogoutUser()) {
                Intent intent = new Intent(context, StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        }), "تسجيل خروج", "هل انت متاكد من تسجيل خروج ");
    }

}
