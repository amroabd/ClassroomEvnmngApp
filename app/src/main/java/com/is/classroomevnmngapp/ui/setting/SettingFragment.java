package com.is.classroomevnmngapp.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.is.classroomevnmngapp.MyApplication;
import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.databinding.FragmentUserSettingBinding;
import com.is.classroomevnmngapp.utils.SharePerf;

import static com.is.classroomevnmngapp.utils.SharePerf.TYPE_ACCOUNT_USER;

public class SettingFragment extends Fragment {
    FragmentUserSettingBinding settingBinding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_setting, container, false);
        settingBinding = FragmentUserSettingBinding.inflate(inflater, container, false);
        return settingBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (SharePerf.getInstance(requireContext()).getTypeUser() == TYPE_ACCOUNT_USER) {
            settingBinding.addClassroomBtn.setVisibility(View.GONE);
        }
         //----------------------
        settingBinding.addClassroomBtn.setOnClickListener(view1 ->
                Navigation.findNavController(view1).navigate(R.id.action_settingFragment_to_classroomListFragment));
        //-----------------------
        settingBinding.changePasswordBtn.setOnClickListener(view1 ->
                Navigation.findNavController(view1).navigate(R.id.navigation_change_pass));
        //-----------------------
        MyApplication myApplication= (MyApplication) getActivity().getApplicationContext();
        settingBinding.logoutBtn.setOnClickListener(view1 -> myApplication.logout(requireContext()));
    }
}