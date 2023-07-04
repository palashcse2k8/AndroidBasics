package com.example.androidbasics;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbasics.FormC.FormCSubmissionFragment;
import com.example.androidbasics.FormC.FormCViewModel;
import com.example.androidbasics.databinding.ActivityMainBinding;
import com.example.androidbasics.psrupload.viewmodels.PSRViewModel;
import com.example.androidbasics.psrupload.views.ModuleSelectionFragment;
import com.example.androidbasics.psrupload.views.PsrSubmissionFragment;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainActivity extends AppCompatActivity {

//    private AppBarConfiguration appBarConfiguration;

    PSRViewModel psrViewModel;
    FormCViewModel formCViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        psrViewModel = new ViewModelProvider(this).get(PSRViewModel.class);
        formCViewModel = new ViewModelProvider(this).get(FormCViewModel.class);
        com.example.androidbasics.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        setupActionBar();

        checkPermissions(permissions);

        Fragment fragment = new ModuleSelectionFragment();
        String tag = fragment.getClass().getSimpleName();
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container_view, fragment, tag).commit();

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("", "onOptionsItemSelected");

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home || id == androidx.appcompat.R.id.home) {
            Log.d("", "Ha ha ha");
            onToolBarBackPressNew();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();

        super.onBackPressed();
        return super.onSupportNavigateUp();
    }

//    public void onToolBarBackPress() {
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
//
//        Log.d("Palash", "onToolBarBackPress ");
//
//        assert navHostFragment != null;
//        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
//
//        if (fragment instanceof PsrSubmissionFragment) {
////            PsrSubmissionFragment yourFragment = (PsrSubmissionFragment) fragment;
//            ((PsrSubmissionFragment) fragment).showExitConfirmationDialog("Toolbar Back Button");
////            yourFragment.showExitConfirmationDialog("Toolbar Back Button");
//        } else if (fragment instanceof FormCSubmissionFragment) {
//            ((FormCSubmissionFragment) fragment).showExitConfirmationDialog("Toolbar Back Button");
//        } else {
//            if (navController.getCurrentDestination() != null)
//                Log.d("Palash", navController.getCurrentDestination().getDisplayName());
//            super.onBackPressed();
//        }
//    }

    public void onToolBarBackPressNew() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);

        if (fragment instanceof PsrSubmissionFragment) {
            ((PsrSubmissionFragment) fragment).showExitConfirmationDialog("Toolbar Back Button");
        } else if (fragment instanceof FormCSubmissionFragment) {
            ((FormCSubmissionFragment) fragment).showExitConfirmationDialog("Toolbar Back Button");
        } else {
            super.onBackPressed();
        }
    }


    private static final int PERMISSION_REQUEST_CODE = 200;

    String[] permissions = new String[]{CAMERA, MANAGE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};

    private void checkPermissions(String[] permissions) {

        Log.d("Palash", "check permission called");
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            }
        }
    }

    private void requestPermission() {
        Log.d("Palash", "requestPermission called");
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage("You need to allow access to both the permissions")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String [] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (accepted)
                    Toast.makeText(this, "Permission Granted, You can access.", Toast.LENGTH_SHORT).show();
                else {

                    Toast.makeText(this, "Permission not Granted, You can't access.", Toast.LENGTH_SHORT).show();

                    if (shouldShowRequestPermissionRationale(permissions[0])) {
                        showMessageOKCancel(
                                (dialog, which) -> requestPermission());
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Log.d("onActivityResult", "activity onActivityResult called" );
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}