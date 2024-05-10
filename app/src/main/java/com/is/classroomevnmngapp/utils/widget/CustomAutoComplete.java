package com.is.classroomevnmngapp.utils.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAutoComplete extends androidx.appcompat.widget.AppCompatAutoCompleteTextView {

    public CustomAutoComplete(@NonNull Context context) {
        super(context);
    }

    public CustomAutoComplete(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAutoComplete(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused&&getFilter()!=null)
            performFiltering(getText(),0);
    }
}
