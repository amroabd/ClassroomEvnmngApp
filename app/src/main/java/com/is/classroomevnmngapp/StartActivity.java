package com.is.classroomevnmngapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.is.classroomevnmngapp.databinding.ActivityStartBinding;
import com.is.classroomevnmngapp.ui.AdminMainActivity;
import com.is.classroomevnmngapp.utils.SharePerf;

import static com.is.classroomevnmngapp.utils.SharePerf.TYPE_ACCOUNT_ADMIN;
import static com.is.classroomevnmngapp.utils.SharePerf.TYPE_ACCOUNT_USER;

public class StartActivity extends AppCompatActivity {
   ActivityStartBinding startBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharePerf.getInstance(this).isLoggedIn()) {
            if (SharePerf.getInstance(this).getTypeUser() == TYPE_ACCOUNT_ADMIN) {
                startActivity(new Intent(this, AdminMainActivity.class));
                finish();
            } else if (SharePerf.getInstance(this).getTypeUser() == TYPE_ACCOUNT_USER) {
                startActivity(new Intent(this, UserMainActivity.class));
                finish();
            }
            return;
        }

        startBinding=ActivityStartBinding.inflate(LayoutInflater.from(this));
        setContentView(startBinding.getRoot());

        startBinding.container.setAnimation(AnimationUtils.loadAnimation(this, R.anim.animate_slide_up_enter));
        //----
        startBinding.loginASAdminBtn.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        startBinding.loginASProfBtn.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        //----

        startBinding.admLyt.setOnClickListener(view -> {
            SharePerf.getInstance(getBaseContext()).addTypeUser(TYPE_ACCOUNT_ADMIN);
            startActivity(new Intent(StartActivity.this, AuthMainActivity.class));
        });
        startBinding.profmLyt.setOnClickListener(view -> {
            SharePerf.getInstance(getBaseContext()).addTypeUser(TYPE_ACCOUNT_USER);
            startActivity(new Intent(StartActivity.this, AuthMainActivity.class));
        });

    }
}