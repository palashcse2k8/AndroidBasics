package com.example.androidbasics.OCR;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidbasics.R;

//import bd.com.sonalibank.sonalie_sheba.R;
//import bd.com.sonalibank.sonalie_sheba.ui.eye_blink.EyeBlinkFragment;
//import bd.com.sonalibank.sonalie_sheba.utils.Utils;

public class NIDFragment extends Fragment {

    Button button_next;
    TextView nid_font_txt;
    TextView nid_back_txt;
    ImageView nid_font;
    ImageView nid_back;
    String back_nid_fn = null;
    String  font_nid_fn = null;
    String  nid_name = null;
    String  nid_num = null;
    String  nid_dob = null;
    boolean nid_isNID = false;
    boolean nid_isDOB = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.nid_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_next = view.findViewById(R.id.button_next);

        nid_font_txt = view.findViewById(R.id.nid_front_txt);
        nid_back_txt = view.findViewById(R.id.nid_back_txt);
        nid_font = view.findViewById(R.id.nid_front);
        nid_back = view.findViewById(R.id.nid_back);

        nid_font_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new OcrFragment();
                String tag = OcrFragment.class.getSimpleName();

               fragment.setTargetFragment(NIDFragment.this, OcrFragment.REQ_CODE_OCR);
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, tag).addToBackStack(null).commit();

            }
        });

        nid_back_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new OcrFragment();
                String tag = OcrFragment.class.getSimpleName();

                fragment.setTargetFragment(NIDFragment.this, OcrFragment.REQ_CODE_IMG_ONLY);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, tag).addToBackStack(null).commit();

            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (font_nid_fn == null) {
                    Toast.makeText(v.getContext(), "Please Upload NID Front Image ", Toast.LENGTH_LONG).show();
                } else if (back_nid_fn == null) {
                    Toast.makeText(v.getContext(), "Please Upload NID Back Image ", Toast.LENGTH_LONG).show();
                } else if (!nid_isDOB || !nid_isNID) {
                    Toast.makeText(v.getContext(), "NID Front page not recognized.\nPlease Upload NID Front Image ", Toast.LENGTH_LONG).show();
                }
                else
                {
//                    EyeBlinkFragment fragment = EyeBlinkFragment.newInstance(nid_num, nid_dob, font_nid_fn, back_nid_fn);
//
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == OcrFragment.REQ_CODE_OCR) {
            font_nid_fn = null;
            nid_name = null;
            nid_num = null;
            nid_dob = null;
            nid_isNID = false;
            nid_isDOB = false;
            if (data != null) {

                font_nid_fn = data.getStringExtra(OcrFragment.RESPONSE_IMG);
                nid_name = data.getStringExtra(OcrFragment.RESPONSE_name);
                nid_num = data.getStringExtra(OcrFragment.RESPONSE_nid);
                nid_dob = data.getStringExtra(OcrFragment.RESPONSE_dob);
                nid_isNID = data.getBooleanExtra(OcrFragment.RESPONSE_isNID,false);
                nid_isDOB = data.getBooleanExtra(OcrFragment.RESPONSE_isDOB,false);

            }

        } else if (requestCode == OcrFragment.REQ_CODE_IMG_ONLY) {
            back_nid_fn=null;
            if (data != null) {
                back_nid_fn = data.getStringExtra(OcrFragment.RESPONSE_IMG);
            }

        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (font_nid_fn != null) {
            Bitmap bitmap = Utils.getBitmap(nid_back.getContext(), font_nid_fn);
            nid_font.setImageBitmap(bitmap);
            //nid_font_txt.setVisibility(View.GONE);

        }
        if (back_nid_fn != null) {
            Bitmap bitmap = Utils.getBitmap(nid_back.getContext(), back_nid_fn);
            nid_back.setImageBitmap(bitmap);
            //nid_back_txt.setVisibility(View.GONE);

            // commint

        }

    }
}