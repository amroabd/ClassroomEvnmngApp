package com.is.classroomevnmngapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

public class SharePerf {
    //define
    private final Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static SharePerf mInstance;
    public static final int TYPE_ACCOUNT_ADMIN=1;
    public static final int TYPE_ACCOUNT_USER=2;
    public static final int TYPE_ACCOUNT_DEFAULT=TYPE_ACCOUNT_ADMIN;//0


    //constructor
    private SharePerf(Context mContext) {
        this.mContext = mContext;
    }

    //get instance
    public static SharePerf getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharePerf(context);
        }
        return mInstance;
    }


    /* init data User  */
    public void addUserShare(String usname, String pass, String email,String phone) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("UserFile", Context.MODE_PRIVATE).edit();
        Random random=new Random(100);
        editor.putString("user_id", ""+random.nextInt(99));
        editor.putString("user_name", usname);
        editor.putString("password", pass);
        editor.putString("phone", phone);
        editor.putString("email", email);
        editor.apply();
    }

    public void addUserLogin(String userid,String username,String pass){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("UserFile", Context.MODE_PRIVATE).edit();
        editor.putString("user_id", userid);
        editor.putString("password", pass);
        editor.putString("user_name", username);
        editor.apply();
    }

    public String getUserName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserFile", Context.MODE_PRIVATE);
        return sharedPreferences.contains("user_name") ?sharedPreferences.getString("user_name", null):null;
    }

    public String getUserID() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserFile", Context.MODE_PRIVATE);
        return sharedPreferences.contains("user_id") ?sharedPreferences.getString("user_id", "0"):"0";
    }
    public String getPassword() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserFile", Context.MODE_PRIVATE);
        return sharedPreferences.contains("password") ?sharedPreferences.getString("password", "-"):"-";
    }

    public void addUserID(String id){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("UserFile", Context.MODE_PRIVATE).edit();
        editor.putString("user_id", id);
        editor.apply();
    }


    public boolean isLoggedIn() {
       final SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserFile", Context.MODE_PRIVATE);
        return sharedPreferences.contains("user_id") && !sharedPreferences.getString("user_id", "0").equals("0");
    }
//-----------------------------------------------
    public boolean LogoutUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
      return true;
    }
//------------------------------------------------
    public void addTypeUser(int type){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("TypeUser", Context.MODE_PRIVATE).edit();
        editor.putInt("typeUser", type);
        editor.apply();
    }
    public int getTypeUser(){
       return mContext.getSharedPreferences("TypeUser", Context.MODE_PRIVATE).getInt("typeUser",TYPE_ACCOUNT_DEFAULT);
    }
//---------------------------------------------------
    public void addAddressBT(String device_address){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("saveAddressBTFile", Context.MODE_PRIVATE).edit();
        editor.putString("AddressBT", device_address);
        editor.apply();
    }
    public String getAddressBT(){
        return mContext.getSharedPreferences("saveAddressBTFile", Context.MODE_PRIVATE).getString("AddressBT","0");
    }
//----------------------------------------------------

   public   void addLanguage(String language) {
        SharedPreferences languagePreferences = mContext.getSharedPreferences("language", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = languagePreferences.edit();
        editor.putString("languageCode", language);
        editor.apply();
    }

    public String getLanguageCode() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("language", Context.MODE_PRIVATE);
        return sharedPreferences.contains("languageCode") ? sharedPreferences.getString("languageCode", "default") : "default";
    }

}
