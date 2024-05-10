package com.is.classroomevnmngapp.ui.setting.change_pass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.is.classroomevnmngapp.databinding.FragmentChangePasswordBinding;

public class ChangPasswordFragment extends Fragment {
    private ChangPasswordViewModel classRoomUserViewModel;
    FragmentChangePasswordBinding checkDeviceBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        classRoomUserViewModel = new ViewModelProvider(this).get(ChangPasswordViewModel.class);
        checkDeviceBinding=FragmentChangePasswordBinding.inflate(inflater);

        classRoomUserViewModel.getText().observe(getViewLifecycleOwner(), s -> {

        });
        return checkDeviceBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
  }
}