package com.example.androidbasics.FormC;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentFormCBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FormCFragment extends Fragment {
    FragmentFormCBinding binding;
    FormCViewModel viewModel;
    Dialog dialog;

    public FormCFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Form-C/Form-C (ICT)");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(FormCViewModel.class);
        binding = FragmentFormCBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setOnFocusListener();
        binding.btnSubmitFinal.setOnClickListener(v -> {
            if (isValid()) {
                updateViewModel();
                if (!binding.checkbox.isChecked()) {
                    Toast toast = Toast.makeText(getContext(), "Please check the terms and conditions!", Toast.LENGTH_SHORT);
                    toast.getView().setBackgroundColor(Color.RED);
                    toast.show();
                    return;
                }
//                NavHostFragment.findNavController(FormCFragment.this).navigate(R.id.action_form_c_continue);
                Fragment fragment = new FormCConfirmFragment();
                String tag = fragment.getClass().getSimpleName();
                getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();

            } else {
                Toast toast = Toast.makeText(getContext(), "Please fix the error to continue!", Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.RED);
                toast.show();
            }
        });
    }

    private void updateViewModel() {
        viewModel.setName(binding.senderName.getText().toString());
        viewModel.setAmount(binding.amount.getText().toString());
        viewModel.setCountryName(binding.spinnerCountry.getText().toString());
        viewModel.setCurrencyName(binding.spinnerCurrency.getText().toString());
        viewModel.setAddress(binding.address.getText().toString());
        viewModel.setPurpose(binding.spinnerPurpose.getText().toString());
        viewModel.setPhoneNumber(binding.senderPhone.getText().toString());
        viewModel.setBankName(binding.spinnerBankName.getText().toString());
    }

    private void setOnFocusListener() {
        binding.senderName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.formCNameContainer.setHelperText(validateName());
            }
        });

        binding.senderPhone.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.formCPhoneContainer.setHelperText(validatePhone());
            }
        });

        binding.address.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.formCAddressContainer.setHelperText(validateAddress());
            }
        });

        binding.amount.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                binding.formCAmountContainer.setHelperText(validateAmount());
            }
        });

        binding.tvTermsCondition.setOnClickListener(v -> {
            binding.checkbox.setChecked(!binding.checkbox.isChecked());
        });
    }

    private void resetForm() {
        binding.amount.setText("");
        binding.senderName.setText("");
        binding.formCNameContainer.setHelperText("Required");
        binding.formCAmountContainer.setHelperText("Required");
    }

    public boolean isValid() {
        String helperText = "";
        helperText = validateName();
        binding.formCNameContainer.setHelperText(helperText);
        helperText = validatePhone();
        binding.formCPhoneContainer.setHelperText(helperText);
        helperText = validatePhone();
        binding.formCAddressContainer.setHelperText(helperText);
        helperText = validateCountry();
        binding.spinnerCountry.setError(helperText);
        helperText = validateBankName();
        binding.spinnerBankName.setError(helperText);
        helperText = validatePurpose();
        binding.spinnerPurpose.setError(helperText);
        helperText = validateCurrency();
        binding.spinnerCurrency.setError(helperText);
        helperText = validateAmount();
        binding.formCAmountContainer.setHelperText(helperText);

//        Log.d("helperText", helperText);

        return helperText == null;
    }

    private String validateName() {
        String senderName = Objects.requireNonNull(binding.senderName.getText()).toString();
        if (senderName.length() < 5) {
            return "Name should be minimum of 6 characters";
        }
        if (senderName == null) {
            return "Name is required!";
        }

        return null;
    }

    private String validatePurpose() {
        Log.d("", "validatePurpose");
        String purpose = binding.spinnerPurpose.getText().toString();
        String errorText = null;
        if (purpose.equals("Select Purpose")) {
            errorText = "Please select a Purpose!";
        }
        if (purpose == null) {
            errorText = "Purpose is required!";
        }

        return errorText;
    }

    private String validateBankName() {
        String bankName = binding.spinnerBankName.getText().toString();
        String errorText = null;
        if (bankName.equals("Select Bank Name")) {
            errorText = "Please select a Bank!";
        }
        if (bankName == null) {
            errorText = "Bank Name is required!";
        }
        return errorText;
    }

    private String validateAddress() {
        String address = binding.address.getText().toString();

        if (address.length() == 0) {
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

//        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
//            return "Its not a valid phone number";
//        }
        return null;
    }

    private String validateAmount() {
        String amount = binding.amount.getText().toString();
        if (amount.length() < 5) {
            return "Amount should be minimum of 5 digits";
        }
        if (amount == null && amount.isEmpty()) {
            return "Amount is required!";
        }

        return null;
    }

    private String validateCountry() {
        String countryName = binding.spinnerCountry.getText().toString();
        if (countryName.equals("Select Country")) {
            return "Please select a country!";
        }
        if (countryName == null) {
            return "Country is required!";
        }

        return null;
    }

    private String validateCurrency() {
        String countryName = binding.spinnerCurrency.getText().toString();
        if (countryName.equals("Select Currency")) {
            return "Please select currency!";
        }
        if (countryName == null) {
            return "Currency is required!";
        }

        return null;
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

    public class SearchableDropdown implements View.OnClickListener {
        List<String> adapterList;

        Context context;

        TextView myTextView;

        Dialog dialog;

        SearchableDropdown(Context context, View view, List<String> list) {
            this.adapterList = list;
            this.context = context;
            myTextView = (TextView) view;
        }

        public void init() {
            dialog = new Dialog(this.context);
            //set  (our custom layout for dialog)
            dialog.setContentView(R.layout.layout_searchable_spinner);

            //set transparent background
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            //show dialog
            dialog.show();

            //initialize and assign variable
            EditText editText = dialog.findViewById(R.id.editText_of_searchableSpinner);
            ListView listView = dialog.findViewById(R.id.listView_of_searchableSpinner);
            //array adapter
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, this.adapterList);
            listView.setAdapter(arrayAdapter);
            //Text-watcher for change data after every text type by user
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //filter arraylist
                    arrayAdapter.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            // listview onitem click listener
            listView.setOnItemClickListener((adapterView, view1, i, l) -> {

                myTextView.setText(arrayAdapter.getItem(i));
//                binding.spinnerCountry.setText(arrayAdapter.getItem(i));
//                        Toast.makeText(getContext(), "Selected:" + arrayAdapter.getItem(i), Toast.LENGTH_SHORT).show();
                //dismiss dialog after choose
                dialog.dismiss();
            });
        }

        @Override
        public void onClick(View v) {
            init();
        }
    }

    public void init() {

        binding.spinnerCountry.setOnClickListener(
            new SearchableDropdown(getContext(), binding.spinnerCountry, getCountryList())
        );

        binding.spinnerBankName.setOnClickListener(new SearchableDropdown(getContext(), binding.spinnerBankName, getBankList()));

        binding.spinnerPurpose.setOnClickListener(new SearchableDropdown(getContext(), binding.spinnerPurpose, getPurposeList()));

        binding.spinnerCurrency.setOnClickListener(new SearchableDropdown(getContext(), binding.spinnerCurrency, getCurrencyList()));

    }

    public List<String> getCountryList() {

        String[] list = new String[]{"United States", "Canada", "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and/or Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France, Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States minor outlying islands", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City State", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zaire", "Zambia", "Zimbabwe"};

        return new ArrayList<>(Arrays.asList(list));
    }

    public List<String> getCurrencyList() {

        List<String> countryList = new ArrayList<>();

        String[] list = new String[]{"AED-United Arab Emirates Dirham", "AFN-Afghan Afghani", "ALL-Albanian Lek", "AMD-Armenian Dram", "ANG-Netherlands Antillean Guilder", "AOA-Angolan Kwanza", "ARS-Argentine Peso", "AUD-Australian Dollar", "AWG-Aruban Florin", "AZN-Azerbaijani Manat", "BAM-Bosnia-Herzegovina Convertible Mark", "BBD-Barbadian Dollar", "BDT-Bangladeshi Taka", "BGN-Bulgarian Lev", "BHD-Bahraini Dinar", "BIF-Burundian Franc", "BMD-Bermudan Dollar", "BND-Brunei Dollar", "BOB-Bolivian Boliviano", "BRL-Brazilian Real", "BSD-Bahamian Dollar", "BTC-Bitcoin", "BTN-Bhutanese Ngultrum", "BWP-Botswanan Pula", "BYN-Belarusian Ruble", "BZD-Belize Dollar", "CAD-Canadian Dollar", "CDF-Congolese Franc", "CHF-Swiss Franc", "CLF-Chilean Unit of Account (UF)", "CLP-Chilean Peso", "CNH-Chinese Yuan (Offshore)", "CNY-Chinese Yuan", "COP-Colombian Peso", "CRC-Costa Rican Colón", "CUC-Cuban Convertible Peso", "CUP-Cuban Peso", "CVE-Cape Verdean Escudo", "CZK-Czech Republic Koruna", "DJF-Djiboutian Franc", "DKK-Danish Krone", "DOP-Dominican Peso", "DZD-Algerian Dinar", "EGP-Egyptian Pound", "ERN-Eritrean Nakfa", "ETB-Ethiopian Birr", "EUR-Euro", "FJD-Fijian Dollar", "FKP-Falkland Islands Pound", "GBP-British Pound Sterling", "GEL-Georgian Lari", "GGP-Guernsey Pound", "GHS-Ghanaian Cedi", "GIP-Gibraltar Pound", "GMD-Gambian Dalasi", "GNF-Guinean Franc", "GTQ-Guatemalan Quetzal", "GYD-Guyanaese Dollar", "HKD-Hong Kong Dollar", "HNL-Honduran Lempira", "HRK-Croatian Kuna", "HTG-Haitian Gourde", "HUF-Hungarian Forint", "IDR-Indonesian Rupiah", "ILS-Israeli New Sheqel", "IMP-Manx pound", "INR-Indian Rupee", "IQD-Iraqi Dinar", "IRR-Iranian Rial", "ISK-Icelandic Króna", "JEP-Jersey Pound", "JMD-Jamaican Dollar", "JOD-Jordanian Dinar", "JPY-Japanese Yen", "KES-Kenyan Shilling", "KGS-Kyrgystani Som", "KHR-Cambodian Riel", "KMF-Comorian Franc", "KPW-North Korean Won", "KRW-South Korean Won", "KWD-Kuwaiti Dinar", "KYD-Cayman Islands Dollar", "KZT-Kazakhstani Tenge", "LAK-Laotian Kip", "LBP-Lebanese Pound", "LKR-Sri Lankan Rupee", "LRD-Liberian Dollar", "LSL-Lesotho Loti", "LYD-Libyan Dinar", "MAD-Moroccan Dirham", "MDL-Moldovan Leu", "MGA-Malagasy Ariary", "MKD-Macedonian Denar", "MMK-Myanma Kyat", "MNT-Mongolian Tugrik", "MOP-Macanese Pataca", "MRU-Mauritanian Ouguiya", "MUR-Mauritian Rupee", "MVR-Maldivian Rufiyaa", "MWK-Malawian Kwacha", "MXN-Mexican Peso", "MYR-Malaysian Ringgit", "MZN-Mozambican Metical", "NAD-Namibian Dollar", "NGN-Nigerian Naira", "NIO-Nicaraguan Córdoba", "NOK-Norwegian Krone", "NPR-Nepalese Rupee", "NZD-New Zealand Dollar", "OMR-Omani Rial", "PAB-Panamanian Balboa", "PEN-Peruvian Nuevo Sol", "PGK-Papua New Guinean Kina", "PHP-Philippine Peso", "PKR-Pakistani Rupee", "PLN-Polish Zloty", "PYG-Paraguayan Guarani", "QAR-Qatari Rial", "RON-Romanian Leu", "RSD-Serbian Dinar", "RUB-Russian Ruble", "RWF-Rwandan Franc", "SAR-Saudi Riyal", "SBD-Solomon Islands Dollar", "SCR-Seychellois Rupee", "SDG-Sudanese Pound", "SEK-Swedish Krona", "SGD-Singapore Dollar", "SHP-Saint Helena Pound", "SLL-Sierra Leonean Leone", "SOS-Somali Shilling", "SRD-Surinamese Dollar", "SSP-South Sudanese Pound", "STD-São Tomé and Príncipe Dobra (pre-2018)", "STN-São Tomé and Príncipe Dobra", "SVC-Salvadoran Colón", "SYP-Syrian Pound", "SZL-Swazi Lilangeni", "THB-Thai Baht", "TJS-Tajikistani Somoni", "TMT-Turkmenistani Manat", "TND-Tunisian Dinar", "TOP-Tongan Pa'anga", "TRY-Turkish Lira", "TTD-Trinidad and Tobago Dollar", "TWD-New Taiwan Dollar", "TZS-Tanzanian Shilling", "UAH-Ukrainian Hryvnia", "UGX-Ugandan Shilling", "USD-United States Dollar", "UYU-Uruguayan Peso", "UZS-Uzbekistan Som", "VEF-Venezuelan Bolívar Fuerte (Old)", "VES-Venezuelan Bolívar Soberano", "VND-Vietnamese Dong", "VUV-Vanuatu Vatu", "WST-Samoan Tala", "XAF-CFA Franc BEAC", "XAG-Silver Ounce", "XAU-Gold Ounce", "XCD-East Caribbean Dollar", "XDR-Special Drawing Rights", "XOF-CFA Franc BCEAO", "XPD-Palladium Ounce", "XPF-CFP Franc", "XPT-Platinum Ounce", "YER-Yemeni Rial", "ZAR-South African Rand", "ZMW-Zambian Kwacha", "ZWL-Zimbabwean Dollar"};

        countryList.add("Select Currency");
        countryList.addAll(Arrays.asList(list));
        return countryList;
    }
}