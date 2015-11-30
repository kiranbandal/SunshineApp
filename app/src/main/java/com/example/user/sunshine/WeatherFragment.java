package com.example.user.sunshine;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by User on 20-Oct-15.
 * This fragment does not have any UI, rather the UI is rendered from the xml resource file (weather_preference.xml)
 * This fragmetn gets added to the activity,
 */
public class WeatherFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String LOCATION_PREF_KEY = "location_preference";
    public static final String UNITS_PREF_KEY = "weather_units_list_preference";
    public static final String DAYS_PREF_KEY = "count_of_preference";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.weather_preferences);


    }

    @Override
    public void onResume() {
        super.onResume();

        //register event for change preference changed
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        //un-register event for change preference changed
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    //When preference in the Settings will be changed (new item will be selected in the Settings)
    //following method will be called
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(LOCATION_PREF_KEY) || key.equals(UNITS_PREF_KEY) || key.equals(DAYS_PREF_KEY)) {

            Preference connectionPref = findPreference(key);
            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(sharedPreferences.getString(key, ""));

            String a = "adad";
        }

    }
}
