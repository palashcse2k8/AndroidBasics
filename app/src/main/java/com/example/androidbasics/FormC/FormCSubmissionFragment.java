package com.example.androidbasics.FormC;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentFormCContinueBinding;
import com.example.androidbasics.databinding.FragmentFormCSubmissionBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormCSubmissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormCSubmissionFragment extends Fragment {

    FragmentFormCSubmissionBinding binding;
    FormCViewModel viewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormCSubmissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormCSubmissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormCSubmissionFragment newInstance(String param1, String param2) {
        FormCSubmissionFragment fragment = new FormCSubmissionFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity()).get(FormCViewModel.class);
        binding = FragmentFormCSubmissionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromViewModel();
        binding.btnDone.setOnClickListener(v-> {
            NavHostFragment.findNavController(FormCSubmissionFragment.this).navigate(R.id.action_form_c_toHome);
        });
    }

    public void getDataFromViewModel(){
        binding.labelToken.setText("123456789");
        binding.labelCurrency.setText(viewModel.getCurrency().getValue());
        binding.labelAmount.setText(viewModel.getAmount().getValue());
        binding.labelUploadDate.setText(getCurrentDate());
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }
}