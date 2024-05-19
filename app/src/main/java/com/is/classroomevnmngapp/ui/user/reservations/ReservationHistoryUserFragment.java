package com.is.classroomevnmngapp.ui.user.reservations;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.is.classroomevnmngapp.data.model.JoinReserveALecture;
import com.is.classroomevnmngapp.databinding.FragmentUserReserveHistoryBinding;
import com.is.classroomevnmngapp.databinding.RowItemReserveHistoryBinding;
import com.is.classroomevnmngapp.utils.DateUtils;
import com.is.classroomevnmngapp.utils.Log1;

import static com.is.classroomevnmngapp.data.model.JoinReserveALecture.DIFF_CALLBACK;
import static com.is.classroomevnmngapp.utils.DateUtils.getDate;


public class ReservationHistoryUserFragment extends Fragment {
    private static final String TAG = "ReservationHistoryUserF";
    private ReservationUserViewModel viewModel;
    FragmentUserReserveHistoryBinding classroomBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ReservationUserViewModel.class);
             classroomBinding=FragmentUserReserveHistoryBinding.inflate(inflater);

        return classroomBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

     initRecyclerView();
    }

    //======================
    private void initRecyclerView() {
       ItemAdapter itemAdapter = new ItemAdapter();

        viewModel.getReserveALectureHistList().observe(getViewLifecycleOwner(), joinReserveALectures -> {
            //-----
            itemAdapter.submitList(joinReserveALectures);
            Log1.d(TAG, "joinReserveALectures size :" + joinReserveALectures.size());
        });

        classroomBinding.listview.setAdapter(itemAdapter);
    }

    //============================
    class ItemAdapter extends PagedListAdapter<JoinReserveALecture, ReserveHVHolder> {

        protected ItemAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public ReserveHVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RowItemReserveHistoryBinding itemReserveBinding;
            itemReserveBinding= RowItemReserveHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ReserveHVHolder(itemReserveBinding);
        }

        @SuppressLint({"NewApi", "ResourceAsColor"})
        @Override
        public void onBindViewHolder(@NonNull ReserveHVHolder holder, int position) {
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
                        holder.itemReserveBinding.cancelButton.setVisibility(View.VISIBLE);
                        //----
                        holder.itemReserveBinding.itemStartTime.setText(String.format("%s %s", getDate(), item.getReserveStartTime()));
                        holder.itemReserveBinding.itemEndTime.setText(String.format("%s %s", getDate(), item.getReserveEndTime()));
                        holder.itemReserveBinding.cancelButton.setOnClickListener(view -> {
                            viewModel.updateReservation(item.getReserveId(), 0);
                        });

                    } else {
                        // at status complete time reserve, here update status in db
                        Log1.d(TAG, "----------> hasEnd complete START update status");
                        if (item.getReserveStatus() == 1)
                            viewModel.updateReservation(item.getReserveId(), 0);
                        else {
                            holder.itemReserveBinding.cancelButton.setVisibility(View.GONE);
                            holder.itemReserveBinding.reserveDateLyt.setVisibility(View.GONE);
                            holder.itemReserveBinding.itemStatusReserve.setText(String.format("%s", "متاح"));
                        }

                    }
                } else {
                    holder.itemReserveBinding.itemStatusReserve.setText(String.format("%s", "متاح"));
                }
            }

        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }
    }

    static class ReserveHVHolder extends RecyclerView.ViewHolder {
        RowItemReserveHistoryBinding itemReserveBinding;

        public ReserveHVHolder(@NonNull RowItemReserveHistoryBinding itemReserveBinding) {
            super(itemReserveBinding.getRoot());
            this.itemReserveBinding = itemReserveBinding;
        }
    }
}