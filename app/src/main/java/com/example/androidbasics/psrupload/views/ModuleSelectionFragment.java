package com.example.androidbasics.psrupload.views;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidbasics.R;
import com.example.androidbasics.databinding.FragmentModuleSelectionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModuleSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModuleSelectionFragment extends Fragment {

    FragmentModuleSelectionBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ModuleSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModuleSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModuleSelectionFragment newInstance(String param1, String param2) {
        ModuleSelectionFragment fragment = new ModuleSelectionFragment();
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
        checkPermissions(permissions);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentModuleSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnPsrUpload.setOnClickListener(view1 -> NavHostFragment.findNavController(ModuleSelectionFragment.this)
                .navigate(R.id.action_enter_psr_module));

        binding.btnFormC.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(ModuleSelectionFragment.this)
                    .navigate(R.id.action_enter_from_c_module);
        });
//        binding.btnFormC.setOnClickListener(view1 -> {
//            NavHostFragment.findNavController(ModuleSelectionFragment.this)
//                    .navigate(R.id.action_temp);
//        });
    }

    private static final int PERMISSION_REQUEST_CODE = 200;

    String[] permissions = new String[]{CAMERA, MANAGE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};

    private void checkPermissions(String[] permissions) {

        Log.d("Palash", "check permission called");
        for (int i = 0; i < permissions.length; i++) {
            int result = ContextCompat.checkSelfPermission(getContext(), permissions[i]);
            if (result != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            }
        }
    }

    private void requestPermission() {
        Log.d("Palash", "requestPermission called");
        ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSION_REQUEST_CODE);
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (accepted)
                        Toast.makeText(getContext(), "Permission Granted, You can access.", Toast.LENGTH_SHORT).show();
                    else {

                        Toast.makeText(getContext(), "Permission not Granted, You can't access.", Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(permissions[0])) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermission();
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
}