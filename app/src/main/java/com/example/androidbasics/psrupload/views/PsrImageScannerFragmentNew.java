package com.example.androidbasics.psrupload.views;

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
import com.example.androidbasics.imagecropper.CropImageNew;
import com.example.androidbasics.imagecropper.CropImageView;
import com.example.androidbasics.psrupload.utils.ImageUtil;
import com.example.androidbasics.psrupload.viewmodels.PSRViewModel;

import java.io.IOException;

public class PsrImageScannerFragmentNew extends Fragment {

    FragmentPsrImageScannerBinding binding;
    PSRViewModel vm;
    Bitmap bitmap1, bitmap2;

    public PsrImageScannerFragmentNew() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(PSRViewModel.class);

        requireActivity().getSupportFragmentManager().setFragmentResultListener(CropImageNew.CROP_IMAGE_REQUEST_FIRST_CODE_STRING, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey == CropImageNew.CROP_IMAGE_REQUEST_FIRST_CODE_STRING) {

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

        requireActivity().getSupportFragmentManager().setFragmentResultListener(CropImageNew.CROP_IMAGE_REQUEST_SECOND_CODE_STRING, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey == CropImageNew.CROP_IMAGE_REQUEST_SECOND_CODE_STRING) {

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
    }

    public void updateView() {
        if (vm.getBitmap1().getValue() != null) {
            binding.nidFrontTxt.setText("Reload");
            binding.nidFront.setImageBitmap(vm.getBitmap1().getValue());
            bitmap1 = vm.getBitmap1().getValue();
        }

        if (vm.getBitmap2().getValue() != null) {
            binding.nidBackTxt.setText("Reload");
            binding.nidBack.setImageBitmap(vm.getBitmap2().getValue());
            bitmap2 = vm.getBitmap2().getValue();
        }
    }

    public void setActionBarTitle(CharSequence title) {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("", "scanner onCreateView");
        binding = FragmentPsrImageScannerBinding.inflate(inflater, container, false);
        setActionBarTitle("Upload PSR Photo");
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateView();
        init();
    }

    private void init() {
        binding.nidFrontTxt.setOnClickListener(v -> {
            CropImageNew.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .startFragment((MainActivity) getActivity(), this, CropImageNew.CROP_IMAGE_REQUEST_FIRST_CODE_STRING);
        });
        binding.nidBackTxt.setOnClickListener(v -> {
            CropImageNew.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .startFragment((MainActivity) getActivity(), this, CropImageNew.CROP_IMAGE_REQUEST_SECOND_CODE_STRING);
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        bitmap2 = null;
        bitmap1 = null;
    }
}