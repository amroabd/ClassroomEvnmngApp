package com.is.classroomevnmngapp.ui.admin.check_device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.is.classroomevnmngapp.databinding.FragmentAdminCheckDeviceBinding;

public class CheckDeviceAdminFragment extends Fragment {
    private CheckDeviceAdminViewModel classRoomUserViewModel;
    FragmentAdminCheckDeviceBinding checkDeviceBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        classRoomUserViewModel = new ViewModelProvider(this).get(CheckDeviceAdminViewModel.class);
        checkDeviceBinding=FragmentAdminCheckDeviceBinding.inflate(inflater);

        classRoomUserViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return checkDeviceBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String []arrData=new String[]{"one","tow","three","four"};
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, android.R.id.text1,arrData);
        checkDeviceBinding.classroomSpinner.setAdapter(arrayAdapter);

        checkDeviceBinding.classroomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"position:"+i,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}