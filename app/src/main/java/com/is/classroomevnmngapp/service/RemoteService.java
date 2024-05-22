package com.is.classroomevnmngapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.data.source.local.AppDatabase;
import com.is.classroomevnmngapp.data.source.local.entities.UserEntity;
import com.is.classroomevnmngapp.utils.Log1;

import java.util.List;

public class RemoteService  extends Service {

    private static final String TAG = RemoteService.class.getSimpleName();

    //here can work in background thread for handle
    private void handleData(int startId){
        //here start handle


        //at end complete task do stop to service
        stopSelf(startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log1.d(TAG,"onStartCommand(intent,flags,startId)");

        //HANDLE DATA from remote source and publish updates through LiveData



        return START_STICKY;
    }

    private void fetchDataFromRemote(int type_data){
        Log1.d(TAG,"start fetchDataFromRemote-------->");
    }

    private void sendDataToRemote(int type_data){
        Log1.d(TAG,"start sendDataToRemote----------->");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log1.d(TAG,"onDestroy");
    }
}

//test================

// MyRepository.java
 class MyRepository {
    private MyLocalDataSource localDataSource;
    private MyRemoteDataSource remoteDataSource;

    public MyRepository(MyLocalDataSource localDataSource, MyRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public LiveData<List<UserEntity>> getData() {
        // Use a MediatorLiveData to combine local and remote data sources
        MediatorLiveData<List<UserEntity>> mediatorLiveData = new MediatorLiveData<>();

        // Observe the local data source
        LiveData<List<UserEntity>> localData = localDataSource.getLocalData();
        mediatorLiveData.addSource(localData, mediatorLiveData::setValue);

        // Observe the remote data source (e.g., a Service)
        LiveData<List<UserEntity>> remoteData = remoteDataSource.getRemoteData();
        mediatorLiveData.addSource(remoteData, data -> {
            // Save remote data to local data source
            localDataSource.saveData(data);
            mediatorLiveData.setValue(data);
        });

        return mediatorLiveData;
    }
}

// MyLocalDataSource.java
 class MyLocalDataSource {
    private AppDatabase appDatabase;

    public MyLocalDataSource(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public LiveData<List<UserEntity>> getLocalData() {
        return appDatabase.userDao().getAllLiveData();
    }

    public void saveData(List<UserEntity> data) {
        appDatabase.userDao().insertAll(data);
    }
}

// MyRemoteDataSource.java
 class MyRemoteDataSource {
    private MyService myService;

    public MyRemoteDataSource(MyService myService) {
        this.myService = myService;
    }

    public LiveData<List<UserEntity>> getRemoteData() {
        return myService.fetchDataFromService();
    }
}

// MyService.java
 class MyService extends Service {
    private final MutableLiveData<List<UserEntity>> dataLiveData = new MutableLiveData<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Fetch data from a remote source and publish updates through the LiveData
        fetchDataFromRemote();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void fetchDataFromRemote() {
        // Fetch data from a remote source (e.g., an API)
        //List<UserEntity> data = fetchDataFromApi();
        //dataLiveData.postValue(data);
    }

    public LiveData<List<UserEntity>> fetchDataFromService() {
        return dataLiveData;
    }
}

// Usage in an Activity/Fragment
 class MyActivity extends AppCompatActivity {
    private MyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_main);

        // Initialize the repository
        MyLocalDataSource localDataSource = new MyLocalDataSource(null);
        MyRemoteDataSource remoteDataSource = new MyRemoteDataSource(null);
        repository = new MyRepository(localDataSource, remoteDataSource);

        // Observe the data from the repository
        repository.getData().observe(this, data -> {
            // Update the UI with the fetched data
            updateUI(data);
        });
    }

    private void updateUI(List<UserEntity> data) {
        // Update the UI with the fetched data
    }
}
