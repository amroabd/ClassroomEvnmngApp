package com.is.classroomevnmngapp.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.is.classroomevnmngapp.ui.AuthMainActivity;
import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.ui.UserMainActivity;
import com.is.classroomevnmngapp.databinding.OnboardingFragmentBinding;
import com.is.classroomevnmngapp.ui.AdminMainActivity;
import com.is.classroomevnmngapp.utils.SharePerf;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.Objects;

import static com.is.classroomevnmngapp.utils.SharePerf.TYPE_ACCOUNT_ADMIN;
import static com.is.classroomevnmngapp.utils.SharePerf.TYPE_ACCOUNT_USER;

public class OnboardingActivity extends AppCompatActivity {
    OnboardingFragmentBinding onboardingFragmentBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharePerf.getInstance(this).isLoggedIn()) {
            if (SharePerf.getInstance(this).getTypeUser() == TYPE_ACCOUNT_ADMIN) {
                startActivity(new Intent(OnboardingActivity.this, AdminMainActivity.class));
            } else if (SharePerf.getInstance(this).getTypeUser() == TYPE_ACCOUNT_USER) {
                startActivity(new Intent(OnboardingActivity.this, UserMainActivity.class));
            }
            return;
        }

        onboardingFragmentBinding = OnboardingFragmentBinding.inflate(LayoutInflater.from(this));
        onboardingFragmentBinding.getRoot();
        setContentView(onboardingFragmentBinding.getRoot());

        //bind adapter with viewPage
        onboardingFragmentBinding.viewpager.setAdapter(new OnboardingPagerAdapter(this));

        DotsIndicator springDotsIndicator = findViewById(R.id.dots_indicator);
        springDotsIndicator.setViewPager(onboardingFragmentBinding.viewpager);


        onboardingFragmentBinding.nextButton.setOnClickListener(v -> {
            int currentItem = onboardingFragmentBinding.viewpager.getCurrentItem();
            int totalItems = Objects.requireNonNull(onboardingFragmentBinding.viewpager.getAdapter()).getCount();
            if (currentItem < totalItems - 1) {
                onboardingFragmentBinding.viewpager.setCurrentItem(currentItem + 1);
            } else {
                startActivity(new Intent(OnboardingActivity.this, AuthMainActivity.class));
            }
        });

    }
}