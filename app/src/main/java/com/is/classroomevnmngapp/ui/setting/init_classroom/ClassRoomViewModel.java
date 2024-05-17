package com.is.classroomevnmngapp.ui.setting.init_classroom;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.is.classroomevnmngapp.data.repository.LectureHallRepository;
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;

public class ClassRoomViewModel extends AndroidViewModel {
    LectureHallRepository sLectureHallRepository;

    public ClassRoomViewModel(Application application) {
        super(application);
        sLectureHallRepository=LectureHallRepository.getInstance(application);
    }



    public LiveData<PagedList<LectureHallEntity>> getAllLectureHall(){
       return sLectureHallRepository.getAllData();
    }

    public int addLectureHall(LectureHallEntity entity) {
      return (int) sLectureHallRepository.insertLectureHall(entity);
    }

}