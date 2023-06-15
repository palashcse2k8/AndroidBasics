package com.example.androidbasics.OCR;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import bd.com.sonalibank.sonalie_sheba.R;
import bd.com.sonalibank.sonalie_sheba.utils.Utils;


public class ResultFragment extends Fragment {


    private static final String ARG_NID = "ARG_NID";
    private static final String ARG_DOB = "ARG_DOB";
    private static final String ARG_PHOTO = "ARG_PHOTO";
    private static final String ARG_NID_FRONT = "ARG_NID_FRONT";
    private static final String ARG_NID_BACK = "ARG_NID_BACK";


    private String NID;
    private String DOB;
    private String PHOTO;
    private String NID_FRONT;
    private String NID_BACK;

    private TextView viewNID;
    private TextView viewDOB;
    private ImageView viewPHOTO;
    private ImageView viewNID_FRONT;
    private ImageView viewNID_BACK;

    public ResultFragment() {
        // Required empty public constructor
    }


    public static ResultFragment newInstance(String nid, String dob, String photo, String nid_front, String nid_back) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NID, nid);
        args.putString(ARG_DOB, dob);
        args.putString(ARG_PHOTO, photo);
        args.putString(ARG_NID_FRONT, nid_front);
        args.putString(ARG_NID_BACK, nid_back);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            NID = getArguments().getString(ARG_NID);
            DOB = getArguments().getString(ARG_DOB);
            PHOTO = getArguments().getString(ARG_PHOTO);
            NID_FRONT = getArguments().getString(ARG_NID_FRONT);
            NID_BACK = getArguments().getString(ARG_NID_BACK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_result, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewNID=view.findViewById(R.id.nid);
        viewDOB=view.findViewById(R.id.dob);
        viewPHOTO=view.findViewById(R.id.photo);
        viewNID_FRONT=view.findViewById(R.id.nid_front);
        viewNID_BACK=view.findViewById(R.id.nid_back);


        viewNID.setText(NID);
        viewDOB.setText(DOB);

        viewPHOTO.setImageBitmap(Utils.getBitmap(view.getContext(),PHOTO));
        viewNID_FRONT.setImageBitmap(Utils.getBitmap(view.getContext(),NID_FRONT));
        viewNID_BACK.setImageBitmap(Utils.getBitmap(view.getContext(),NID_BACK));


    }
}