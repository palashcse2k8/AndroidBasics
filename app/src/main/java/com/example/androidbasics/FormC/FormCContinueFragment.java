package com.example.androidbasics.FormC;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentFormCContinueBinding;
import com.example.androidbasics.psrupload.views.PsrUploadFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormCContinueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormCContinueFragment extends Fragment {

    FragmentFormCContinueBinding binding;
    FormCViewModel viewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormCContinueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormCContinueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormCContinueFragment newInstance(String param1, String param2) {
        FormCContinueFragment fragment = new FormCContinueFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(requireActivity()).get(FormCViewModel.class);
        binding = FragmentFormCContinueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void setOnFocusListener() {
        binding.address.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.formCAddressContainer.setHelperText(validateAddress());
            }
        });

        binding.senderPhone.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.formCPhoneContainer.setHelperText(validatePhone());
            }
        });
    }

    private void setSpinnerError(Spinner spinner, String error) {
        Log.d("setSpinnerError","setSpinnerError with " + error );
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
//            selectedTextView.setError("Select anyone!"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
//            selectedTextView.setText(error); // actual error message
//            spinner.performClick(); // to open the spinner list if error is found.
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setOnFocusListener();
        binding.btnContinue.setOnClickListener(v -> {
            if (isValid()) {
                updateViewModel();

                Fragment fragment = new FormCConfirmFragment();
                String tag = fragment.getClass().getSimpleName();
                getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();

//                NavHostFragment.findNavController(FormCContinueFragment.this).navigate(R.id.action_form_c_confirm);
            } else {
                Toast toast = Toast.makeText(getContext(), "Please fix the error to continue!", Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.RED);
                toast.show();
            }
        });
    }

    private void updateViewModel() {
        viewModel.setAddress(binding.address.getText().toString());
        viewModel.setPurpose(binding.spinnerPurpose.getSelectedItem().toString());
        viewModel.setPhoneNumber(binding.senderPhone.getText().toString());
        viewModel.setBankName(binding.spinnerBankName.getSelectedItem().toString());
    }

    private String validatePurpose() {
        Log.d("","validatePurpose");
        String purpose = binding.spinnerPurpose.getSelectedItem().toString();
        String errorText = null;
        if (purpose.equals("Select Purpose")) {
            errorText = "Please select a Purpose!";
        }
        if (purpose == null) {
            errorText = "Purpose is required!";
        }

        if (errorText != null)
            setSpinnerError(binding.spinnerPurpose, errorText);

        Log.d("","validatePurpose " + errorText);
        return errorText;
    }

    private String validateBankName() {
        String bankName = binding.spinnerBankName.getSelectedItem().toString();
        String errorText = null;
        if (bankName.equals("Select Bank Name")) {
            errorText = "Please select a Bank!";
        }
        if (bankName == null) {
            errorText = "Bank Name is required!";
        }
        if (errorText != null)
            setSpinnerError(binding.spinnerBankName, errorText);

        return errorText;
    }

    private String validateAddress() {
        String address = binding.address.getText().toString();

        if(address.length() == 0) {
            return "Address is required!";
        }

        if (address.length() < 5) {
            return "Address should be minimum of 6 characters!";
        }
        if (address == null) {
            return "Address is required!";
        }

        return null;
    }

    private String validatePhone() {
        String phoneNumber = binding.senderPhone.getText().toString();

        if (phoneNumber.length() == 0) {
            return "Can't be null!";
        }
        if (!phoneNumber.matches(".*[0-9].*")) {
            return "Must be all Digits";
        }
        if (phoneNumber.length() != 11) {
            return "Must be 11 Digits";
        }
        return null;
    }


    public void init() {
        ArrayAdapter<String> purposeAdaptor = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, getPurposeList());
        ArrayAdapter<String> bankNameAdaptor = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, getBankList());
        purposeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankNameAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerBankName.setAdapter(bankNameAdaptor);
        binding.spinnerPurpose.setAdapter(purposeAdaptor);
    }

    public boolean isValid() {
        binding.formCAddressContainer.setHelperText(validateAddress());
        binding.formCPhoneContainer.setHelperText(validatePhone());
        return validateAddress() == null && validatePhone() == null && validatePurpose() == null && validateBankName() == null;
    }

    public List<String> getPurposeList() {

        List<String> countryList = new ArrayList<>();

        String list[] = new String[]{"Travel", "Food", "Family", "ICT", "Travel", "Food", "Family", "ICT"};

        countryList.add("Select Purpose");
        countryList.addAll(Arrays.asList(list));
        return countryList;
    }

    public List<String> getBankList() {

        List<String> countryList = new ArrayList<>();

        String list[] = new String[]{"AB Bank Limited", "Agrani Bank Limited", "Al-Arafah Islami Bank Limited", "Bangladesh Commerce Bank Limited", "Bangladesh Development Bank Limited", "Bangladesh Krishi Bank", "Bank Al-Falah Limited", "Bank Asia Limited", "BASIC Bank Limited", "Bengal Commercial Bank Limited", "BRAC Bank Limited", "Citibank N.A", "Citizens Bank PLC", "Commercial Bank of Ceylon Limited", "Community Bank Bangladesh Limited", "Dhaka Bank Limited", "Dutch-Bangla Bank Limited", "Eastern Bank Limited", "EXIM Bank Limited", "First Security Islami Bank Limited", "Global Islami Bank Limited", "Habib Bank Ltd.", "ICB Islamic Bank Ltd.", "IFIC Bank PLC", "Islami Bank Bangladesh Ltd", "Jamuna Bank Ltd", "Janata Bank Limited", "Meghna Bank Limited", "Mercantile Bank Limited", "Midland Bank Limited", "Modhumoti Bank Limited", "Mutual Trust Bank Limited", "National Bank Limited", "National Bank of Pakistan", "National Credit & Commerce Bank Ltd", "NRB Bank Limited", "NRB Commercial Bank Limited", "One Bank Limited", "Padma Bank Limited", "Premier Bank Limited", "Prime Bank Ltd", "Probashi Kollyan Bank", "Pubali Bank Limited", "Rajshahi Krishi Unnayan Bank", "Rupali Bank Limited", "Shahjalal Islami Bank Limited", "Shimanto Bank Limited", "Social Islami Bank Ltd.", "Sonali Bank Limited", "South Bangla Agriculture & Commerce Bank Limited", "Southeast Bank Limited", "Standard Bank Limited", "Standard Chartered Bank", "State Bank of India", "The City Bank Ltd.", "The Hong Kong and Shanghai Banking Corporation. Ltd.", "Trust Bank Limited", "Union Bank Limited", "United Commercial Bank PLC", "Uttara Bank Limited", "Woori Bank"};

        countryList.add("Select Bank Name");
        countryList.addAll(Arrays.asList(list));
        return countryList;
    }

}