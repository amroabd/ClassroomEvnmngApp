package com.is.classroomevnmngapp.ui.auth.sigup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.UserMainActivity;
import com.is.classroomevnmngapp.databinding.FragmentAuthSignupBinding;
import com.is.classroomevnmngapp.ui.AdminMainActivity;
import com.is.classroomevnmngapp.ui.auth.sigup.data.SigUpResponse;
import com.is.classroomevnmngapp.ui.auth.sigup.data.SignUpRequest;
import com.is.classroomevnmngapp.utils.ConvertData;
import com.is.classroomevnmngapp.utils.SharePerf;
import com.is.classroomevnmngapp.utils.Validator;
import com.is.classroomevnmngapp.utils.widget.custom.CustomDialog;

import static com.is.classroomevnmngapp.utils.SharePerf.TYPE_ACCOUNT_ADMIN;
import static com.is.classroomevnmngapp.utils.SharePerf.TYPE_ACCOUNT_USER;
import static com.is.classroomevnmngapp.utils.widget.custom.CustomDialog.setDialogCallback;

public class SignUpFragment extends Fragment {
    FragmentAuthSignupBinding signupBinding;
    SigUpViewModel viewModel;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        signupBinding = FragmentAuthSignupBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SigUpViewModel.class);
        return signupBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signupBinding.loginButton.setOnClickListener(view1 ->
                Navigation.findNavController(view1).navigate(R.id.nav_auth_login));

        //==============================

        signupBinding.createAccountButton.setOnClickListener(view12 -> {

            if (onValidateViews.onValidInput(0)) {

                final String username = ConvertData.to2String(signupBinding.usernameEditText);
                final String password = ConvertData.to2String(signupBinding.passwordEditText);
                final String phone = ConvertData.to2String(signupBinding.phoneEditText);
                final String email = ConvertData.to2String(signupBinding.emailEditText);

                final SignUpRequest sign = new SignUpRequest(username, password, email, phone);

                progressDialog = ProgressDialog.show(getContext(),
                        getString(R.string.title_progress),
                        getString(R.string.message_progresss));

                viewModel.sendSigUpRequest(sign, new SignUpCallback() {
                    @Override
                    public void onSuccess(SigUpResponse response) {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();

                        setDialogCallback(getContext(), new CustomDialog.NoteAlertDialogFactory(onValidateViews::onFollow),
                                "Title :" + response.getCode(),
                                        "Message :" + response.getData());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                        setDialogCallback(getContext(), new CustomDialog.ErrorAlertDialogFactory(() -> {
                        }), "", "Err :" + t.toString());
                    }
                });

            }
        });
    }

    //create object to valid from input form
   private final Validator.OnValidateViews onValidateViews = new Validator.OnValidateViews() {
        @Override
        public boolean onValidInput(int role) {
            return Validator.isNotEmptyEditText(signupBinding.usernameEditText, "Require Enter username.!!")
                    && Validator.isNotEmptyEditText(signupBinding.emailEditText, "Require Enter email.!!")
                    && Validator.isNotEmptyEditText(signupBinding.passwordEditText, "Require Enter password.!!")
                    && Validator.isNotEmptyEditText(signupBinding.phoneEditText, "Require Enter phone.!!");
        }

        @Override
        public void onFollow() {
            if (SharePerf.getInstance(getContext()).getTypeUser() == TYPE_ACCOUNT_ADMIN) {
                startActivity(new Intent(getContext(), AdminMainActivity.class));
            } else if (SharePerf.getInstance(getContext()).getTypeUser() == TYPE_ACCOUNT_USER) {
                startActivity(new Intent(getContext(), UserMainActivity.class));
            }
            //startActivity(new Intent(getActivity(), UserMainActivity.class));
        }
    };




}