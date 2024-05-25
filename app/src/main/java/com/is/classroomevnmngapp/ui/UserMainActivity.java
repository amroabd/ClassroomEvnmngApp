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
import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.data.repository.GetResultCallback;
import com.is.classroomevnmngapp.data.repository.LectureHallRepository;
import com.is.classroomevnmngapp.data.repository.ReservationRepository;
import com.is.classroomevnmngapp.utils.ToastUtil1;
import com.is.classroomevnmngapp.view_model.RemoteProcessorViewModel;

import java.util.Objects;

public class UserMainActivity extends AppCompatActivity {
   private Toolbar toolbar;
    private RemoteProcessorViewModel remoteProcessorViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        remoteProcessorViewModel = new ViewModelProvider(this).get(RemoteProcessorViewModel.class);
        setupToolbar();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_user_home).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId()==R.id.navigation_user_home){
                toolbar.setTitle(R.string.label_home);
            }else  if (destination.getId()==R.id.navigation_user_classroom){
                toolbar.setTitle(R.string.label_class_rooms);
            }else  if (destination.getId()==R.id.navigation_user_reservation){
                toolbar.setTitle(R.string.label_reserve_room);
            }else {
                toolbar.setTitle(R.string.label_setting);
            }
        });

        //---------

        remoteProcessorViewModel.downloadData(LectureHallRepository.getInstance(this), mResultCallback);
        remoteProcessorViewModel.downloadData(ReservationRepository.getInstance(this), mResultCallback);
        //--------
    }



    protected void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //----
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

    //listener for result download remote update UI
    private final GetResultCallback mResultCallback= o -> ToastUtil1.showToast(getApplicationContext(),  o.toString());

}