package com.is.classroomevnmngapp.utils.widget.custom.sweet_alert;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.is.classroomevnmngapp.R;

import java.util.List;

public class SweetAlertDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = SweetAlertDialog.class.getSimpleName();
    private View mDialogView;
    private final AnimationSet mModalInAnim;
    private final AnimationSet mModalOutAnim;
    private final Animation mOverlayOutAnim;
    private final Animation mErrorInAnim;
    private final AnimationSet mErrorXInAnim;
    private final AnimationSet mSuccessLayoutAnimSet;
    private final Animation mSuccessBowAnim;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private FrameLayout mCustomViewContainer;
    private View mCustomView;
    private String mTitleText;
    private String mContentText;
    private boolean mShowCancel;
    private boolean mShowContent;
    private String mCancelText;
    private String mConfirmText;
    private String mNeutralText;
    private int mAlertType;
    private FrameLayout mErrorFrame;
    private FrameLayout mSuccessFrame;
    private FrameLayout mProgressFrame;

    private SuccessTickView0 mSuccessTick;
    private ImageView mErrorX;
    private View mSuccessLeftMask;
    private View mSuccessRightMask;
    private Drawable mCustomImgDrawable;
    private ImageView mCustomImage;
    private LinearLayout mButtonsContainer;
    private Button mConfirmButton;
    private boolean mHideConfirmButton = false;
    private Button mCancelButton;
    private Button mNeutralButton;
    private Integer mConfirmButtonBackgroundColor;
    private Integer mConfirmButtonTextColor;
    private Integer mNeutralButtonBackgroundColor;
    private Integer mNeutralButtonTextColor;
    private Integer mCancelButtonBackgroundColor;
    private Integer mCancelButtonTextColor;
    private final ProgressHelper mProgressHelper;
    private FrameLayout mWarningFrame;
    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;
    private OnSweetClickListener mNeutralClickListener;
    private boolean mCloseFromCancel;
    private boolean mHideKeyBoardOnDismiss = true;
    private int contentTextSize = 0;

    public static final int NORMAL_TYPE = 0;
    public static final int ERROR_TYPE = 1;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    public static final int CUSTOM_IMAGE_TYPE = 4;
    public static final int PROGRESS_TYPE = 5;


    public static boolean DARK_STYLE = false;

    //aliases
    public final static int BUTTON_CONFIRM = DialogInterface.BUTTON_POSITIVE;
    public final static int BUTTON_CANCEL = DialogInterface.BUTTON_NEGATIVE;

    private final float defStrokeWidth;
    private float strokeWidth = 0;


    public SweetAlertDialog hideConfirmButton() {
        this.mHideConfirmButton = true;
        return this;
    }

    public interface OnSweetClickListener {
        void onClick(SweetAlertDialog sweetAlertDialog);
    }

    public SweetAlertDialog(Context context) {
        this(context, NORMAL_TYPE);
    }


    @SuppressLint("ObsoleteSdkInt")
    public SweetAlertDialog(Context context, int alertType) {
        super(context, DARK_STYLE ? R.style.alert_dialog_dark1 : R.style.alert_dialog_light1);
        setCancelable(true);
        setCanceledOnTouchOutside(true); //TODO was false

        defStrokeWidth = getContext().getResources().getDimension(R.dimen.buttons_stroke_width);
        strokeWidth = defStrokeWidth;

        mProgressHelper = new ProgressHelper(context);
        mAlertType = alertType;
        mErrorInAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.error_x_in);
        // 2.3.x system don't support alpha-animation on layer-list drawable
        // remove it from animation set
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            List<Animation> childAnims = mErrorXInAnim.getAnimations();
            int idx = 0;
            for (; idx < childAnims.size(); idx++) {
                if (childAnims.get(idx) instanceof AlphaAnimation) {
                    break;
                }
            }
            if (idx < childAnims.size()) {
                childAnims.remove(idx);
            }
        }
        mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.success_bow_roate);
        mSuccessLayoutAnimSet = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.success_mask_layout);
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                if (mHideKeyBoardOnDismiss) {
                    hideSoftKeyboard();
                }
                mDialogView.post(() -> {
                    if (mCloseFromCancel) {
                       SweetAlertDialog.super.cancel();
                    } else {
                        SweetAlertDialog.super.dismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // dialog overlay fade out
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sweet_alert_dialog);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTitleTextView = findViewById(R.id.title_text);
        mContentTextView = findViewById(R.id.content_text);
        mCustomViewContainer = findViewById(R.id.custom_view_container);
        mErrorFrame = findViewById(R.id.error_frame);
        mErrorX = mErrorFrame.findViewById(R.id.error_x);
        mSuccessFrame = findViewById(R.id.success_frame);
        mProgressFrame = findViewById(R.id.progress_dialog);

       try {
            mSuccessTick = mSuccessFrame.findViewById(R.id.success_tick);
        }catch (Exception e) {
           e.printStackTrace();
       }
        mSuccessLeftMask = mSuccessFrame.findViewById(R.id.mask_left);
        mSuccessRightMask = mSuccessFrame.findViewById(R.id.mask_right);
        if (mSuccessTick!=null)
        Log.i(TAG+">.onCreate",",mSuccess:"+mSuccessTick.hashCode());
        mCustomImage = findViewById(R.id.custom_image);
        mWarningFrame = findViewById(R.id.warning_frame);
        mButtonsContainer = findViewById(R.id.buttons_container);
        mConfirmButton = findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(this);
        mConfirmButton.setOnTouchListener(Constants.FOCUS_TOUCH_LISTENER);
        mCancelButton = findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(this);
        mCancelButton.setOnTouchListener(Constants.FOCUS_TOUCH_LISTENER);
        mNeutralButton = findViewById(R.id.neutral_button);
        mNeutralButton.setOnClickListener(this);
        mNeutralButton.setOnTouchListener(Constants.FOCUS_TOUCH_LISTENER);
        mProgressHelper.setProgressWheel(findViewById(R.id.progressWheel));

        setTitleText(mTitleText);
        setContentText(mContentText);
        setCustomView(mCustomView);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);
        setNeutralText(mNeutralText);
        applyStroke();

       // setConfirmButtonBackgroundColor(mConfirmButtonBackgroundColor);
        //setConfirmButtonTextColor(mConfirmButtonTextColor);
        //setCancelButtonBackgroundColor(mCancelButtonBackgroundColor);
        //setCancelButtonTextColor(mCancelButtonTextColor);
        //setNeutralButtonBackgroundColor(mNeutralButtonBackgroundColor);
        setNeutralButtonTextColor(mNeutralButtonTextColor);
        changeAlertType(mAlertType, true);

    }

    /***
     *  restore all of views state before switching alert type
     *  restore status views (anim,show ,..)
     */
    private void restore() {
        mCustomImage.setVisibility(View.GONE);
        mErrorFrame.setVisibility(View.GONE);
        mSuccessFrame.setVisibility(View.GONE);
        mWarningFrame.setVisibility(View.GONE);
        mProgressFrame.setVisibility(View.GONE);

        mCancelButton.setVisibility(View.GONE);

        mConfirmButton.setVisibility(mHideConfirmButton ? View.GONE : View.VISIBLE);

        adjustButtonContainerVisibility();

       // mConfirmButton.setBackgroundResource(R.drawable.is_btn_bg_green);
        mErrorFrame.clearAnimation();
        mErrorX.clearAnimation();
        try{
         // if (mSuccessTick != null)
            mSuccessTick.clearAnimation();
            mSuccessLeftMask.clearAnimation();
            mSuccessRightMask.clearAnimation();
        }catch (Exception e){
            Log.e(TAG+">>Error_restore",e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Hides buttons container if all buttons are invisible or gone.
     * This deletes useless margins
     */
    private void adjustButtonContainerVisibility() {
        boolean showButtonsContainer = false;
        for (int i = 0; i < mButtonsContainer.getChildCount(); i++) {
            View view = mButtonsContainer.getChildAt(i);
            if (view instanceof Button && view.getVisibility() == View.VISIBLE) {
                showButtonsContainer = true;
                break;
            }
        }
        mButtonsContainer.setVisibility(showButtonsContainer ? View.VISIBLE : View.GONE);
    }

    private void playAnimation() {
        if (mAlertType == ERROR_TYPE) {
            mErrorFrame.startAnimation(mErrorInAnim);
            mErrorX.startAnimation(mErrorXInAnim);
        } else if (mAlertType == SUCCESS_TYPE) {
           try {
                mSuccessTick.startTickAnim();
            }catch (Exception e){
               Log.e(TAG+">>Error_playAnim",e.toString());
           }
            mSuccessRightMask.startAnimation(mSuccessBowAnim);
        }
    }

    private void changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;
        // call after created views
        if (mDialogView != null) {
            if (!fromCreate) {
                Log.i(TAG+">.restore","changeAlertType");
                // restore all of views state before switching alert type
                restore();
            }
            mConfirmButton.setVisibility(mHideConfirmButton ? View.GONE : View.VISIBLE);
            switch (mAlertType) {
                case ERROR_TYPE:
                    mErrorFrame.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS_TYPE:
                    Log.i(TAG+">.success","changeAlertType");
                    mSuccessFrame.setVisibility(View.VISIBLE);
                    // initial rotate layout of success mask
                    mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
                    mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
                    break;
                case WARNING_TYPE:
//        mConfirmButton.setBackgroundResource(R.drawable.red_button_background);
                    mWarningFrame.setVisibility(View.VISIBLE);
                    break;
                case CUSTOM_IMAGE_TYPE:
                    setCustomImage(mCustomImgDrawable);
                    break;
                case PROGRESS_TYPE:
                    mProgressFrame.setVisibility(View.VISIBLE);
                    mConfirmButton.setVisibility(View.GONE);
//                    mButtonsContainer.setVisibility(View.GONE);
                    break;
            }
            adjustButtonContainerVisibility();
            if (!fromCreate) {
                Log.i(TAG+">.playAnimation","changeAlertType");
                playAnimation();
            }
        }
    }

    public int getAlertType() {
        return mAlertType;
    }

    public void changeAlertType(int alertType) {
        changeAlertType(alertType, false);
    }


    public String getTitleText() {
        return mTitleText;
    }

    public SweetAlertDialog setTitleText(String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            if (text.isEmpty()) {
                mTitleTextView.setVisibility(View.GONE);
            } else {
                mTitleTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setText(Html.fromHtml(mTitleText));
            }
        }
        return this;
    }

    public SweetAlertDialog setTitleText(int resId) {
        return setTitleText(getContext().getResources().getString(resId));
    }

    public SweetAlertDialog setCustomImage(Drawable drawable) {
        mCustomImgDrawable = drawable;
        if (mCustomImage != null && mCustomImgDrawable != null) {
            mCustomImage.setVisibility(View.VISIBLE);
            mCustomImage.setImageDrawable(mCustomImgDrawable);
        }
        return this;
    }

    public SweetAlertDialog setCustomImage(int resourceId) {
        return setCustomImage(getContext().getResources().getDrawable(resourceId));
    }

    public String getContentText() {
        return mContentText;
    }

    /**
     * @param text text which can contain html tags.
     */
    public SweetAlertDialog setContentText(String text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText(true);
            if (contentTextSize != 0) {
                mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, spToPx(contentTextSize, getContext()));
            }
            mContentTextView.setText(Html.fromHtml(mContentText));
            mContentTextView.setVisibility(View.VISIBLE);
            mCustomViewContainer.setVisibility(View.GONE);
        }
        return this;
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    /**
     * @param width in SP
     */
    public SweetAlertDialog setStrokeWidth(float width) {
        this.strokeWidth = spToPx(width, getContext());
        return this;
    }
    //get border color for views
    private int genStrokeColor(int color) {
        float hsv[] = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.7f; // decrease value component
        return Color.HSVToColor(hsv);
    }

    private void applyStroke() {
        if (Float.compare(defStrokeWidth, strokeWidth) != 0) {
            Resources r = getContext().getResources();
           // setConfirmButtonBackgroundColor(r.getColor(R.color.main_green_color));
            //mConfirmButton.setBackgroundResource(R.drawable.green_button_background);
           // setButtonBackgroundColor(mNeutralButton, r.getColor(R.color.main_disabled_color));
            setCancelButtonBackgroundColor( r.getColor(R.color.my_app_on_error));
        }
    }

    public boolean isShowCancelButton() {
        return mShowCancel;
    }
    public SweetAlertDialog showCancelButton(boolean isShow) {
        mShowCancel = isShow;
        if (mCancelButton != null) {
            mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public boolean isShowContentText() {
        return mShowContent;
    }
    public SweetAlertDialog showContentText(boolean isShow) {
        mShowContent = isShow;
        if (mContentTextView != null) {
            mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
        }
        return this;
    }


    //set text for button Cancel
    public SweetAlertDialog setCancelText(String text) {
        mCancelText = text;
        if (mCancelButton != null && mCancelText != null) {
            showCancelButton(true);
            mCancelButton.setText(mCancelText);
        }
        return this;
    }
    public String getCancelText() {
        return mCancelText;
    }
    //set text for button confirm
    public SweetAlertDialog setConfirmText(String text) {
        mConfirmText = text;
        if (mConfirmButton != null && mConfirmText != null) {
            mConfirmButton.setText(Html.fromHtml(mConfirmText) );
        }
        return this;
    }
    public String getConfirmText() {
        return mConfirmText;
    }
    //set text  for button Neutral
    public SweetAlertDialog setNeutralText(String text) {
        mNeutralText = text;
        if (mNeutralButton != null && mNeutralText != null && !text.isEmpty()) {
            mNeutralButton.setVisibility(View.VISIBLE);
            mNeutralButton.setText(mNeutralText);
        }
        return this;
    }

    //set bg color for button confirm
    public SweetAlertDialog setConfirmButtonBackgroundColor(Integer color) {
        mConfirmButtonBackgroundColor = color;
        if (mConfirmButton!=null){
            //mConfirmButton.setBackgroundColor(color);
           // mConfirmButton.setBackgroundResource(R.drawable.is_btn_bg_green);
        }
        //setButtonBackgroundColor(mConfirmButton, color);
        return this;
    }
    public Integer getConfirmButtonBackgroundColor() {
        return mConfirmButtonBackgroundColor;
    }
    //set bg color for button Neutral
    public SweetAlertDialog setNeutralButtonBackgroundColor(Integer color) {
        mNeutralButtonBackgroundColor = color;
        //setButtonBackgroundColor(mNeutralButton, color);
        return this;
    }
    public Integer getNeutralButtonBackgroundColor() {
        return mNeutralButtonBackgroundColor;
    }
    //set bg color for button Cancel
    public SweetAlertDialog setCancelButtonBackgroundColor(Integer color) {
        mCancelButtonBackgroundColor = color;
        if (mCancelButton!=null){
            //mCancelButton.setBackgroundColor(color);
            mConfirmButton.setBackgroundResource(R.drawable.btn_background);
        }
       // setButtonBackgroundColor(mCancelButton, color);
        return this;
    }
    public Integer getCancelButtonBackgroundColor() {
        return mCancelButtonBackgroundColor;
    }

    private void setButtonBackgroundColor(Button btn, Integer color) {
        if (btn != null && color != null) {

            Drawable[] drawableItems = ViewUtils.getDrawable(btn);
            /*if (drawableItems != null) {
                GradientDrawable gradientDrawableUnChecked = (GradientDrawable) drawableItems[0];
                //solid color
                gradientDrawableUnChecked.setColor(color);
                //stroke
                gradientDrawableUnChecked.setStroke((int) strokeWidth, genStrokeColor(color));
            }*/
        }
    }

    //set text color for button confirm
    public SweetAlertDialog setConfirmButtonTextColor(Integer color) {
        mConfirmButtonTextColor = color;
        if (mConfirmButton != null && color != null) {
            mConfirmButton.setTextColor(mConfirmButtonTextColor);
        }
        return this;
    }
    public Integer getConfirmButtonTextColor() {
        return mConfirmButtonTextColor;
    }
    //set text color for button Neutral
    public SweetAlertDialog setNeutralButtonTextColor(Integer color) {
        mNeutralButtonTextColor = color;
        if (mNeutralButton != null && color != null) {
            mNeutralButton.setTextColor(mNeutralButtonTextColor);
        }
        return this;
    }
    public Integer getNeutralButtonTextColor() {
        return mNeutralButtonTextColor;
    }
    //set text color for button Cancel
    public SweetAlertDialog setCancelButtonTextColor(Integer color) {
        mCancelButtonTextColor = color;
        if (mCancelButton != null && color != null) {
            mCancelButton.setTextColor(mCancelButtonTextColor);
        }
        return this;
    }
    public Integer getCancelButtonTextColor() {
        return mCancelButtonTextColor;
    }

    //set action onClock  for button Cancel
    public SweetAlertDialog setCancelClickListener(OnSweetClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }
    //set action onClock  for button Confirm
    public SweetAlertDialog setConfirmClickListener(OnSweetClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }
    //set action onClock  for button Neutral
    public SweetAlertDialog setNeutralClickListener(OnSweetClickListener listener) {
        mNeutralClickListener = listener;
        return this;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.setTitleText(title.toString());
    }
    @Override
    public void setTitle(int titleId) {
        this.setTitleText(getContext().getResources().getString(titleId));
    }

    public Button getButton(int buttonType) {
        switch (buttonType) {
            default:
            case BUTTON_CONFIRM:
                return mConfirmButton;
            case BUTTON_CANCEL:
                return mCancelButton;
            case BUTTON_NEUTRAL:
                return mNeutralButton;
        }
    }

    //set Confirm Button is insert text and action
    public SweetAlertDialog setConfirmButton(String text, OnSweetClickListener listener) {
        this.setConfirmText(text);
        this.setConfirmClickListener(listener);
        return this;
    }
    //set Confirm Button is insert res id for text and action
    public SweetAlertDialog setConfirmButton(int resId, OnSweetClickListener listener) {
        String text = getContext().getResources().getString(resId);
        setConfirmButton(text, listener);
        return this;
    }

    //set Cancel Button is insert text and action
    public SweetAlertDialog setCancelButton(String text, OnSweetClickListener listener) {
        this.setCancelText(text);
        this.setCancelClickListener(listener);
        return this;
    }
    //set Cancel Button is insert res id  for text and action
    public SweetAlertDialog setCancelButton(int resId, OnSweetClickListener listener) {
        String text = getContext().getResources().getString(resId);
        setCancelButton(text, listener);
        return this;
    }

    public SweetAlertDialog setNeutralButton(String text, OnSweetClickListener listener) {
        this.setNeutralText(text);
        this.setNeutralClickListener(listener);
        return this;
    }
    public SweetAlertDialog setNeutralButton(int resId, OnSweetClickListener listener) {
        String text = getContext().getResources().getString(resId);
        setNeutralButton(text, listener);
        return this;
    }

    /**
     * Set content text size
     *
     * @param value text size in sp
     */
    public SweetAlertDialog setContentTextSize(int value) {
        this.contentTextSize = value;
        return this;
    }
    public int getContentTextSize() {
        return contentTextSize;
    }

     @Override
    protected void onStart() {
       // super.onStart();
         Log.i(TAG+">>onStart","**");
        mDialogView.startAnimation(mModalInAnim);
        playAnimation();
    }

    /**
     * set custom view instead of message
     *
     * @param view
     */
    public SweetAlertDialog setCustomView(View view) {
        mCustomView = view;
        if (mCustomView != null && mCustomViewContainer != null) {
            mCustomViewContainer.addView(view);
            mCustomViewContainer.setVisibility(View.VISIBLE);
            mContentTextView.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * The real Dialog.cancel() will be invoked async-ly after the animation finishes.
     */
    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }

    /**
     * The real Dialog.dismiss() will be invoked async-ly after the animation finishes.
     */
    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        //several view animations can't be launched at one view, that's why apply alpha animation on child
        ((ViewGroup) mDialogView).getChildAt(0).startAnimation(mOverlayOutAnim); //alpha animation
        mDialogView.startAnimation(mModalOutAnim); //scale animation
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_button) {
            if (mCancelClickListener != null) {
                mCancelClickListener.onClick(SweetAlertDialog.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.confirm_button) {
            if (mConfirmClickListener != null) {
                mConfirmClickListener.onClick(SweetAlertDialog.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.neutral_button) {
            if (mNeutralClickListener != null) {
                mNeutralClickListener.onClick(SweetAlertDialog.this);
            } else {
                dismissWithAnimation();
            }
        }
    }

    public ProgressHelper getProgressHelper() {
        return mProgressHelper;
    }

    public SweetAlertDialog setHideKeyBoardOnDismiss(boolean hide) {
        this.mHideKeyBoardOnDismiss = hide;
        return this;
    }

    public boolean isHideKeyBoardOnDismiss() {
        return this.mHideKeyBoardOnDismiss;
    }

    private void hideSoftKeyboard() {
        Activity activity = getOwnerActivity();
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
}