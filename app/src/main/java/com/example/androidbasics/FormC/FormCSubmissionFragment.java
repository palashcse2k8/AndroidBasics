package com.example.androidbasics.FormC;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentFormCSubmissionBinding;
import com.example.androidbasics.psrupload.utils.PDFGenerator;
import com.example.androidbasics.psrupload.views.PdfViewFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormCSubmissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormCSubmissionFragment extends Fragment {

    FragmentFormCSubmissionBinding binding;
    FormCViewModel viewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView bmImage;
    View viewLayout;

    public FormCSubmissionFragment() {
        // Required empty public constructor
    }

    public static FormCSubmissionFragment newInstance(String param1, String param2) {
        FormCSubmissionFragment fragment = new FormCSubmissionFragment();
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity()).get(FormCViewModel.class);
        binding = FragmentFormCSubmissionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromViewModel();
        binding.btnDone.setOnClickListener(v-> {
            NavHostFragment.findNavController(FormCSubmissionFragment.this).navigate(R.id.action_form_c_toHome);
        });


        binding.btnDownload.setOnClickListener(v-> {

            viewLayout = getLayoutInflater().inflate(R.layout.layout_pdf_template, null);
            String fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/FormC_Complete.pdf";
            Bitmap bitmap = getBitmapFromView(getContext(), viewLayout, getActivity());
            PDFGenerator.generateFormCPDF(bitmap, fileName);

            Bundle bundle = new Bundle();
            bundle.putString("param1", fileName);

            NavHostFragment.findNavController(FormCSubmissionFragment.this).navigate(R.id.action_pdf_view_form_c, bundle);

        });
    }

    public void getDataFromViewModel(){
        binding.labelToken.setText("123456789");
        binding.labelCurrency.setText(viewModel.getCurrency().getValue());
        binding.labelAmount.setText(viewModel.getAmount().getValue());
        binding.labelUploadDate.setText(getCurrentDate());
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    public Bitmap getBitmapFromView(Context context, View view, Activity activity) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.getDisplay().getRealMetrics(displayMetrics);
        }
        else{
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        view.measure(
                View.MeasureSpec.makeMeasureSpec(
                        displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
                ),
                View.MeasureSpec.makeMeasureSpec(
                        displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
                )
        );
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        Bitmap bitmap = Bitmap.createBitmap( view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.draw(c);
        return Bitmap.createScaledBitmap(bitmap, 595, 842, true);
    }
}