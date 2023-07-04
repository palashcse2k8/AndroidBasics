package com.example.androidbasics.psrupload.views;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbasics.MainActivity;
import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentPsrImageScannerBinding;
//import com.example.androidbasics.imagecropper.CropImage;
import com.example.androidbasics.imagecropper.CropImageNew;
import com.example.androidbasics.imagecropper.CropImageView;
import com.example.androidbasics.psrupload.utils.ImageUtil;
import com.example.androidbasics.psrupload.viewmodels.PSRViewModel;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PsrImageScannerFragmentNew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PsrImageScannerFragmentNew extends Fragment {

    private static final int REQUEST_CODE_FIRST = 99;
    private static final int REQUEST_CODE_SECOND = 98;
    FragmentPsrImageScannerBinding binding;
    PSRViewModel vm;

    Bitmap bitmap1;
    Bitmap bitmap2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PsrScannerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PsrImageScannerFragmentNew newInstance(String param1, String param2) {
        PsrImageScannerFragmentNew fragment = new PsrImageScannerFragmentNew();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PsrImageScannerFragmentNew() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(PSRViewModel.class);

        requireActivity().getSupportFragmentManager().setFragmentResultListener(CropImageNew.CROP_IMAGE_REQUEST_FIRST_CODE_STRING,this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if(requestKey == CropImageNew.CROP_IMAGE_REQUEST_FIRST_CODE_STRING) {

                    CropImageNew.ActivityResult activityResult = result.getParcelable(CropImageNew.CROP_IMAGE_EXTRA_RESULT);
                    Log.d("", "First Result Received for First Call");
                    if (activityResult != null) {
                        Uri resultUri = activityResult.getUri();
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (bitmap != null) {
                            vm.setBitMap1(bitmap);
                            updateView();
                        }
                    } else {
                        Exception error = activityResult.getError();
                        Log.d("", error.getMessage());
                    }
                }
            }
        });

        requireActivity().getSupportFragmentManager().setFragmentResultListener(CropImageNew.CROP_IMAGE_REQUEST_SECOND_CODE_STRING,this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if(requestKey == CropImageNew.CROP_IMAGE_REQUEST_SECOND_CODE_STRING) {

                    CropImageNew.ActivityResult activityResult = result.getParcelable(CropImageNew.CROP_IMAGE_EXTRA_RESULT);
                    Log.d("", "Result Received For 2nd Call");
                    if (activityResult != null) {
                        Uri resultUri = activityResult.getUri();
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (bitmap != null) {
                            vm.setBitMap2(bitmap);
                            updateView();
                        }
                    } else {
                        Exception error = activityResult.getError();
                        Log.d("", error.getMessage());
                    }
                }
            }
        });

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public void updateView() {
        if(vm.getBitmap1().getValue() != null) {
            binding.nidFrontTxt.setText("Reload");
            binding.nidFront.setImageBitmap(vm.getBitmap1().getValue());
            bitmap1 = vm.getBitmap1().getValue();
        }

        if(vm.getBitmap2().getValue() != null) {
            binding.nidBackTxt.setText("Reload");
            binding.nidBack.setImageBitmap(vm.getBitmap2().getValue());
            bitmap2 = vm.getBitmap2().getValue();
        }
    }

    public void setActionBarTitle (CharSequence title) {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("","scanner onCreateView");
        binding = FragmentPsrImageScannerBinding.inflate(inflater, container, false);
        setActionBarTitle("Upload PSR Photo");
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateView();
        init();
        binding.buttonNext.setOnClickListener(v -> {

            if (bitmap1 == null && bitmap2 == null) {
                Toast toast = Toast.makeText(getContext(), "You didn't Select any image", Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.RED);
                toast.show();
                return;
            }

            vm.getUser().getValue().setPsr(ImageUtil.combineBitmapsVertically(bitmap1, bitmap2));
            vm.setBitMap1(null);
            vm.setBitMap2(null);

            Fragment fragment = new PsrMergeFragment();
            String tag = fragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, tag).addToBackStack(tag).commit();

//            NavHostFragment.findNavController(PsrImageScannerFragment.this)
//                    .navigate(R.id.action_psr_merge);
        });
    }

    private void init() {
        binding.nidFrontTxt.setOnClickListener(v -> {
            CropImageNew.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .startFragment((MainActivity)getActivity(), this, CropImageNew.CROP_IMAGE_REQUEST_FIRST_CODE_STRING);
        });
        binding.nidBackTxt.setOnClickListener(v -> {
            CropImageNew.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .startFragment((MainActivity)getActivity(), this, CropImageNew.CROP_IMAGE_REQUEST_SECOND_CODE_STRING);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        bitmap2 = null;
        bitmap1 = null;
    }
}