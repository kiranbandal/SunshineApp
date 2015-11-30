package com.example.user.sunshine;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

//This activity holds the PreferenceFragment (WeatherFragment.java.
//In the OnCreate method, the fragment is added to the activity
public class SettingsActivityWithFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_with_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //first acivity is getting created for the first time, so add the weather fragment here
        if (savedInstanceState == null) {

            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();

            //instance of our fragment
            WeatherFragment weatherFrgmnt = new WeatherFragment();
            fragTransaction.replace(R.id.activity_for_fragment, weatherFrgmnt);
            fragTransaction.commit();
        }

        try {
            PreferenceManager.setDefaultValues(this, R.xml.weather_preferences, false);
        } catch (Exception ex) {
            Log.e("READ ERROR", "Error while setting the default preferences values");
        }
    }

}

