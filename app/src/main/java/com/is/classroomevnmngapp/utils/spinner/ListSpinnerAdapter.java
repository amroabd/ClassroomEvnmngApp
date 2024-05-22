package com.is.classroomevnmngapp.utils.spinner;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.is.classroomevnmngapp.R;

import java.util.List;

public class ListSpinnerAdapter extends ArrayAdapter<ListSpinner> {
    private static final String TAG =ListSpinnerAdapter.class.getSimpleName() ;
    //define variables dependency
    private final LayoutInflater layoutInflater;
    private final List<ListSpinner> listSpinner1;
    private int selectedIndex;
    private final int selectedColor = Color.parseColor("#abcdef");

    private static class ViewHolder {
        TextView nameSpinner;
    }

    //constructor
    public ListSpinnerAdapter(@NonNull Context paramContext, @NonNull List<ListSpinner> paramList) {
        super(paramContext, R.layout.list_item_spinner, paramList);
        this.layoutInflater = LayoutInflater.from(paramContext);
        this.listSpinner1 = paramList;
        selectedIndex = -1;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int position) {
        selectedIndex = position;
        notifyDataSetChanged();
    }


    private View getInflate(@LayoutRes int resource, ViewGroup root) {
        return layoutInflater.inflate(resource, root,false);
    }

    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = getInflate(R.layout.list_spinner_drop, viewGroup);
            viewHolder.nameSpinner = convertView.findViewById(R.id.text_nameSpinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (listSpinner1 != null) {
            ListSpinner listSpinner = getItem(position);
            viewHolder.nameSpinner.setText(listSpinner.getNameSpinner());
        }
        return convertView;
    }

    //@SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup paramViewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = getInflate(R.layout.list_spinner_drop, null);
            viewHolder.nameSpinner = convertView.findViewById(R.id.text_nameSpinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (listSpinner1 != null) {
            ListSpinner listSpinner = getItem(position);
            viewHolder.nameSpinner.setText(listSpinner.getNameSpinner());
            //textView1.setTextColor(Color.WHITE);
            if (selectedIndex != -1 && position == selectedIndex) {
                viewHolder.nameSpinner.setBackgroundColor(selectedColor);
            } else {
                viewHolder.nameSpinner.setBackgroundColor(Color.WHITE);
            }
        }
        return convertView;
    }


    public int getId(int position) {
        return getItem(position).getIdSpinner();
    }

    public String getId_s(int paramInt) {
        return getItem(paramInt).getIdSpinnerS();
    }

    public String getNameItemSelected(int position) {
        return getItem(position).getNameSpinner();
    }

    //return position item  as spinnerId received
    public int getPosition(int spinnerId) {
        byte b = 0;
        for (int i = 0; ; i++) {
            if (i < this.listSpinner1.size()) {
                if (spinnerId == this.listSpinner1.get(i).getIdSpinner())
                    return i;
            } else {
                return b;
            }
        }
    }

    //return position item  as spinnerId received
    public int getPosition(String spinner_id) {
        if (spinner_id==null) {
            Log.e(TAG, "PARAM spinner_id IS null.");
            return 0;
        }
        byte b = 0;
        for (int i = 0; ; i++) {
            if (i < this.listSpinner1.size()) {
                if (spinner_id.equals(this.listSpinner1.get(i).getIdSpinnerS()))
                    return i;
            } else {
                return b;
            }
        }
    }


}
