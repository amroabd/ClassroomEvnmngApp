package com.is.classroomevnmngapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

public class BitmapUtils {

    public static Bitmap getBitmapDrawable(Context context, int drawableRes) {
        Bitmap bitmapIcon = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BitmapDrawable bitmapDrawable;
            bitmapDrawable = (BitmapDrawable) context.getDrawable(drawableRes);
            bitmapIcon = bitmapDrawable.getBitmap();
        }
        return bitmapIcon;
    }

}
