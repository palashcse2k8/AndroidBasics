package com.example.androidbasics.psrupload.views;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidbasics.databinding.FragmentPsrUploadMainBinding;

public class PsrUploadMainFragment extends Fragment {

    FragmentPsrUploadMainBinding binding;
    public PsrUploadMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Proof of Submission of Return");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPsrUploadMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}