package com.example.androidbasics.psrupload.utils;

import static com.example.androidbasics.psrupload.utils.DateTimeUtil.getSimpleDate;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidbasics.R;
import com.example.androidbasics.apihandle.QueryResponseModel;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private final Context context;
    private final List<QueryResponseModel> results;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_EMPTY = 1;

    public ListViewAdapter(Context context, List<QueryResponseModel> arr) {
        this.context = context;
        this.results = arr;
    }

    @Override
    public int getCount() {
        if (results.isEmpty()) {
            // Return 1 for the empty list layout
            return 1;
        } else {
            // Return the actual item count
            return results.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (results.isEmpty()) {
            // Return the view type for the empty list layout
            return VIEW_TYPE_EMPTY;
        } else {
            // Return the view type for the regular item layout
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getViewTypeCount() {
        // Return the number of view types (including the empty list layout)
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (getItemViewType(position) == VIEW_TYPE_EMPTY) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_psr_listview_no_item, viewGroup, false);

//            // Adjust parent ViewGroup layout parameters to fill parent area
//            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            viewGroup.setLayoutParams(layoutParams);
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_psr_listview_item, viewGroup, false);

            // Bind data to the regular item layout views
            TextView lblAssessmentYear = convertView.findViewById(R.id.lblAssessmentYear);
            TextView lblName = convertView.findViewById(R.id.lblName);
            TextView lblTin = convertView.findViewById(R.id.lblTin);
            TextView lblStatus = convertView.findViewById(R.id.lblStatus);
            TextView lblDate = convertView.findViewById(R.id.lblDate);

            QueryResponseModel item = results.get(position);
            lblAssessmentYear.setText(item.getAssessmentYear());
            lblName.setText(item.getCustomerName());
            lblTin.setText(item.getTin());

            String status = item.getTrStatus();
            lblStatus.setText(status);
            if (status.equalsIgnoreCase("Rejected")) {
                lblStatus.setTextColor(Color.RED);
            }

            String date = item.getTrUploadDate();
            lblDate.setText("Date: " + getSimpleDate(date));
        }

        return convertView;
    }
}