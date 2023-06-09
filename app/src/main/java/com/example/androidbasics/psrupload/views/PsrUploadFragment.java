package com.example.androidbasics.psrupload.views;

import static com.example.androidbasics.psrupload.utils.JsonParser.getAssessmentResultsList;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbasics.R;
import com.example.androidbasics.apihandle.QueryResponseModel;
import com.example.androidbasics.databinding.FragmentPsrUploadBinding;
import com.example.androidbasics.psrupload.models.PSRStatus;
import com.example.androidbasics.psrupload.viewmodels.PSRViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PsrUploadFragment extends Fragment {

    FragmentPsrUploadBinding binding;

    PSRViewModel vm;

    private String previousTaxSession, runningTaxSession;

    public PsrUploadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(PSRViewModel.class);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentPsrUploadBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.btnUpload.setOnClickListener(view1 -> NavHostFragment.findNavController(PsrUploadFragment.this)
//                .navigate(R.id.action_psr_image_scanner));

        binding.btnUpload.setOnClickListener(view1 -> {
            Fragment fragment = new PsrImageScannerFragmentNew();
            String tag = fragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();
        });

        binding.labelName.setText(vm.getUser().getValue().fullName);
        binding.labelAcNumber.setText(vm.getUser().getValue().accountNumber);
        binding.labelTin.setText(vm.getUser().getValue().tinNumber);

        binding.btnUpload.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, getAssessmentYear());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner1.setAdapter(adapter);
        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String item = parent.getItemAtPosition(position).toString();
                    vm.getUser().getValue().setSelectedAssessmentYear(item);
                }
                updateStatus(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void updateStatus(int position) {

        if (position == 0) {
            binding.btnUpload.setVisibility(View.GONE);
            binding.labelTaxAssessmentStatus.setText("");
        } else if (position == 1) {
            setText(getStatus(previousTaxSession));
        } else if (position == 2) {
            setText(getStatus(runningTaxSession));
        } else {
            Log.d("", "Not in the range");
        }
    }

    public String getStatus(String selectedYear) {

        String status = "";
        List<QueryResponseModel> responseModelList;
        if (selectedYear.equalsIgnoreCase(previousTaxSession)) {
            responseModelList = getAssessmentResultsList(getContext(), "query_response_previous_session.json");
            if (responseModelList.isEmpty()) {
                status = "Not Submitted";
            } else {
                status = responseModelList.get(0).getTrStatus();
            }
            return status;
        } else if (selectedYear.equalsIgnoreCase(runningTaxSession)) {
            responseModelList = getAssessmentResultsList(getContext(), "query_response_current_session.json");

            if (responseModelList.isEmpty()) {
                status = "Not Submitted";
            } else {
                status = responseModelList.get(0).getTrStatus();
            }
            return status;
        } else {
            Log.d("", "Wrong year selected!");
        }
        return status;
    }


//    public String getStatusFromAPI() {
//
//        try {
//            String baseUrl = "http://10.10.2.42:9797/SBLWalletBanking/v1/";
//            String deviceId = "abc";
//
//            // Create Retrofit instance
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            // Create an instance of your API service
//            ApiService apiService = retrofit.create(ApiService.class);
//
//            // Create the request payload
//            RequestModel requestModel = new RequestModel();
//            PayLoad payLoad = new PayLoad("01921621655", "2022-2023");
//            MessageHeader messageHeader = new MessageHeader("FETCH_SUBMITTED_RETURN", "12345", "SBLWallet", "e2930c5d-77f3-40bd-aebd-2c1fd6832b33", "2022-12-26T11:32:16.685", "android", "1.0.0");
//
//            // Set the request payload values
//            requestModel.setMessageHeader(messageHeader);
//            requestModel.setPayLoad((List<PayLoad>) payLoad);
//
//            Gson gson = new Gson();
//            String jsonPayload = gson.toJson(requestModel);
//            String base64Request = "";
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                base64Request = Base64.getEncoder().encodeToString(jsonPayload.getBytes());
//            } else {
//                base64Request = org.apache.commons.codec.binary.Base64.encodeBase64String(jsonPayload.getBytes());
//            }
//
//            // Make the API call
//            Call<QueryResponseModel> call = apiService.fetchSubmittedReturn(deviceId, base64Request);
//            Response<QueryResponseModel> response = call.execute();
//
//            if (response.isSuccessful()) {
//                // Handle successful response
//                QueryResponseModel responseModel = response.body();
//                Log.d("response.body", response.body().toString());
//                // Access the response values
//                String status = responseModel.getStatus();
//                String message = responseModel.getMessage();
//                List<QueryResponseModel.SubmittedReturn> submittedReturns = responseModel.getSubmittedReturns();
//                // Handle the response data
//                // ...
//            } else {
//                Log.d("response.code", String.valueOf(response.code()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void setText(@NonNull String status) {
        if (status.equalsIgnoreCase(PSRStatus.Updated.name()) || status.equalsIgnoreCase(PSRStatus.Submitted.name())) {
            binding.labelTaxAssessmentStatus.setText(status);
            binding.labelTaxAssessmentStatus.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_dark));
            binding.btnUpload.setVisibility(View.GONE);
        } else {
            binding.labelTaxAssessmentStatus.setText(status);
            binding.labelTaxAssessmentStatus.setTextColor(Color.RED);
            binding.btnUpload.setVisibility(View.VISIBLE);
        }
    }

    public List<String> getAssessmentYear() {

        List<String> assessmentYears = new ArrayList<>();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        String currentSession;
        String previousSession;
        String separator = "-";

        if (currentMonth < 7) {
            previousSession = (currentYear - 2) + separator + (currentYear - 1);
            currentSession = (currentYear - 1) + separator + currentYear;
        } else {
            previousSession = (currentYear - 1) + separator + currentYear;
            currentSession = (currentYear) + separator + (currentYear + 1);
        }
//        Log.d("getAssessmentYear : ",previousSession+ " and " + currentSession);

        previousTaxSession = previousSession;
        runningTaxSession = currentSession;
        assessmentYears.add("Select Tax Assessment Year");
        assessmentYears.add(previousSession);
        assessmentYears.add(currentSession);

        return assessmentYears;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}