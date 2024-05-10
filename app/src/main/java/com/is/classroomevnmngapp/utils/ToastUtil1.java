package com.is.classroomevnmngapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.is.classroomevnmngapp.R;


public class ToastUtil1 {
    static Toast toast;

    @SuppressLint("InflateParams")
    public static void showToast(Context context, String msg) {

        toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
        TextView textView = view.findViewById(R.id.toastText);
        textView.setText(msg);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,100);

        toast.show();
    }


    public static void showToastFail(Context context, String msg) {
        toast = new Toast(context);
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast_fail, null);
        TextView textView = view.findViewById(R.id.toastText);
        textView.setText(msg);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

}
