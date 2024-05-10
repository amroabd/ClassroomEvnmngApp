package com.is.classroomevnmngapp.utils.widget.custom.sweet_alert;


import android.graphics.drawable.Drawable;
import android.view.View;

public class ViewUtils {

   public static Drawable[] getDrawable(View view) {
        Drawable drawable = view.getBackground();
        Drawable.ConstantState dcs =  drawable.getConstantState();
        if (dcs != null) {
            return new Drawable[]{dcs.newDrawable()};
        }
        return null;
    }

}