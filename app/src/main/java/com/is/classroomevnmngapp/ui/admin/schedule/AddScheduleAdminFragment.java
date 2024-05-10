package com.is.classroomevnmngapp.ui.admin.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.is.classroomevnmngapp.R;

public class AddScheduleAdminFragment extends Fragment {

    private AddScheduleAdminViewModel classRoomUserViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        classRoomUserViewModel = new ViewModelProvider(this).get(AddScheduleAdminViewModel.class);
        View root = inflater.inflate(R.layout.fragment_admin_add_schedule, container, false);

        classRoomUserViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}