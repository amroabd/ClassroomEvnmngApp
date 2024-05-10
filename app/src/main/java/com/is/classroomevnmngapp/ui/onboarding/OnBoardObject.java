package com.is.classroomevnmngapp.ui.onboarding;

import com.is.classroomevnmngapp.R;

public enum OnBoardObject {

    PAGE01(R.string.page_title_1, R.layout.onboarding_page_1),
    PAGE02(R.string.page_title_2, R.layout.onboarding_page_2),
    PAGE03(R.string.page_title_3, R.layout.onboarding_page_3);

    private final int mTitleResId;
    private final int mLayoutResId;

    OnBoardObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
