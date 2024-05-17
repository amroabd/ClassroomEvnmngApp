package com.is.classroomevnmngapp.ui.setting.init_classroom;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.is.classroomevnmngapp.data.repository.LectureHallRepository;
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;

import java.util.List;

public class AddClassroomAdminViewModel extends AndroidViewModel {
    LectureHallRepository sLectureHallRepository;

    public AddClassroomAdminViewModel(Application application) {
        super(application);
        sLectureHallRepository=LectureHallRepository.getInstance(application);
    }



    public LiveData<List<LectureHallEntity>> getAllLectureHall(){
       return sLectureHallRepository.getAll();
    }

    public int addLectureHall(LectureHallEntity entity) {
      return (int) sLectureHallRepository.insertLectureHall(entity);
    }

}