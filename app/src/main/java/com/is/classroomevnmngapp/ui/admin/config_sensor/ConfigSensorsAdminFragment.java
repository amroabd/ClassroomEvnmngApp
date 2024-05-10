package com.is.classroomevnmngapp.ui.admin.config_sensor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.is.classroomevnmngapp.databinding.FragmentAdminConfigSensorsBinding;
import com.is.classroomevnmngapp.utils.Log1;
import com.is.classroomevnmngapp.utils.ToastUtil1;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;
import com.is.classroomevnmngapp.utils.spinner.ListSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConfigSensorsAdminFragment extends Fragment {
    private static final String TAG = "ConfigSensorsAdminFragm";
    private ConfigSensorsAdminViewModel viewModel;
    FragmentAdminConfigSensorsBinding sensorsBinding;
    private List<ListSpinner> sensorListSpinners, classroomListSpinners;
    ListSpinnerAdapter classroomSpinnerAdapter, sensorSpinnerAdapter;
    String sensorId;
    String classId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ConfigSensorsAdminViewModel.class);
        sensorsBinding = FragmentAdminConfigSensorsBinding.inflate(inflater, container, false);

        return sensorsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //----------
        sensorsBinding.startCheckButton.setOnClickListener(view1 -> sendConfigSensor());
        //----------
        loadSpinnerClassroom();
        loadSpinnerSensor();
        //----------
        sensorsBinding.classroomSpinner.setOnItemClickListener((adapterView, view1, position, l) -> {
            Log1.d(TAG, "classroomSpinner.setOnItemClickListener :" + ((ListSpinner) adapterView.getItemAtPosition(position)).getIdSpinnerS());
            classId = ((ListSpinner) adapterView.getItemAtPosition(position)).getIdSpinnerS();
            ToastUtil1.showToast(getContext(), "item :" + ((ListSpinner) adapterView.getItemAtPosition(position)));
            classroomSpinnerAdapter.setSelectedIndex(position);
        });

        sensorsBinding.sensorSpinner.setOnItemClickListener((adapterView, view1, position, l) -> {
            Log1.d(TAG, "sensorSpinner.setOnItemClickListener :" + ((ListSpinner) adapterView.getItemAtPosition(position)).getIdSpinnerS());
            sensorId = ((ListSpinner) adapterView.getItemAtPosition(position)).getIdSpinnerS();
            ToastUtil1.showToast(getContext(), "item :" + ((ListSpinner) adapterView.getItemAtPosition(position)));
            classroomSpinnerAdapter.setSelectedIndex(position);
        });

    }

    private void loadSpinnerClassroom() {
        classroomListSpinners = new ArrayList<>(0);
        viewModel.getSpinnerList("1", listSpinner -> {
            classroomListSpinners = listSpinner;
            Log1.d(TAG, "resultCallback-> classroomListSpinners :" + classroomListSpinners.size() + "," + listSpinner.size());
            classroomSpinnerAdapter = new ListSpinnerAdapter(requireContext(), classroomListSpinners);
            sensorsBinding.classroomSpinner.setAdapter(classroomSpinnerAdapter);
        });
        Log1.d(TAG, "listSpinner :" + classroomListSpinners.size());
    }

    private void loadSpinnerSensor() {
        sensorListSpinners = new ArrayList<>(0);
        viewModel.getSpinnerList("2", listSpinner -> {
            sensorListSpinners = listSpinner;
            Log1.d(TAG, "resultCallback-> sensorListSpinners :" + sensorListSpinners.size());
            sensorSpinnerAdapter = new ListSpinnerAdapter(requireContext(), sensorListSpinners);
            sensorsBinding.sensorSpinner.setAdapter(sensorSpinnerAdapter);
        });
        Log1.d(TAG, "sensorListSpinners :" + sensorListSpinners.size());
    }


    private void sendConfigSensor() {
        try {

            Log1.d(TAG, "classId :" + classId + ",sensorId :" + sensorId);
            //-------------
            viewModel.sendConfigSensor(classId, sensorId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}