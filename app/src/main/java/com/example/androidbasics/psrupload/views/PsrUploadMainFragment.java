package com.example.androidbasics.psrupload.views;
import static com.example.androidbasics.psrupload.utils.DateTimeUtil.getSimpleDate;
import static com.example.androidbasics.psrupload.utils.DateTimeUtil.getSimpleTime;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidbasics.R;
import com.example.androidbasics.apihandle.QueryResponseModel;
import com.example.androidbasics.databinding.FragmentPsrUploadMainBinding;
import com.example.androidbasics.psrupload.utils.ListViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PsrUploadMainFragment extends Fragment {

    FragmentPsrUploadMainBinding binding;

    List<QueryResponseModel> responseModelList;
    public PsrUploadMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        responseModelList = getAssessmentResultList();
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Proof of Submission of Return");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPsrUploadMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), responseModelList);
        binding.lvSubmissionResult.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();
        binding.lvSubmissionResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetailAlertDialogue(position);
            }
        });
        binding.btnAddPSR.setOnClickListener(v -> {
            Fragment fragment = new PsrUploadFragment();
            String tag = fragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();

        });
    }

    public void showDetailAlertDialogue (int index) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_psr_listview_item_detail, null);
        dialogBuilder.setTitle("PSR Uploaded Info");
        dialogBuilder.setNegativeButton("Close", (dialog, which) -> {
            dialog.cancel();
        });
        dialogBuilder.setView(dialogView);

        TextView lblDetailStatus = (TextView) dialogView.findViewById(R.id.lblDetailName);
        lblDetailStatus.setText(responseModelList.get(index).getTrStatus());

        TextView lblDetailTin = (TextView) dialogView.findViewById(R.id.lblDetailTin);
        lblDetailTin.setText(responseModelList.get(index).getTin());

        TextView lblDetailWallet = (TextView) dialogView.findViewById(R.id.lblDetailWallet);
        lblDetailWallet.setText(responseModelList.get(index).getMobileNo());

        TextView lblDetailAssessmentYear = (TextView) dialogView.findViewById(R.id.lblDetailAssessmentYear);
        lblDetailAssessmentYear.setText(responseModelList.get(index).getAssessmentYear());

        TextView lblDetailAccounts = (TextView) dialogView.findViewById(R.id.lblDetailAccounts);
        lblDetailAccounts.setText(responseModelList.get(index).getPrimaryAccount());

        TextView lblDetailUploadDateTime = (TextView) dialogView.findViewById(R.id.lblDetailUploadDateTime);

        String dateTime = getSimpleDate(responseModelList.get(index).getTrUploadDate())+ ", " + getSimpleTime(responseModelList.get(index).getTrUploadDate());
        lblDetailUploadDateTime.setText(dateTime);

        AlertDialog alertDialog = dialogBuilder.create();
//        TextView textView = (TextView) alertDialog.findViewById(android.R.id.title);
//        if(textView != null)
//        {
//            textView.setGravity(Gravity.CENTER);
//        } else {
//            Log.d("", "no title found!");
//        }
        alertDialog.show();
    }
    public List<QueryResponseModel> getAssessmentResultList() {
        ArrayList<QueryResponseModel> queryResponseModels = new ArrayList<>();

        try {
            // Read JSON file from assets folder
            InputStream inputStream = getContext().getAssets().open("query_response.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            //Log.d("json data", json.toString());

            // Parse JSON data
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Create QueryResponseModel object and add to the list
                QueryResponseModel responseModel = new QueryResponseModel();
                responseModel.setMobileNo(jsonObject.getString("mobileNo"));
                responseModel.setCustomerName(jsonObject.getString("customerName"));
                responseModel.setAssessmentYear(jsonObject.getString("assessmentYear"));
                responseModel.setTin(jsonObject.getString("tin"));
                responseModel.setTrStatus(jsonObject.getString("trStatus"));
                responseModel.setTrUploadDate(jsonObject.getString("trUploadDate"));
                responseModel.setPrimaryAccount(jsonObject.getString("primaryAccount"));
                // Set other fields similarly

                queryResponseModels.add(responseModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       // Log.d("queryResponseModels", queryResponseModels.toString());
        return queryResponseModels;
    }
}