package com.example.androidbasics.psrupload.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentPsrSubmissionBinding;
import com.example.androidbasics.psrupload.utils.PDFGenerator;
import com.example.androidbasics.psrupload.viewmodels.BitMapResource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PsrSubmissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PsrSubmissionFragment extends Fragment {

    FragmentPsrSubmissionBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public String date;

    BitMapResource vm;
    Toolbar toolbar;

    public PsrSubmissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PsrSubmissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PsrSubmissionFragment newInstance(String param1, String param2) {
        PsrSubmissionFragment fragment = new PsrSubmissionFragment();
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
        date = getCurrentDate();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                showExitConfirmationDialog("Back Button");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vm = ViewModelProviders.of(getActivity()).get(BitMapResource.class);
        PDFGenerator.generatePDF(vm.getUser().getValue().psrBitmap, vm.getUser().getValue().tinNumber, vm.getUser().getValue().selectedAssessmentYear, date, vm.getUser().getValue().fileLocation);
        binding = FragmentPsrSubmissionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.labelTin.setText(vm.getUser().getValue().tinNumber);
        binding.labelTaxAssessmentYear.setText(vm.getUser().getValue().selectedAssessmentYear);
        binding.labelUploadDate.setText(date);

        binding.btnDownload.setOnClickListener( v -> {
            NavHostFragment.findNavController(PsrSubmissionFragment.this)
                    .navigate(R.id.action_pdf_view);
        });

        binding.btnDone.setOnClickListener(view1 -> NavHostFragment.findNavController(PsrSubmissionFragment.this)
                .navigate(R.id.action_home));

        binding.btnShare.setOnClickListener(v-> shareFile());
//        addListenerToToolBarBackButton();
    }

    public String getCurrentDate () {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void shareFile() {
        Uri fileUri = FileProvider.getUriForFile(getContext(), "com.scanlibrary.provider.main", new File(vm.getUser().getValue().fileLocation));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Share File"));
    }
    public boolean showExitConfirmationDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Exit by " + str);
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NavHostFragment.findNavController(PsrSubmissionFragment.this)
                        .navigate(R.id.action_home);
//                requireActivity().onBackPressed();
            }
        });
        builder.setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }

//    void addListenerToToolBarBackButton() {
//        final AppCompatActivity act = (AppCompatActivity) getActivity();
//        if (act.getSupportActionBar() != null) {
//            toolbar = (Toolbar) act.getSupportActionBar().getCustomView();
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showExitConfirmationDialog();
//                }
//            });
//        }
//    }
}