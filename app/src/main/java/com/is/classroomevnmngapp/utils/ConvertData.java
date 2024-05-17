package com.is.classroomevnmngapp.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.is.classroomevnmngapp.utils.spinner.ListSpinnerAdapter;


public abstract class ConvertData {


    public static String generateImageName(byte[] paramArrayOfbyte) {
        return (paramArrayOfbyte == null) ? null : Base64.encodeToString(paramArrayOfbyte, 0);
    }

    public static String  resId2String(Context context, int resId){
        return  context.getResources().getString(resId);
    }

    public static int getIdSpinner(ListSpinnerAdapter spinnerAdapter, Spinner spinner){
        return spinnerAdapter.getId(spinner.getSelectedItemPosition());
        //return Math.max(id, -1);
    }
    public static String getIdSpinner_s(ListSpinnerAdapter spinnerAdapter, Spinner spinner){
        return spinnerAdapter.getId_s(spinner.getSelectedItemPosition());
    }

    public static Integer str2Integer(EditText editText1){
        String data=editText1.getText().toString().trim();
        //boolean b= Integer.parseInt(data) >0;
        if (!TextUtils.isEmpty(data))
            if (!data.trim().equals("0") && !data.contains("null")) {
                if (TextUtils.isDigitsOnly(data)) {
                    return Integer.parseInt(data);
                }
            }
        return 0;
    }
    public static Integer str2Integer( String data){
         //data.isEmpty();//Returns true if only if length is 0
        if (!TextUtils.isEmpty(data)) {//Returns true if the string is null or 0-length
            if (TextUtils.isDigitsOnly(data))return Integer.parseInt(data);
        }
        return 0;
    }

    public static Double str2Double(EditText editText1){
        String data=editText1.getText().toString().trim();
        if (!TextUtils.isEmpty(data)){
            return  Double.parseDouble(data);
        }
        return 0.0D;
    }
    public static Float str2Float(EditText editText1){
        String data=editText1.getText().toString().trim();
        if (!TextUtils.isEmpty(data)){
            return  Float.parseFloat(data);
        }
        return 0.0F;
    }
    @NonNull
    public static String to2String(@NonNull TextView editText){
        return editText.getText().toString().trim();
    }
    public static boolean test2String(@NonNull EditText editText, String s2){
        String s1=editText.getText().toString().trim();
        Log1.i("test2String","[st1:"+s1+"],[st2:"+s2);
        return s1.equals(s2);
    }
    public static boolean test2Spinner(@NonNull Spinner spinner, int position_2){
        int p_1=spinner.getSelectedItemPosition();
        Log1.i("test2Spinner",",[sp1: "+p_1+"],[sp2:"+position_2);
        return p_1 == position_2;
    }

    @NonNull
    public static String to2String(String paramString){
        if (paramString != null && paramString.length() > 0 && !paramString.equals("0")) {
            return paramString.contains("null") ? "" : paramString;
        }
        return "";
    }

    @NonNull
    public static String getMemberType(String paramString) {
        int i = getNumber(paramString);
        return (i > 0) ? ((i == 1) ? "رب الاسرة": ((i == 2) ? "مقدم الرعاية": ((i == 3) ? "فرد": ((i == 4) ? "رب الاسرة/ مقدم الرعاية": "")))) : "";
    }
    private static int getNumber(String paramString) {
        int i = (byte) 0;
        if (paramString != null) {
            try {
                if (paramString.length() > 0) {
                    if (!paramString.trim().equals("0")&&!paramString.contains("null")) {
                        if (Integer.parseInt(paramString) > 0)
                            i = Integer.parseInt(paramString);
                    }
                }
                return i;
            } catch (Exception exception) {
                return 0;
            }
        }
        return i;
    }



}
