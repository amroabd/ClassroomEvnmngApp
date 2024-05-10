package com.is.classroomevnmngapp.utils;


import android.content.Context;
import android.widget.ArrayAdapter;

import com.is.classroomevnmngapp.R;
import com.is.classroomevnmngapp.utils.spinner.ListSpinner;

import java.util.ArrayList;
import java.util.List;


public class ArraySpinnerAdapter {
    //declare
    private final List<ListSpinner>mainDataList;
    private final List<String>subDataList;

    public ArraySpinnerAdapter(List<ListSpinner> mainDataList) {
        this.mainDataList = mainDataList;
        subDataList=new ArrayList<>();
        assignSubData();
    }

    public ArrayAdapter<String> getArrayAdapter(Context context){
     return new ArrayAdapter<>(context, R.layout.list_item_spinner, R.id.text_nameSpinner, subDataList);
    }

    //
    private void assignSubData(){
        for (int i=0;i<mainDataList.size();i++)
             subDataList.add(mainDataList.get(i).getNameSpinner());
    }

    public int getId(int pos) {
      if (pos>0)return mainDataList.get(pos).getIdSpinner();
       return 0;
    }

}
