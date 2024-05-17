package com.is.classroomevnmngapp.ui.setting.init_classroom;

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
import com.is.classroomevnmngapp.databinding.FragmentUserClassroomBinding;
import com.is.classroomevnmngapp.databinding.RowItemClassroomBinding;
import com.is.classroomevnmngapp.utils.Log1;

import java.text.MessageFormat;
import java.util.Locale;

import static com.is.classroomevnmngapp.data.source.local.entities.LectureHallEntity.DIFF_CALLBACK;

public class ClassRoomListFragment extends Fragment {
    private static final String TAG = "ClassRoomListFragment";
    private ClassRoomViewModel viewModel;
    FragmentUserClassroomBinding classroomBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ClassRoomViewModel.class);
             classroomBinding=FragmentUserClassroomBinding.inflate(inflater);


        return classroomBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //add
         classroomBinding.addClassroom.setOnClickListener(view1 -> {
             Navigation.findNavController(view1).navigate(R.id.action_classroom_listFragment_to_add_classroomFragment);
         });

         //------
         initListView();
    }
    private void initListView(){
       final ItemAdapter itemAdapter= new ItemAdapter();
        viewModel.getAllLectureHall().observe(getViewLifecycleOwner(), pagedList -> {
                    itemAdapter.submitList(pagedList);
                    int siz= pagedList.size();
                    Log1.d(TAG,"size :"+siz);
                     if (siz>0){
                         classroomBinding.textPlaceholder.setVisibility(View.GONE);
                        // classroomBinding.listview.setAdapter(itemAdapter);
                     }
                });

        classroomBinding.listview.setAdapter(itemAdapter);
    }

    //====================================================

    static class ItemAdapter extends PagedListAdapter<LectureHallEntity, VHolder> {

        public ItemAdapter() {
            super(DIFF_CALLBACK);
        }

        @NonNull
        @Override
        public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RowItemClassroomBinding itemClassroomBinding;
            itemClassroomBinding=RowItemClassroomBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
             return new VHolder(itemClassroomBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull VHolder holder, int position) {
         LectureHallEntity entity=getItem(position);
         //-----
            if (entity!=null) {
                StringBuilder builder=new StringBuilder();
                holder.itemClassroomBinding.itemCapacity.setText(String.format(Locale.ENGLISH, "%d", entity.getCapacity()));
                holder.itemClassroomBinding.itemTitleClassroom.setText(MessageFormat.format("{0}", entity.getTitle()));
                if (entity.getHasProjector()==1){
                    builder.append("1-Projector").append("\r");
                }
                if (entity.getAcStatus()==1){
                    builder.append("2-AC").append("\r");
                }
                if (entity.getLightingStatus()==1){
                    builder.append("3-Lighting");
                }
                holder.itemClassroomBinding.itemFoundDevices.setText(MessageFormat.format("{0}",builder.toString()));

            }
            Log1.d(TAG,"getItemCount :"+getItemCount());
        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }
    }

    private static class VHolder extends RecyclerView.ViewHolder {
        RowItemClassroomBinding itemClassroomBinding;
        public VHolder(@NonNull RowItemClassroomBinding itemClassroomBinding) {
            super(itemClassroomBinding.getRoot());
            this.itemClassroomBinding=itemClassroomBinding;
        }
    }
}