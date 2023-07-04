package com.example.androidbasics.imagecropper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidbasics.MainActivity;
import com.example.androidbasics.R;

import java.io.File;
import java.io.IOException;

/**
 * A fragment for image cropping.
 * Use {@link CropImageNew#activity(Uri)} to create a builder to start this fragment.
 */
public class CropImageFragment extends Fragment
        implements CropImageView.OnSetImageUriCompleteListener,
        CropImageView.OnCropImageCompleteListener {

    /**
     * The crop image view library widget used in the fragment
     */
    private CropImageView mCropImageView;
    private String CROP_IMAGE_REQUEST_KEY;


    /**
     * Persist URI image to crop URI if specific permissions are required
     */
    private Uri mCropImageUri;

    public CropImageView.OnCropImageCompleteListener getCropImageCompleteListener() {
        return cropImageCompleteListener;
    }

    public void setCropImageCompleteListener(CropImageView.OnCropImageCompleteListener cropImageCompleteListener) {
        this.cropImageCompleteListener = cropImageCompleteListener;
    }

    public CropImageView.OnCropImageCompleteListener cropImageCompleteListener;

    /**
     * the options that were set for the crop image
     */
    private CropImageOptions mOptions;

    public CropImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("","onCreateView");
        View view = inflater.inflate(R.layout.crop_image_activity, container, false);

        mCropImageView = view.findViewById(R.id.cropImageView);

        mCropImageUri = getArguments().getParcelable(CropImageNew.CROP_IMAGE_EXTRA_SOURCE);
        mOptions = getArguments().getParcelable(CropImageNew.CROP_IMAGE_EXTRA_OPTIONS);
        CROP_IMAGE_REQUEST_KEY = getArguments().getString(CropImageNew.CROP_IMAGE_REQUEST_KEY);
//        mCropImageUri = bundle.getParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE);
//        mOptions = bundle.getParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS);


        if (savedInstanceState == null) {
            if (mCropImageUri == null || mCropImageUri.equals(Uri.EMPTY)) {
                if (CropImageNew.isExplicitCameraPermissionRequired(requireContext())) {
                    // request permissions and handle the result in onRequestPermissionsResult()
                    requestPermissions(
                            new String[]{Manifest.permission.CAMERA},
                            CropImageNew.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
                } else {
                    Log.d("","Loading Image");
                    CropImageNew.startPickImageActivity(requireActivity());
                }
            } else if (CropImageNew.isReadExternalStoragePermissionsRequired(requireContext(), mCropImageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CropImageNew.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already granted, can start crop image activity

                mCropImageView.setImageUriAsync(mCropImageUri);
            }
        }

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            CharSequence title = mOptions != null &&
                    mOptions.activityTitle != null && mOptions.activityTitle.length() > 0
                    ? mOptions.activityTitle
                    : getResources().getString(R.string.crop_image_activity_title);
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCropImageView.setOnSetImageUriCompleteListener(this);
        mCropImageView.setOnCropImageCompleteListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mCropImageView.setOnSetImageUriCompleteListener(null);
        mCropImageView.setOnCropImageCompleteListener(null);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.crop_image_menu, menu);

        // Customize the menu items as needed
        MenuItem cropMenuItem = menu.findItem(R.id.crop_image_menu_crop);
        // ...

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("onOptionsItemSelected", "onOptionsItemSelected");
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            setResultCancel();
            return true;
        } else if (itemId == R.id.crop_image_menu_crop) {
            cropImage();
            return true;
        } else if (itemId == R.id.crop_image_menu_rotate_left) {
            mCropImageView.rotateImage(-mOptions.rotationDegrees);
            return true;
        } else if (itemId == R.id.crop_image_menu_rotate_right) {
            mCropImageView.rotateImage(mOptions.rotationDegrees);
            return true;
        } else if (itemId == R.id.crop_image_menu_flip_horizontally) {
            mCropImageView.flipImageHorizontally();
            return true;
        } else if (itemId == R.id.crop_image_menu_flip_vertically) {
            mCropImageView.flipImageVertically();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == CropImageNew.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCropImageView.setImageUriAsync(mCropImageUri);
            } else {
                Toast.makeText(getContext(), R.string.crop_image_activity_no_permissions, Toast.LENGTH_LONG).show();
                setResultCancel();
            }
        } else if (requestCode == CropImageNew.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            // if request is cancelled, the result arrays are empty
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImageNew.startPickImageActivity(requireActivity());
            } else {
                Toast.makeText(getContext(), R.string.crop_image_activity_no_camera_permissions, Toast.LENGTH_LONG).show();
                setResultCancel();
            }
        }
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        if (error == null) {
            // show the crop image view and crop button if everything is OK
            if (mOptions.initialCropWindowRectangle != null) {
                mCropImageView.setCropRect(mOptions.initialCropWindowRectangle);
            }
            if (mOptions.initialRotation > -1) {
                mCropImageView.setRotatedDegrees(mOptions.initialRotation);
            }
        } else {
            Log.e("CropImage", "Failed to load image for cropping: " + error.getMessage(), error);
            Toast.makeText(getContext(), R.string.crop_image_activity_load_image_error, Toast.LENGTH_LONG).show();
            setResultCancel();
        }
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("onActivityResult", "fragment onActivityResult called" );
        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImageNew.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the picker. We don't have anything to crop
                setResultCancel();
            }

            if (resultCode == Activity.RESULT_OK) {
                mCropImageUri = CropImageNew.getPickImageResultUri(getContext(), data);

                // For API >= 23 we need to check specifically that we have permissions to read external
                // storage.
                if (CropImageNew.isReadExternalStoragePermissionsRequired(getContext(), mCropImageUri)) {
                    // request permissions and handle the result in onRequestPermissionsResult()
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            CropImageNew.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                } else {
                    // no permissions required or already grunted, can start crop image activity
                    mCropImageView.setImageUriAsync(mCropImageUri);
                }
            }
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        setResult(result.getUri(), result.getError(), result.getSampleSize());
    }
    protected Uri getOutputUri() {
        Uri outputUri = mOptions.outputUri;
        if (outputUri == null || outputUri.equals(Uri.EMPTY)) {
            try {
                String ext =
                        mOptions.outputCompressFormat == Bitmap.CompressFormat.JPEG
                                ? ".jpg"
                                : mOptions.outputCompressFormat == Bitmap.CompressFormat.PNG ? ".png" : ".webp";
                outputUri = Uri.fromFile(File.createTempFile("cropped", ext, getContext().getCacheDir()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to create temp file for output image", e);
            }
        }
        return outputUri;
    }

    public  CropImageNew.ActivityResult getResult(Uri uri, Exception error, int sampleSize) {
        CropImageNew.ActivityResult result =
                new CropImageNew.ActivityResult(
                        mCropImageView.getImageUri(),
                        uri,
                        error,
                        mCropImageView.getCropPoints(),
                        mCropImageView.getCropRect(),
                        mCropImageView.getRotatedDegrees(),
                        mCropImageView.getWholeImageRect(),
                        sampleSize);
        return result;
    }

    /**
     * Execute crop image action
     */
    private void cropImage() {
        if (mOptions.noOutputImage) {
            Log.d("cropImage", "cropImage");
            setResult(null, null, 1);
        } else {
            Uri outputUri = getOutputUri();
            mCropImageView.saveCroppedImageAsync(
                    outputUri,
                    mOptions.outputCompressFormat,
                    mOptions.outputCompressQuality,
                    mOptions.outputRequestWidth,
                    mOptions.outputRequestHeight,
                    mOptions.outputRequestSizeOptions);
        }
    }

    /**
     * Set the result when crop image activity finishes
     */
    private void setResult(Uri uri, Exception error, int sampleSize) {
        Log.d("setResult", "setResult with cropped image");
//        int resultCode = error == null ? 200 : CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;
//        if(cropImageCompleteListener != null)
//        {
//            Log.d("cropImageCompleteListener",cropImageCompleteListener.getClass().getSimpleName());
//            this.cropImageCompleteListener.onCropImageComplete(view, result);
//        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(CropImageNew.CROP_IMAGE_EXTRA_RESULT, getResult(uri, error, sampleSize));

        requireActivity().getSupportFragmentManager().setFragmentResult(CROP_IMAGE_REQUEST_KEY, bundle);

        requireActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * Set the result when crop image activity was canceled
     */
    private void setResultCancel() {
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}
