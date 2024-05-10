package com.is.classroomevnmngapp.utils;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.is.classroomevnmngapp.R;

import org.jetbrains.annotations.NotNull;

public class AnimationUtil {
    private static final String TAG = "AnimationUtil";
    private static final int CLOUD_TRANSLATION_DISTANCE = 100;
    static int ANIMATION_DURATION = 3000;

        public static void animateBird(@NotNull Activity context, @NotNull View view) {
            view.setX(-200.0F);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            view.animate().x((displayMetrics.widthPixels + 50))
                    .setStartDelay(1000L)
                    .setDuration(ANIMATION_DURATION)
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator param1Animator) {
                            animateBird(context,view);
                            Log1.d(TAG,"animateBird->onAnimationEnd");
                        }
                    }).start();
        }

        public static void animateSun(Context context, View view) {
            AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.sun_swing);
            animatorSet.setTarget(view);
            animatorSet.start();
        }

        public static void animateWheel(Context context, View view) {
            AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.wheel_spin);
            animatorSet.setTarget(view);
            animatorSet.start();
        }

        public static void darkenSky( View view) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(
                   view, "backgroundColor",
                     Color.parseColor("#03A9F4"), Color.parseColor("#7c4dff"));
            objectAnimator.setDuration(ANIMATION_DURATION);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator.setEvaluator(new ArgbEvaluator());
            objectAnimator.start();
        }


        public static void moveClouds(View view) {
            @SuppressLint({"NewApi", "LocalSuppress"})
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y,  CLOUD_TRANSLATION_DISTANCE,0.0F);
            objectAnimator1.setDuration(ANIMATION_DURATION);
            //objectAnimator1.setRepeatCount(0);
            //objectAnimator1.setRepeatMode(ValueAnimator.REVERSE);
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, View.ROTATION, 0.0F, 360.0F);
            objectAnimator2.setDuration(ANIMATION_DURATION);
            //objectAnimator2.setRepeatCount(0);
            //objectAnimator2.setRepeatMode(ValueAnimator.RESTART);

            animatorSet.playTogether(objectAnimator1, objectAnimator2);
            animatorSet.start();
        }

}
