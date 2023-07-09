package com.example.androidbasics.psrupload.utils;

import static com.example.androidbasics.psrupload.utils.DateTimeUtil.getSimpleDate;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidbasics.R;
import com.example.androidbasics.apihandle.QueryResponseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    private Context context = null;
    private List<QueryResponseModel> results = null;

    public ListViewAdapter(Context context, List<QueryResponseModel> arr) {
        this.context = context;
        this.results = arr;
    }

    @Override
    public int getCount() {
        return results.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_psr_listview_item, null);

        TextView lblAssessmentYear = (TextView) convertView.findViewById(R.id.lblAssessmentYear);
        TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
        TextView lblTin = (TextView) convertView.findViewById(R.id.lblTin);
        TextView lblStatus = (TextView) convertView.findViewById(R.id.lblStatus);
        TextView lblDate = (TextView) convertView.findViewById(R.id.lblDate);

        lblAssessmentYear.setText(results.get(position).getAssessmentYear());
        lblName.setText(results.get(position).getCustomerName());
        lblTin.setText(results.get(position).getTin());


        String status = results.get(position).getTrStatus();
        lblStatus.setText(status);
//        lblStatus.setTextColor(Color.GREEN);
        if(status.equalsIgnoreCase("Rejected")){
            lblStatus.setTextColor(Color.RED);
        }

        String date = results.get(position).getTrUploadDate();
        lblDate.setText("Date: " + getSimpleDate(date));

        return convertView;
    }
}