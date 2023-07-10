package com.example.androidbasics.psrupload.views;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentPsrMergeBinding;
import com.example.androidbasics.psrupload.viewmodels.PSRViewModel;

public class PsrMergeFragment extends Fragment {
    FragmentPsrMergeBinding binding;

    PSRViewModel vm;

    public PsrMergeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(PSRViewModel.class);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Submit PSR");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPsrMergeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.mergeImageView.setImageBitmap(vm.getUser().getValue().psrBitmap);

        binding.tvTermsCondition.setOnClickListener(v -> {
            binding.checkbox.setChecked(!binding.checkbox.isChecked());
        });
        binding.btnSubmitFinal.setOnClickListener(v -> {
//            NavHostFragment.findNavController(PsrMergeFragment.this)
//                    .navigate(R.id.action_psr_submission)

            if(!binding.checkbox.isChecked()){
                Toast toast = Toast.makeText(getContext(), "Please check the terms and conditions!", Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.RED);
                toast.show();
                return;
            }
            Fragment fragment = new PsrSubmissionFragment();
            String tag = fragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();
        });
    }
}