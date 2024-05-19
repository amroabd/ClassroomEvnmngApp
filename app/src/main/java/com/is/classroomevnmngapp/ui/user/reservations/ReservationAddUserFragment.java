package com.is.classroomevnmngapp.ui.user.reservations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.data.source.local.entities.ReservationEntity;
import com.is.classroomevnmngapp.databinding.FragmentUserReservationBinding;
import com.is.classroomevnmngapp.ui.BaseFragment;
import com.is.classroomevnmngapp.utils.ConvertData;
import com.is.classroomevnmngapp.utils.Log1;
import com.is.classroomevnmngapp.utils.ToastUtil1;
import com.is.classroomevnmngapp.utils.Validator;
import com.is.classroomevnmngapp.utils.widget.custom.CustomDialog;

import static com.is.classroomevnmngapp.utils.constant.KeyExtra.KEY_EXTRA_LECTURE_ID;
import static com.is.classroomevnmngapp.utils.widget.custom.CustomDialog.setDialogCallback;

public class ReservationAddUserFragment extends BaseFragment {
    private static final String TAG = "ReservationUserFragment";

    private ReservationUserViewModel viewModel;
    private FragmentUserReservationBinding reservationBinding;

    private int lectureId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            lectureId=getArguments().getInt(KEY_EXTRA_LECTURE_ID,0);
            Log1.d(TAG, "lectureId :"+lectureId);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log1.d(TAG, "onCreateView()");
        viewModel = new ViewModelProvider(this).get(ReservationUserViewModel.class);

        reservationBinding = FragmentUserReservationBinding.inflate(inflater, container, false);

        return reservationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log1.d(TAG, "onViewCreated()");
        //----------------
        reservationBinding.reserveButton.setOnClickListener(view1 -> {
            if (onValidateViews.onValidInput(0)) {
                Log1.d(TAG, "VALID INPUT");
                setDialogCallback(getContext(), new CustomDialog.NoteAlertDialogFactory(onValidateViews::onFollow),
                        getString(R.string.label_save), getString(R.string.label_message_sure));
            }
        });
        //---
        reservationBinding.dateEditText.setOnClickListener(view1 ->showDate(reservationBinding.dateEditText));
    }

    //create object to valid from input form
    private final Validator.OnValidateViews onValidateViews = new Validator.OnValidateViews() {
        @Override
        public boolean onValidInput(int role) {
            return Validator.isNotEmptyEditText(reservationBinding.usernameEditText, "Require Enter username.!!")
                    && Validator.isNotEmptyEditText(reservationBinding.dateEditText, "Require Select date reserve .!!")
                    && !Validator.emptySpinner(reservationBinding.startTimeSpinner)
                    && !Validator.emptySpinner(reservationBinding.endTimeSpinner);
        }
        @Override
        public void onFollow() {
            saveReservationData();
        }
    };

    private void saveReservationData() {
        Log1.d(TAG, "saveReservationData()");
        //-----------------------
        ReservationEntity entity = new ReservationEntity();
        entity.setLectureHallIdFk(lectureId);
        entity.setReserveUsername(ConvertData.to2String(reservationBinding.usernameEditText));
        entity.setReserveDate(ConvertData.to2String(reservationBinding.dateEditText));
        entity.setReserveStartTime((String.valueOf(reservationBinding.startTimeSpinner.getSelectedItem())));
        entity.setReserveEndTime((String.valueOf(reservationBinding.endTimeSpinner.getSelectedItem())));
        entity.setReserveStatus(1);
        Log1.d(TAG, "startTimeSpinner :"+reservationBinding.startTimeSpinner.getSelectedItem());

        //------
        int id = (int) viewModel.addReservation(entity);
        if (id > 0) {
            viewModel.uploadData();
            ToastUtil1.showToast(getContext(), "Success in Added data Reservation to db.!");
            NavHostFragment.findNavController(ReservationAddUserFragment.this).popBackStack();
        } else {
            ToastUtil1.showToastFail(getContext(), "Fail in Added data Reservation to db.!");
        }
    }

}