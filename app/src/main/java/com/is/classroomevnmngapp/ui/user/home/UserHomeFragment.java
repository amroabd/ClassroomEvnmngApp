package com.is.classroomevnmngapp.ui.user.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.databinding.FragmentUserHomeBinding;
import com.is.classroomevnmngapp.ui.user.reservations.ReservationUserViewModel;

import java.util.Locale;

public class UserHomeFragment extends Fragment {

    private UserHomeViewModel userHomeViewModel;
    private ReservationUserViewModel viewModel;
    FragmentUserHomeBinding userHomeBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ReservationUserViewModel.class);
        userHomeViewModel = new ViewModelProvider(this).get(UserHomeViewModel.class);
        userHomeBinding=FragmentUserHomeBinding.inflate(inflater);

        userHomeViewModel.getText().observe(getViewLifecycleOwner(), s -> { });
        return userHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //----------------
        userHomeBinding.classRoomsIcon.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));
        userHomeBinding.reserveRoomIcon.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));
        userHomeBinding.settingIcon.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));

        //
        userHomeBinding.classRoomsCard.setOnClickListener(view1 -> classRooms());
        userHomeBinding.reserveRoomCard.setOnClickListener(view1 -> reserveRoom());
        userHomeBinding.settingCard.setOnClickListener(view1 -> setting());
        //----------
        detail();
    }

    private void detail(){
        userHomeBinding.reserveRoomCounts.setText(String.format(Locale.ENGLISH,"%d", viewModel.getReservedCount()));

    }

    private void classRooms (){

    }
    private void reserveRoom (){

    }
    private void setting (){

    }


}