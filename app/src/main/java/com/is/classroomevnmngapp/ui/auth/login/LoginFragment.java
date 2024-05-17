package com.is.classroomevnmngapp.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.UserMainActivity;
import com.is.classroomevnmngapp.databinding.FragmentAuthLoginBinding;
import com.is.classroomevnmngapp.ui.AdminMainActivity;
import com.is.classroomevnmngapp.ui.auth.login.data.LoginResponse;
import com.is.classroomevnmngapp.utils.ConvertData;
import com.is.classroomevnmngapp.utils.SharePerf;
import com.is.classroomevnmngapp.utils.Validator;

import static com.is.classroomevnmngapp.utils.SharePerf.TYPE_ACCOUNT_ADMIN;
import static com.is.classroomevnmngapp.utils.SharePerf.TYPE_ACCOUNT_USER;
import static com.is.classroomevnmngapp.utils.widget.custom.CustomDialog.ErrorAlertDialogFactory;
import static com.is.classroomevnmngapp.utils.widget.custom.CustomDialog.setDialogCallback;

public class LoginFragment extends Fragment {

    FragmentAuthLoginBinding loginBinding;
    LoginViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginBinding = FragmentAuthLoginBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        return loginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //-----------------------------------
        loginBinding.createAccountButton.setOnClickListener(view1 ->
                Navigation.findNavController(view1).navigate(R.id.action_loginFragment_to_signUpFragment));

        loginBinding.forgetPasswordButton.setOnClickListener(view1 ->
                Navigation.findNavController(view1).navigate(R.id.action_loginFragment_to_forgetPassFragment));

        //-----------------------
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                // validInputLogin();
            }
        };
        //-------------------
        loginBinding.usernameEditText.addTextChangedListener(afterTextChangedListener);
        loginBinding.passwordEditText.addTextChangedListener(afterTextChangedListener);
        //----------------
        loginBinding.passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login();
            }
            return false;
        });

        loginBinding.loginButton.setOnClickListener(view1 -> validInputLogin());

    }

    private void validInputLogin() {
        if (Validator.isUserNameValid(loginBinding.usernameEditText)
                && !Validator.emptyEditText(loginBinding.passwordEditText, getString(R.string.invalid_password))) {
            login();
        }
    }

    private void login() {

        String username = ConvertData.to2String(loginBinding.usernameEditText);
        String password = ConvertData.to2String(loginBinding.passwordEditText);

        viewModel.sendLoginRequest(username, password, new LoginCallback() {
            @Override
            public void onSuccess(LoginResponse response) {
                SharePerf.getInstance(getContext()).addUserLogin(response.getUserId(), username, password);
                SharePerf.getInstance(getContext()).addTypeUser(response.getUserType());
                //---start activity
                if (response.getUserType() == TYPE_ACCOUNT_ADMIN) {
                    startActivity(new Intent(getContext(), AdminMainActivity.class));
                } else if (response.getUserType() == TYPE_ACCOUNT_USER) {
                    startActivity(new Intent(getContext(), UserMainActivity.class));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                setDialogCallback(getContext(), new ErrorAlertDialogFactory(() -> {

                }), "", "Err :" + t.toString());
            }
        });
         if (SharePerf.getInstance(getContext()).isLoggedIn()) {
             if (SharePerf.getInstance(getContext()).getTypeUser() == TYPE_ACCOUNT_ADMIN) {
                 startActivity(new Intent(getContext(), AdminMainActivity.class));
             } else if (SharePerf.getInstance(getContext()).getTypeUser() == TYPE_ACCOUNT_USER) {
                 startActivity(new Intent(getContext(), UserMainActivity.class));
             }
         }else {
             setDialogCallback(getContext(), new ErrorAlertDialogFactory(() -> {

             }), "", "Err :" + "بيانات تسجيل الدخول غير صحيح ,<br>تاكد من ان لديك حساب سابق او انشى حساب جديد.!!! ");
         }
    }

}