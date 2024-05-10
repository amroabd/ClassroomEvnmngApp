package com.is.classroomevnmngapp.ui.user.classroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.is.classroomevnmngapp.databinding.FragmentUserClassroomBinding;
import com.is.classroomevnmngapp.databinding.RowItemClassroomBinding;

public class ClassRoomUserFragment extends Fragment {

    private ClassRoomUserViewModel classRoomUserViewModel;
    FragmentUserClassroomBinding classroomBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        classRoomUserViewModel = new ViewModelProvider(this).get(ClassRoomUserViewModel.class);
             classroomBinding=FragmentUserClassroomBinding.inflate(inflater);


        return classroomBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        classRoomUserViewModel.getText().observe(getViewLifecycleOwner(), s -> {

        });

        ItemAdapter itemAdapter=new ItemAdapter();
        classroomBinding.listview.setAdapter(itemAdapter);
    }

    class ItemAdapter extends RecyclerView.Adapter<VHolder>{

        @NonNull
        @Override
        public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RowItemClassroomBinding itemClassroomBinding;
            itemClassroomBinding=RowItemClassroomBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
             return new VHolder(itemClassroomBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull VHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 9;
        }
    }
    class VHolder extends RecyclerView.ViewHolder {
        RowItemClassroomBinding itemClassroomBinding;
        public VHolder(@NonNull RowItemClassroomBinding itemClassroomBinding) {
            super(itemClassroomBinding.getRoot());
            this.itemClassroomBinding=itemClassroomBinding;
        }
    }
}