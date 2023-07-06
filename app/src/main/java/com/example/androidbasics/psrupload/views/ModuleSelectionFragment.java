package com.example.androidbasics.psrupload.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidbasics.FormC.FormCFragment;
import com.example.androidbasics.OCR.NIDFragment;
import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentModuleSelectionBinding;

public class ModuleSelectionFragment extends Fragment {

    FragmentModuleSelectionBinding binding;

    public ModuleSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentModuleSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnPsrUpload.setOnClickListener(view1 -> {
//            NavHostFragment.findNavController(ModuleSelectionFragment.this)
//                    .navigate(R.id.action_enter_psr_module)

            Fragment fragment = new PsrUploadFragment();
            String tag = fragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();

        });

        binding.btnFormC.setOnClickListener(view1 -> {
//            NavHostFragment.findNavController(ModuleSelectionFragment.this)
//                    .navigate(R.id.action_enter_from_c_module);

            Fragment fragment = new FormCFragment();
            String tag = fragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();

        });
        binding.btnCamera.setOnClickListener(view1 -> {
//            NavHostFragment.findNavController(ModuleSelectionFragment.this)
//                    .navigate(R.id.action_enter_from_c_module);

            Fragment fragment = new NIDFragment();
            String tag = fragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();

        });

//        binding.btnFormC.setOnClickListener(view1 -> {
//            NavHostFragment.findNavController(ModuleSelectionFragment.this)
//                    .navigate(R.id.action_temp);
//        });
    }
}