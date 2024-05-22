package com.is.classroomevnmngapp.ui;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.is.classroomevnmngapp.MyApplication;
import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.data.repository.GetResultCallback;
import com.is.classroomevnmngapp.data.repository.LectureHallRepository;
import com.is.classroomevnmngapp.data.repository.UserRepository;
import com.is.classroomevnmngapp.view_model.RemoteProcessorViewModel;

import java.util.Objects;

public class AdminMainActivity extends AppCompatActivity implements GetResultCallback {
    private RemoteProcessorViewModel remoteProcessorViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        remoteProcessorViewModel =new ViewModelProvider(this).get(RemoteProcessorViewModel.class);
        setupToolbar();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_admin_home).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId()==R.id.nav_admin_home){
                toolbar.setTitle(R.string.label_home);
            }else  if (destination.getId()==R.id.navigation_admin_add_schedule){
                toolbar.setTitle(R.string.label_add_schedule);
            }else  if (destination.getId()==R.id.navigation_admin_check_device){
                toolbar.setTitle(R.string.label_check_devices);
            }else  if (destination.getId()==R.id.navigation_admin_config_sensors){
                toolbar.setTitle(R.string.label_config_sensors);
            }else {
                toolbar.setTitle(R.string.label_setting);
            }
        });

        //sync user from remote
        remoteProcessorViewModel.downloadData(new UserRepository(this), this);
        remoteProcessorViewModel.downloadData( LectureHallRepository.getInstance(this), this);
        //remoteProcessorViewModel.downloadData( ReservationRepository.getInstance(this), this);
    }

    Toolbar toolbar;

    protected void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResult(Object o) {
        MyApplication.getInstance().popupWindow(toolbar,o,this);
    }
}