package com.example.androidbasics.psrupload.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentPsrSubmissionBinding;
import com.example.androidbasics.psrupload.utils.PDFGenerator;
import com.example.androidbasics.psrupload.viewmodels.PSRViewModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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

    PSRViewModel vm;

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
        vm = new ViewModelProvider(requireActivity()).get(PSRViewModel.class);
        binding = FragmentPsrSubmissionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private Bitmap getBitmapFromAsset(String strName) throws IOException, IOException {
        AssetManager assetManager = getActivity().getAssets();
        Bitmap bitmap;
        try (InputStream istr = assetManager.open(strName)) {
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bitmap;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.labelTin.setText(vm.getUser().getValue().tinNumber);
        binding.labelTaxAssessmentYear.setText(vm.getUser().getValue().selectedAssessmentYear);
        binding.labelUploadDate.setText(date);

//        try {
//            binding.ivTick.setImageBitmap(getBitmapFromAsset("tik_mark.png"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        binding.btnDownload.setOnClickListener(v -> {
            PDFGenerator.generatePSRPDF(vm.getUser().getValue().psrBitmap, vm.getUser().getValue().tinNumber, vm.getUser().getValue().selectedAssessmentYear, date, vm.getUser().getValue().fileLocation);
//            NavHostFragment.findNavController(PsrSubmissionFragment.this)
//                    .navigate(R.id.action_pdf_view);

            Fragment fragment = new PdfViewFragment();
            String tag = fragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();

        });

        binding.btnDone.setOnClickListener(view1 -> {
//            NavHostFragment.findNavController(PsrSubmissionFragment.this)
//                    .navigate(R.id.action_home);
            goToHome();
        });

        binding.btnShare.setOnClickListener(v -> shareFile());
    }

    private static final int PERMISSION_REQUEST_CODE = 123;

    private void requestPermissions() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    public void goToHome() {
        Fragment fragment = new ModuleSelectionFragment();
        String tag = fragment.getClass().getSimpleName();
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(false).replace(R.id.fragment_container_view, fragment, tag).commit();
    }

    public String getCurrentDate() {
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
//                NavHostFragment.findNavController(PsrSubmissionFragment.this)
//                        .navigate(R.id.action_home);
//                requireActivity().onBackPressed();
                goToHome();
            }
        });
        builder.setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }
}