package com.is.rhismonitor.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.is.rhismonitor.R;
import com.is.rhismonitor.utils.DateUtils;

import org.jetbrains.annotations.NotNull;

import static widget.custom.CustomDialog.NoteAlertDialogFactory;
import static widget.custom.CustomDialog.setDialogCallback;

public class BaseListFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hidToolbar(true);
    }

    protected final void showDate(@NotNull TextView textView) {
        //textView.setOnClickListener(view -> DatePickerFragment.newInstance().showDateDialog(getChildFragmentManager(), textView::setText));
        textView.setOnClickListener(view -> materialDate(textView));
    }

    private void materialDate(TextView textView) {
        MaterialDatePicker<Long> m = MaterialDatePicker.Builder.datePicker().setTitleText("اختار التاريخ")
                .setSelection((MaterialDatePicker.todayInUtcMilliseconds())).build();
        //------------
        m.showNow(getChildFragmentManager(), "date");
        m.addOnPositiveButtonClickListener(selection -> textView.setText(DateUtils.getSelectDate(selection)));
    }

    protected void dateRangePicker(RangeDateListener dateCallback) {
        MaterialDatePicker<Pair<Long, Long>> m = MaterialDatePicker.Builder.dateRangePicker().setTitleText("اختار التاريخ من-الى")
                .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds() )).build();
        //------------
        m.showNow(getChildFragmentManager(), "date");
        m.addOnPositiveButtonClickListener(selection -> {
            dateCallback.resultSelection(DateUtils.getSelectDate(selection.first),DateUtils.getSelectDate(selection.second));
        });
    }

    protected interface RangeDateListener {
        void resultSelection(String first,String second);
    }

    @Nullable
    protected final ActionBar getSupportActionBar() {
        ActionBar actionBar = null;
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            actionBar = activity.getSupportActionBar();
            //actionBar.setCustomView(R.layout.toolbar);
        }
        return actionBar;
    }

    @SuppressLint("RestrictedApi")
    protected final void hidToolbar(boolean isHid) {
        if (getSupportActionBar() == null) return;
        //getSupportActionBar().setShowHideAnimationEnabled(true);
        if (isHid) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
    }

    protected final void back() {
        Toast.makeText(requireActivity(), "Successfully Added data.", Toast.LENGTH_SHORT).show();
        NavHostFragment.findNavController(requireParentFragment()).popBackStack();
    }

    protected final void onShowNote(String msg) {
        setDialogCallback(requireContext(), new NoteAlertDialogFactory(() -> {
        }), "wrong.", msg);
    }


    private void popUp(View ancherView) {
        LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popView = layoutInflater.inflate(R.layout.row_columns_four, null);
        //---
        PopupWindow popupWindow = new PopupWindow(popView, 400, 50, false);
        popupWindow.showAtLocation(ancherView, Gravity.CENTER, 0, 0);
        popupWindow.setAnimationStyle(R.animator.shr_next_button_state_list_anim);

        popView.setOnClickListener(view -> popupWindow.dismiss());

    }
}
