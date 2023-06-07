package com.example.androidbasics;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidbasics.databinding.ActivityMainBinding;
import com.example.androidbasics.psrupload.views.PsrSubmissionFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.androidbasics.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

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
            onToolBarBackPress();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onToolBarBackPress() {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

        Log.d("Palash", "onToolBarBackPress ");

        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof PsrSubmissionFragment) {
//            PsrSubmissionFragment yourFragment = (PsrSubmissionFragment) fragment;
            ((PsrSubmissionFragment) fragment).showExitConfirmationDialog("Toolbar Back Button");
//            yourFragment.showExitConfirmationDialog("Toolbar Back Button");
        } else {
            if (navController.getCurrentDestination() != null)
                Log.d("Palash", navController.getCurrentDestination().getDisplayName());
            super.onBackPressed();
        }
    }
}