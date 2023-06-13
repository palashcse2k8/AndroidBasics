package com.example.androidbasics.psrupload.views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidbasics.databinding.FragmentPdfViewBinding;
import com.example.androidbasics.psrupload.utils.PDFGenerator;
import com.example.androidbasics.psrupload.viewmodels.BitMapResource;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PdfViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PdfViewFragment extends Fragment {
    FragmentPdfViewBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param1";

    // TODO: Rename and change types of parameters
    private String mParam;
    BitMapResource vm;

    Bitmap pdfBitmap;

    public PdfViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fileName Parameter 1.
     * @return A new instance of fragment PdfViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PdfViewFragment newInstance(String fileName) {
        PdfViewFragment fragment = new PdfViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, fileName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layo ut for this fragment
        vm = ViewModelProviders.of(getActivity()).get(BitMapResource.class);
        if(mParam == null)
            mParam = vm.getUser().getValue().fileLocation;
        pdfBitmap = PDFGenerator.getPdfFileAsBitmap(mParam);
        binding = FragmentPdfViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewLocation.setText("File Location : " + mParam);

        binding.imageViewPdf.setImageBitmap(pdfBitmap);
    }
}