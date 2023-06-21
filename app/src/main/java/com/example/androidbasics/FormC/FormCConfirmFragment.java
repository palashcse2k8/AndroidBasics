package com.example.androidbasics.FormC;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentFormCBinding;
import com.example.androidbasics.databinding.FragmentFormCConfirmBinding;
import com.example.androidbasics.psrupload.views.PsrUploadFragment;

public class FormCConfirmFragment extends Fragment {

    FragmentFormCConfirmBinding binding;
    FormCViewModel viewModel;

    public static FormCConfirmFragment newInstance() {
        return new FormCConfirmFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(FormCViewModel.class);
        binding = FragmentFormCConfirmBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromViewModel();
        binding.btnSubmit.setOnClickListener( v -> {

            if(binding.tvWalletPin.getText().toString().equalsIgnoreCase("")){
                Toast toast = Toast.makeText(getContext(), "Pin Required!", Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.RED);
                toast.show();
            } else {
                Fragment fragment = new FormCSubmissionFragment();
                String tag = fragment.getClass().getSimpleName();
                getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();
            }
//            NavHostFragment.findNavController(FormCConfirmFragment.this).navigate(R.id.action_form_c_submission);
        });
    }

    public void getDataFromViewModel(){
        binding.tvName.setText(viewModel.getName().getValue());
        binding.tvAddress.setText(viewModel.getAddress().getValue());
        binding.tvCountry.setText(viewModel.getCountry().getValue());
        binding.tvCurrency.setText(viewModel.getCurrency().getValue());
        binding.tvAmount.setText(viewModel.getAmount().getValue());
        binding.tvPurpose.setText(viewModel.getPurpose().getValue());
        binding.tvBank.setText(viewModel.getBankName().getValue());
        binding.tvNumber.setText(viewModel.getPhoneNumber().getValue());
    }
}
