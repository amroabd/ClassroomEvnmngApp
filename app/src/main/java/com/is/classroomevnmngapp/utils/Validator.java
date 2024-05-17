package com.is.classroomevnmngapp.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.is.classroomevnmngapp.R;


public class Validator {

    //take cpu lock to prevent cpu from off  if user presses the
    // power ,permission <uses-permission android :name="android.permission.WAKE_LOCK"/>
    public void preventCpuOff(@NonNull Context context){
        PowerManager.WakeLock  mWakeLock;
        PowerManager mPm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock=mPm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,getClass().getName());
        mWakeLock.acquire();
        //end
        mWakeLock.release();

    }

    public static boolean emptySpinner(Spinner paramSpinner, LinearLayout linearLayout) {
        if (paramSpinner != null && paramSpinner.getSelectedItemPosition() <= 0)
            try {
                if (paramSpinner.getSelectedView() != null)
                  linearLayout.setBackgroundResource(R.drawable.layout_red_for_not_select);
                paramSpinner.setFocusable(true);
                paramSpinner.setFocusableInTouchMode(true);
                paramSpinner.requestFocus();
                Log1.w("emptySpinner","true");
                return true;
            } catch (Exception exception) {
                return true;
            }
        linearLayout.setBackgroundResource(R.drawable.layout_text);
        if (paramSpinner != null) {
            paramSpinner.clearFocus();
            paramSpinner.setFocusable(false);
        }  Log1.w("emptySpinner","false");
        return false;
    }
    ///
    public static boolean emptySpinner(Spinner paramSpinner) {
        //String s=((TextView)paramSpinner.getSelectedView()).getText().toString();
        if (paramSpinner != null && paramSpinner.getSelectedItemPosition() <= 0)
            try {
                if (paramSpinner.getSelectedView() != null)
                    ((TextView)paramSpinner.getSelectedView()).setTextColor(Color.parseColor("#F27474"));
                paramSpinner.setFocusable(true);
                paramSpinner.setFocusableInTouchMode(true);
                paramSpinner.requestFocus();
                return true;
            } catch (Exception exception) {
                return true;
            }
        if (paramSpinner != null) {
            paramSpinner.clearFocus();
            paramSpinner.setFocusable(false);
        }
        return false;
    }
    public static boolean emptyEditText(EditText paramEditText, String msgNote) {
         String msg_d="must input data field!";
        if (TextUtils.isEmpty(paramEditText.getText().toString())) {
            paramEditText.setError(msgNote==null?msg_d:msgNote);
            paramEditText.setFocusableInTouchMode(true);
            paramEditText.requestFocus();
            Log.w("emptyEditText","true");
            return true;
        }
        Log.w("emptyEditText","false");
        paramEditText.setError(null);
        paramEditText.clearFocus();
        return false;
    }
    public static boolean isNotEmptyEditText(@NonNull TextView paramEditText, String msgNote) {
        String msg_d="Require input data field.!";
        if (TextUtils.isEmpty(paramEditText.getText().toString())) {
            paramEditText.setError(msgNote==null?msg_d:msgNote);
            paramEditText.setFocusableInTouchMode(true);
            paramEditText.requestFocus();
            Log.w("isNotEmptyEditText","false");
            return false;
        }
        Log.w("isNotEmptyEditText","true");
        paramEditText.setError(null);
        paramEditText.clearFocus();
        return true;
    }

    // A placeholder username validation check
    public static boolean isUserNameValid(EditText editText) {
        String username=ConvertData.to2String(editText);
        if (username.length() <= 0) {
            editText.setError(editText.getContext().getString(R.string.invalid_username));
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        editText.clearFocus();
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }
    public static void testPhoneEditText(EditText paramEditText) {
        String msg_d="must input data field!";
        String phone=paramEditText.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            paramEditText.setError(msg_d);
            //paramEditText.setFocusableInTouchMode(true);
            //paramEditText.requestFocus();
            Log1.w("emptyEditText","true");
            return;
        }
        if (phone.length()>1)
        if (phone.startsWith("71")||phone.startsWith("73")||phone.startsWith("77")){
           // paramEditText.setTranslationY(12);
            if (phone.length()<9) {
                paramEditText.setError("must input number Phone is equal 9 number .!!");
                return;
            }
            paramEditText.setError(null);
        }else {
            paramEditText.setError("must start number Phone [71 Or 73 or 77]");
        }
        //String first_number=phone.startsWith("71")
        Log1.w("emptyEditText","false");
    }


    public static boolean emptyTextView(TextView paramTextView, String msgNote) {
        if (TextUtils.isEmpty(paramTextView.getText().toString())) {
            paramTextView.setError(msgNote);
            paramTextView.setFocusableInTouchMode(true);
            paramTextView.requestFocus();
            return true;
        }
        paramTextView.setError(null);
        paramTextView.clearFocus();
        return false;
    }

    public interface OnValidateViews{
        default void onSpinnerValid(){}
        default void onEditValid(){}
        default void onHidOrShowLayout(){}
        boolean onValidInput(int role);
        default void onSure(){}
        void onFollow();
        default boolean onValidPart(int role){return false;}
        default void onHidLayout_all(){}
        default void confirm2Spinner(){}
        default void confirm2Edit(){}
    }

}
