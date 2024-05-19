package com.is.classroomevnmngapp.ui.setting.init_classroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;
import com.is.classroomevnmngapp.databinding.FragmentAdminAddClassroomBinding;
import com.is.classroomevnmngapp.utils.ConvertData;
import com.is.classroomevnmngapp.utils.ToastUtil1;
import com.is.classroomevnmngapp.utils.Validator;
import com.is.classroomevnmngapp.utils.widget.custom.CustomDialog;

public class AddClassroomAdminFragment extends Fragment {
    private ClassRoomViewModel viewModel;
    private FragmentAdminAddClassroomBinding addClassroomBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ClassRoomViewModel.class);
        addClassroomBinding = FragmentAdminAddClassroomBinding.inflate(inflater);

        viewModel.getAllLectureHall().observe(getViewLifecycleOwner(), dataList -> {

        });
        return addClassroomBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //event at onclick btn
        addClassroomBinding.addButton.setOnClickListener(view1 -> {
            if (onValidateViews.onValidInput(0)) {
                CustomDialog.setDialogCallback(requireContext(),
                        new CustomDialog.NoteAlertDialogFactory(onValidateViews::onFollow)
                        , getString(R.string.label_save), getString(R.string.label_message_save));
            }
        });
    }

    private final Validator.OnValidateViews onValidateViews = new Validator.OnValidateViews() {
        @Override
        public boolean onValidInput(int role) {
            return Validator.isNotEmptyEditText(addClassroomBinding.nameClassroomEditText,
                    getString(R.string.plse_enter_edit_text, "name lecture Hall")) &&
                    Validator.isNotEmptyEditText(addClassroomBinding.capacityEditText,
                            getString(R.string.plse_enter_edit_text, "capacity lecture Hall"));

        }

        @Override
        public void onFollow() {
            saveLectureHall();
        }
    };

    private void saveLectureHall() {
        LectureHallEntity entity = new LectureHallEntity();
        entity.setTitle(ConvertData.to2String(addClassroomBinding.nameClassroomEditText));
        entity.setCapacity(ConvertData.str2Integer(addClassroomBinding.capacityEditText));

        entity.setHasProjector((addClassroomBinding.projector.isChecked() ? 1 : 0));
        entity.setLightingStatus((addClassroomBinding.lighting.isChecked() ? 1 : 0));
        entity.setAcStatus((addClassroomBinding.aC.isChecked() ? 1 : 0));

        //----
        int id = viewModel.addLectureHall(entity);

        //---check if success insert
        if (id > 0) {
            ToastUtil1.showToast(requireContext(), "Success added new Record.");
            NavHostFragment.findNavController(AddClassroomAdminFragment.this).popBackStack();
        } else {
            ToastUtil1.showToastFail(requireContext(), "Failed added new Record.!!!");
        }

    }
}