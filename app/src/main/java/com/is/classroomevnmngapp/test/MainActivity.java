package com.is.classroomevnmngapp.test;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.navigationrail.NavigationRailView;
import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {
    NavigationRailView navigationRailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        navigationRailView = findViewById(R.id.cat_navigation_rail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commitNow();
        }

        navigationRailView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.alarms_nav) {
                Toast.makeText(getBaseContext(), "alarms_nav", Toast.LENGTH_LONG).show();
                return true;
            } else if (item.getItemId() == R.id.schedule_nav) {
                Toast.makeText(getBaseContext(), "schedule_nav", Toast.LENGTH_LONG).show();
                BadgeDrawable badgeDrawable= navigationRailView.getBadge(R.id.schedule_nav);
                if (badgeDrawable!=null){
                    badgeDrawable.clearNumber();
                    badgeDrawable.setVisible(false);
                }
                return true;
            } else if (item.getItemId() == R.id.timer_nav) {
                Toast.makeText(getBaseContext(), "timer_nav", Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
        });
        //--------
        navigationRailView.getOrCreateBadge(R.id.schedule_nav).setNumber(6);

    }
}