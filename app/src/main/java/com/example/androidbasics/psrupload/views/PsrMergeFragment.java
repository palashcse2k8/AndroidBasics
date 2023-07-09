package com.example.androidbasics.psrupload.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentPsrMergeBinding;
import com.example.androidbasics.psrupload.viewmodels.PSRViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PsrMergeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PsrMergeFragment extends Fragment {
    FragmentPsrMergeBinding binding;

    PSRViewModel vm;

    public PsrMergeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(PSRViewModel.class);;
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

        binding.btnSubmitFinal.setOnClickListener(v -> {
//            NavHostFragment.findNavController(PsrMergeFragment.this)
//                    .navigate(R.id.action_psr_submission);

            Fragment fragment = new PsrSubmissionFragment();
            String tag = fragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();
        });
    }
}