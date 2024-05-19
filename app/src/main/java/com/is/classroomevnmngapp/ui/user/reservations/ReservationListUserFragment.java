package com.is.classroomevnmngapp.ui.user.reservations;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.data.model.JoinReserveALecture;
import com.is.classroomevnmngapp.databinding.FragmentUserReserveListBinding;
import com.is.classroomevnmngapp.databinding.RowItemReserveBinding;
import com.is.classroomevnmngapp.utils.DateUtils;
import com.is.classroomevnmngapp.utils.Log1;
import com.is.classroomevnmngapp.utils.widget.custom.CustomDialog;

import static com.is.classroomevnmngapp.data.model.JoinReserveALecture.DIFF_CALLBACK;
import static com.is.classroomevnmngapp.utils.DateUtils.getDate;
import static com.is.classroomevnmngapp.utils.constant.KeyExtra.KEY_EXTRA_LECTURE_ID;

public class ReservationListUserFragment extends Fragment {
    private static final String TAG = "ReservationListUserFrag";
    private ReservationUserViewModel viewModel;
    private FragmentUserReserveListBinding reserveListBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log1.d(TAG,"onCreateView()");
        viewModel = new ViewModelProvider(this).get(ReservationUserViewModel.class);
        reserveListBinding = FragmentUserReserveListBinding.inflate(inflater, container, false);
        //
        return reserveListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        initRecyclerView();
    }

    private void initRecyclerView() {
        ReserveAdapter itemAdapter = new ReserveAdapter();

        viewModel.getReserveALectureList().observe(getViewLifecycleOwner(), joinReserveALectures -> {
            //-----
            itemAdapter.submitList(joinReserveALectures);
            Log1.d(TAG, "joinReserveALectures size :" + joinReserveALectures.size());
        });

        reserveListBinding.listview.setAdapter(itemAdapter);
    }

    class ReserveAdapter extends PagedListAdapter<JoinReserveALecture, ReserveVHolder> {

        protected ReserveAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public ReserveVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RowItemReserveBinding itemReserveBinding;
            itemReserveBinding = RowItemReserveBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ReserveVHolder(itemReserveBinding);
        }

        @SuppressLint({"NewApi", "ResourceAsColor"})
        @Override
        public void onBindViewHolder(@NonNull ReserveVHolder holder, int position) {
            JoinReserveALecture item = getItem(position);

            //-----
            if (item != null) {
                holder.itemReserveBinding.itemTitleClassroom.setText(String.format("%s", item.getTitle()));
                if (item.getReserveEndTime() != null) {
                    boolean hasEnd = DateUtils.compareCurrentWithParseTime(item.getReserveEndTime());
                    if (!hasEnd && item.getReserveStatus() == 1) {
                        Log1.d(TAG, "----------> hasEnd incomplete");
                        // incomplete
                        holder.itemReserveBinding.reserveDateLyt.setVisibility(View.VISIBLE);
                        //----
                        holder.itemReserveBinding.itemStartTime.setText(String.format("%s %s", getDate(), item.getReserveStartTime()));
                        holder.itemReserveBinding.itemEndTime.setText(String.format("%s %s", getDate(), item.getReserveEndTime()));
                        holder.itemReserveBinding.statImageView.setImageTintList(ColorStateList.valueOf(R.color.success_100));
                    } else {
                        // at status complete time reserve, here update status in db
                        Log1.d(TAG, "----------> hasEnd complete START update status");
                        if (item.getReserveStatus() == 1)
                            viewModel.updateReservation(item.getReserveId(), 0);
                        else {
                            holder.itemReserveBinding.itemStatusReserve.setText(String.format("%s", "متاح"));
                        }
                    }
                } else {
                    holder.itemReserveBinding.itemStatusReserve.setText(String.format("%s", "متاح"));
                }
            }else {
                Log1.d(TAG, "----------> no data here add placeholder");

            }

            //-------action
            holder.itemReserveBinding.getRoot().setOnClickListener(view -> {
                if (item != null) {
                    if (item.getReserveEndTime() != null) {
                        if (item.getReserveStatus() == 1 && !DateUtils.compareCurrentWithParseTime(item.getReserveEndTime())) {
                            CustomDialog.setDialogCallback(requireContext(),
                                    new CustomDialog.ErrorAlertDialogFactory(() -> {

                                    }), "تنبية.", "لايمكنك حجز هذا القاعة لان حالة الحجز لم تتنتهي بعد.!!!");
                            return;
                        }
                    }
                    {
                        Log1.d(TAG, "---------->lecture hall is avail can Go to reserve ");
                        //avail can Go to reserve
                        Bundle args = new Bundle();
                        args.putInt(KEY_EXTRA_LECTURE_ID, item.getLectureHallID());
                        Navigation.findNavController(view).navigate(R.id.navigation_user_reservation, args);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }
    }

    static class ReserveVHolder extends RecyclerView.ViewHolder {
        RowItemReserveBinding itemReserveBinding;

        public ReserveVHolder(@NonNull RowItemReserveBinding itemReserveBinding) {
            super(itemReserveBinding.getRoot());
            this.itemReserveBinding = itemReserveBinding;
        }


    }
}
