package com.is.classroomevnmngapp.ui.reservations;

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
import com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity;
import com.is.classroomevnmngapp.databinding.FragmentListReservationBinding;
import com.is.classroomevnmngapp.databinding.RowItemReserveBinding;
import com.is.classroomevnmngapp.utils.Log1;

import static com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity.DIFF_CALLBACK;
import static com.is.classroomevnmngapp.utils.constant.KeyExtra.KEY_EXTRA_LECTURE_ID;

public class ReservationListFragment extends Fragment {
    private static final String TAG = "ReservationListUserFrag";
    private ReservationViewModel viewModel;
    private FragmentListReservationBinding reserveListBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log1.d(TAG, "onCreateView()");
        viewModel = new ViewModelProvider(this).get(ReservationViewModel.class);
        reserveListBinding = FragmentListReservationBinding.inflate(inflater, container, false);
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

    class ReserveAdapter extends PagedListAdapter<LectureHallEntity, ReserveVHolder> {

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
            LectureHallEntity item = getItem(position);

            //-----
            if (item != null) {
                holder.itemReserveBinding.itemTitleClassroom.setText(String.format("%s", item.getTitle()));
                if (item.getIsActive() == 0) {
                    Log1.d(TAG, "----------> hasEnd availble");
                    // incomplete
                    //holder.itemReserveBinding.reserveDateLyt.setVisibility(View.VISIBLE);
                    //----
                    // holder.itemReserveBinding.itemStartTime.setText(String.format(" %s",  item.getReserveStartTime()));
                    //holder.itemReserveBinding.itemEndTime.setText(String.format("%s", item.getReserveEndTime()));
                    holder.itemReserveBinding.statImageView.setImageTintList(ColorStateList.valueOf(R.color.success_100));
                } else {
                    // at status complete time reserve, here update status in db
                    Log1.d(TAG, "----------> hasEnd not availble");
                    holder.itemReserveBinding.statImageView.setImageTintList(ColorStateList.valueOf(R.color.is_error_stroke_color));

                }
            } else {
                Log1.d(TAG, "----------> no data here add placeholder");

            }

            //-------action
            holder.itemReserveBinding.getRoot().setOnClickListener(view -> {
                if (item != null) {
                    Log1.d(TAG, "---------->lecture hall is avail can Go to reserve ");
                    //avail can Go to reserve
                    Bundle args = new Bundle();
                    args.putInt(KEY_EXTRA_LECTURE_ID, item.getLectureHallId());
                    Navigation.findNavController(view).navigate(R.id.action_reserve_listFragment_to_reserveFragment, args);
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
