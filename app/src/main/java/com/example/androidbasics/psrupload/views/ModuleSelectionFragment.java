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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModuleSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModuleSelectionFragment extends Fragment {

    FragmentModuleSelectionBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ModuleSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModuleSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModuleSelectionFragment newInstance(String param1, String param2) {
        ModuleSelectionFragment fragment = new ModuleSelectionFragment();
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