//package com.example.androidbasics.psrupload.views;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProviders;
//import androidx.navigation.fragment.NavHostFragment;
//
//import com.example.androidbasics.R;
//import com.example.androidbasics.databinding.FragmentPsrScannerBinding;
//import com.example.androidbasics.psrupload.utils.ImageUtil;
//import com.example.androidbasics.psrupload.viewmodels.PSRViewModel;
//import com.scanlibrary.ScanActivity;
//import com.scanlibrary.ScanConstants;
//
//import java.io.IOException;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link PsrScannerOpenCVFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class PsrScannerOpenCVFragment extends Fragment {
//
//    private static final int REQUEST_CODE_FIRST = 99;
//    private static final int REQUEST_CODE_SECOND = 98;
//    FragmentPsrScannerBinding binding;
//    PSRViewModel vm;
//
//    Bitmap bitmap1;
//    Bitmap bitmap2;
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment PsrScannerFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static PsrScannerOpenCVFragment newInstance(String param1, String param2) {
//        PsrScannerOpenCVFragment fragment = new PsrScannerOpenCVFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public PsrScannerOpenCVFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding = FragmentPsrScannerBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        init();
//        binding.btnSubmit.setOnClickListener(v -> {
//            vm.getUser().getValue().setPsr(ImageUtil.combineBitmapsVertically(bitmap1, bitmap2));
//
//            if(bitmap1 == null && bitmap2 == null ) {
//                Toast toast = Toast.makeText(getContext(), "You didn't Select any image", Toast.LENGTH_SHORT);
//                toast.getView().setBackgroundColor(Color.RED);
//                toast.show();
//                return;
//            }
//
//            NavHostFragment.findNavController(PsrScannerOpenCVFragment.this)
//                    .navigate(R.id.action_psr_merge);
////            NavHostFragment.findNavController(PsrScannerFragment.this)
////                    .navigate(R.id.action_psr_crop);
//        });
//    }
//
//    private void init() {
//        binding.btnCameraPart1.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA, REQUEST_CODE_FIRST));
//        binding.btnCameraPart2.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA, REQUEST_CODE_SECOND));
//        binding.btnMediaPart1.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_MEDIA, REQUEST_CODE_FIRST));
//        binding.btnMediaPart2.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_MEDIA, REQUEST_CODE_SECOND));
//        vm = ViewModelProviders.of(getActivity()).get(PSRViewModel.class);
//    }
//
//    private class ScanButtonClickListener implements View.OnClickListener {
//
//        private int preference;
//        private int part;
//
//        public ScanButtonClickListener(int preference, int part) {
//            this.preference = preference;
//            this.part = part;
//        }
//
//        public ScanButtonClickListener() {
//        }
//
//        @Override
//        public void onClick(View v) {
//            startScan(preference, part);
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//        bitmap2 = null;
//        bitmap1 = null;
//    }
//
//    protected void startScan(int preference, int part) {
//        Intent intent = new Intent(getContext(), ScanActivity.class);
//        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
//        startActivityForResult(intent, part);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_FIRST && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
//                getActivity().getContentResolver().delete(uri, null, null);
//                bitmap1 = bitmap;
//                binding.btnCameraPart1.setVisibility(View.GONE);
//                binding.btnMediaPart1.setVisibility(View.GONE);
//                binding.imgPart1.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if (requestCode == REQUEST_CODE_SECOND && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
//                getActivity().getContentResolver().delete(uri, null, null);
//                bitmap2 = bitmap;
//                binding.btnCameraPart2.setVisibility(View.GONE);
//                binding.btnMediaPart2.setVisibility(View.GONE);
//                binding.imgPart2.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Log.d("myTag", "onActivityResult else");
//        }
//    }
//}