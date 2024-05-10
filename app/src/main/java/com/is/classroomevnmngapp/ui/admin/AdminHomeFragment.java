package com.is.classroomevnmngapp.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.databinding.FragmentAdminHomeBinding;


public class AdminHomeFragment extends Fragment {

   private FragmentAdminHomeBinding adminMainFragmentBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adminMainFragmentBinding=FragmentAdminHomeBinding.inflate(inflater,container,false);
        return adminMainFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //----------------
        adminMainFragmentBinding.checkedDeviceIcon.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));
        adminMainFragmentBinding.configSensorIcon.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));
        adminMainFragmentBinding.scheduleIcon.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));
        adminMainFragmentBinding.settingIcon.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));

        //
        adminMainFragmentBinding.sensorCard.setOnClickListener(view1 -> {
            configSensor();
        });
        adminMainFragmentBinding.scheduleCard.setOnClickListener(view1 -> {
            schedule();
        });
        adminMainFragmentBinding.deviceCheckCard.setOnClickListener(view1 -> {
            deviceCheck();
        });
        adminMainFragmentBinding.settingCard.setOnClickListener(view1 -> {
            setting();
        });
    }

    private void configSensor(){

    }
    private void deviceCheck(){

    }
    private void setting(){

    }
    private void schedule(){

    }


}
