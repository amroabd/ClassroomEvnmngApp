package com.is.classroomevnmngapp.ui.admin.config_sensor;

import android.app.Application;

import com.is.classroomevnmngapp.data.repository.ControllerRepository;
import com.is.classroomevnmngapp.data.source.local.entities.ControllerEntity;
import com.is.classroomevnmngapp.view_model.HelperProcessesViewModel;

public class ConfigSensorsAdminViewModel extends HelperProcessesViewModel {
       private
    ControllerRepository controllerRepository;

    public ConfigSensorsAdminViewModel(Application application) {
        super(application);
        controllerRepository =ControllerRepository.getInstance(application);
    }


    public void addConfigSensor(ControllerEntity controllerEntity){
        controllerRepository.insertController(controllerEntity);
    }

    public void sendConfigSensorRequest(String classroom,String sensor){

    }


}