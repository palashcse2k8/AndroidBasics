package com.example.androidbasics.FormC;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentFormCBinding;
import com.example.androidbasics.databinding.FragmentFormCConfirmBinding;

public class FormCConfirmFragment extends Fragment {

    FragmentFormCConfirmBinding binding;

    public static FormCConfirmFragment newInstance() {
        return new FormCConfirmFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFormCConfirmBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
