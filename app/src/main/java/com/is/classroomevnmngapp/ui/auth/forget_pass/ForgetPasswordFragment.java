package com.is.classroomevnmngapp.ui.auth.forget_pass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.is.classroomevnmngapp.databinding.FragmentAuthForgetPasswordBinding;

public class ForgetPasswordFragment extends Fragment {
    private ForgetPasswordViewModel viewModel;
    FragmentAuthForgetPasswordBinding forgetPasswordBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
        forgetPasswordBinding =FragmentAuthForgetPasswordBinding.inflate(inflater);

        viewModel.getText().observe(getViewLifecycleOwner(), s -> {

        });
        return forgetPasswordBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
  }
}