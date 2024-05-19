package com.is.classroomevnmngapp.ui.setting.change_pass;

import android.app.ProgressDialog;
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
import com.is.classroomevnmngapp.data.model.ResponseObj;
import com.is.classroomevnmngapp.data.source.remote.UploadCallback;
import com.is.classroomevnmngapp.databinding.FragmentChangePasswordBinding;
import com.is.classroomevnmngapp.ui.setting.change_pass.data.ChangePassRequest;
import com.is.classroomevnmngapp.utils.ConvertData;
import com.is.classroomevnmngapp.utils.SharePerf;
import com.is.classroomevnmngapp.utils.Validator;
import com.is.classroomevnmngapp.utils.widget.custom.CustomDialog;

import static com.is.classroomevnmngapp.utils.widget.custom.CustomDialog.setDialogCallback;

public class ChangPasswordFragment extends Fragment {
    private ChangPasswordViewModel viewModel;
    FragmentChangePasswordBinding changePasswordBinding;
    ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ChangPasswordViewModel.class);
        changePasswordBinding =FragmentChangePasswordBinding.inflate(inflater);


        return changePasswordBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changePasswordBinding.changeButton.setOnClickListener(view1 -> {
            if (onValidateViews.onValidInput(0)){
                final String oldPass = ConvertData.to2String(changePasswordBinding.oldPassEditText);
                final String newPass = ConvertData.to2String(changePasswordBinding.newPassEditText);
                //final String phone = ConvertData.to2String(changePasswordBinding.confirmNewPassEditText);
                int id= Integer.parseInt(SharePerf.getInstance(getContext()).getUserID());

                ChangePassRequest changePassRequest=new ChangePassRequest(id,oldPass,newPass);
                progressDialog = ProgressDialog.show(getContext(),
                        (getString(R.string.title_change_pass)),
                        getString(R.string.message_change_pass));

                viewModel.sendChangPassword(changePassRequest, new UploadCallback<ResponseObj>() {
                    @Override
                    public void onSuccess(ResponseObj response) {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();

                        if (response.getCode().equals("Success")){
                            setDialogCallback(getContext(), new CustomDialog.NoteAlertDialogFactory(onValidateViews::onFollow),
                                    "Title :" + response.getCode(),
                                    String.format("Message :%s,Data :%s", response.getCode(), response.getServId()));
                            return;
                        }
                        //problem
                        setDialogCallback(getContext(), new CustomDialog.ErrorAlertDialogFactory(() -> {
                        }), response.getCode(), "Waring :" + response.toString());
                    }

                    @Override
                    public void onError(String error) {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                        //problem
                        setDialogCallback(getContext(), new CustomDialog.ErrorAlertDialogFactory(() -> {
                        }), "Err.", "Waring :" + error);
                    }
                });

            }
        });
  }
    //create object to valid from input form
    private final Validator.OnValidateViews onValidateViews = new Validator.OnValidateViews() {
        @Override
        public boolean onValidInput(int role) {
            return Validator.isNotEmptyEditText(changePasswordBinding.oldPassEditText, "Require Enter old Password.!!")
                    && Validator.isNotEmptyEditText(changePasswordBinding.newPassEditText, "Require Enter New password.!!")
                    && Validator.isNotEmptyEditText(changePasswordBinding.confirmNewPassEditText, "Require Enter confirm New password.!!");
        }

        @Override
        public void onFollow() {
            NavHostFragment.findNavController(ChangPasswordFragment.this).popBackStack();

        }
    };
}