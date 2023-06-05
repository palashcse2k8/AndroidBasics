package com.example.androidbasics.psrupload.views;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentPsrUploadBinding;
import com.example.androidbasics.psrupload.models.PSRStatus;
import com.example.androidbasics.psrupload.viewmodels.BitMapResource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PsrUploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PsrUploadFragment extends Fragment {


    FragmentPsrUploadBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    BitMapResource vm;

    public PsrUploadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PsrUploadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PsrUploadFragment newInstance(String param1, String param2) {
        PsrUploadFragment fragment = new PsrUploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentPsrUploadBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnUpload.setOnClickListener(view1 -> NavHostFragment.findNavController(PsrUploadFragment.this)
                .navigate(R.id.action_psr_scanner));

        vm = ViewModelProviders.of(getActivity()).get(BitMapResource.class);

        binding.labelName.setText(vm.getUser().getValue().fullName);
        binding.labelAcNumber.setText(vm.getUser().getValue().accountNumber);
        binding.labelTin.setText(vm.getUser().getValue().tinNumber);

        binding.btnUpload.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( getContext(), R.layout.spinner_item, getAssessmentYear());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner1.setAdapter(adapter);
        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    String item = parent.getItemAtPosition(position).toString();
                    vm.getUser().getValue().setSelectedAssessmentYear(item);
                }
                updateStatus(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void updateStatus (int position) {

        if (position == 0) {
            binding.btnUpload.setVisibility(View.GONE);
            binding.labelTaxAssessmentStatus.setText("");
        } else {
            String psrStatusPreviousYear = vm.getUser().getValue().psrStatusPreviousYear;
            String psrStatusCurrentYear = vm.getUser().getValue().psrStatusCurrentYear;

            if(position == 1) {
                setText(psrStatusPreviousYear);
            } else if (position == 2) {
                setText(psrStatusCurrentYear);
            } else {
                Log.d("","Not in the range");
            }
        }
    }


    public void setText (String Status) {
        if(Status.equalsIgnoreCase(PSRStatus.Updated.name()) || Status.equalsIgnoreCase(PSRStatus.Submitted.name()) ) {
            binding.labelTaxAssessmentStatus.setText(Status);
            binding.labelTaxAssessmentStatus.setTextColor(Color.GRAY);
            binding.btnUpload.setVisibility(View.GONE);
        } else {
            binding.labelTaxAssessmentStatus.setText(Status);
            binding.labelTaxAssessmentStatus.setTextColor(Color.RED);
            binding.btnUpload.setVisibility(View.VISIBLE);
        }
    }

    public List<String> getAssessmentYear () {

        List<String> assessmentYears = new ArrayList<>();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        String currentSession;
        String previousSession;
        String separator = "-";

        if (currentMonth<7) {
            previousSession = (currentYear - 2) + separator + (currentYear-1);
            currentSession = (currentYear - 1) + separator + currentYear;
        } else {
            previousSession = (currentYear - 1) + separator + currentYear;
            currentSession = (currentYear) + separator + (currentYear + 1);
        }
//        Log.d("getAssessmentYear : ",previousSession+ " and " + currentSession);
        assessmentYears.add("Select Tax Assessment Year");
        assessmentYears.add(previousSession);
        assessmentYears.add(currentSession);

        return assessmentYears;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}