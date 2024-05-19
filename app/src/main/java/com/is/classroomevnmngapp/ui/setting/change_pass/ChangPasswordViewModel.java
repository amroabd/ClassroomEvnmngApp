package com.is.classroomevnmngapp.ui.setting.change_pass;

import androidx.lifecycle.ViewModel;

import com.is.classroomevnmngapp.data.model.ResponseObj;
import com.is.classroomevnmngapp.data.source.remote.UploadCallback;
import com.is.classroomevnmngapp.ui.setting.change_pass.data.ChangePassRepository;
import com.is.classroomevnmngapp.ui.setting.change_pass.data.ChangePassRequest;

public class ChangPasswordViewModel extends ViewModel {
   ChangePassRepository sChangePassRepository;


    public ChangPasswordViewModel() {
       sChangePassRepository=new ChangePassRepository();
    }

    public void sendChangPassword(ChangePassRequest changePassRequest, UploadCallback<ResponseObj> callback){
         sChangePassRepository.changePassRequest(changePassRequest,callback);
    }

}