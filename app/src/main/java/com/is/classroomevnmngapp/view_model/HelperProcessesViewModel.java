package com.is.classroomevnmngapp.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.is.classroomevnmngapp.data.repository.GetResultCallback;
import com.is.classroomevnmngapp.data.repository.HelperRepository;
import com.is.classroomevnmngapp.data.source.remote.InsertResultDownloadState;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_INIT_MAIN_MENU;
import static com.is.classroomevnmngapp.utils.constant.NameTableConst.NAME_INIT_SUB_MENU;


public class HelperProcessesViewModel extends AndroidViewModel {
    private static final String TAG = HelperProcessesViewModel.class.getSimpleName();
    //private final MutableLiveData<String> mText;
   protected HelperRepository helperRepository;
    //------------

    public HelperProcessesViewModel(Application application) {
        super(application);
        helperRepository = HelperRepository.getInstance(application);
    }


    public void getSpinnerList(String mainID,GetResultCallback<List<ListSpinner>>resultCallback){
        helperRepository.getSpinnerList(mainID,resultCallback);
    }



    /**
     * to merge data from other shm and wal files  to db ,run this method
     * useful before taking backup
     */
    public void checkPoint(){
        helperRepository.checkPoint();
    }

    public int getCount(String cols, String tableName) {
        return helperRepository.getCount(cols, tableName);
    }


    public int deleteAllEntries(@NotNull String nameT) {
        switch (nameT) {
            case NAME_INIT_MAIN_MENU:
                return helperRepository.deleteAllMainMen();
            case NAME_INIT_SUB_MENU:
                return helperRepository.deleteAllSubMen();
            default:
                return 0;
        }

    }

    public void downMainMenu(InsertResultDownloadState<String> stringResultState) {
        helperRepository.downMainMenu(stringResultState);
    }

    public void downSubMenu(InsertResultDownloadState<String> stringResultState) {
        helperRepository.downSubMenu(stringResultState);

    }




}