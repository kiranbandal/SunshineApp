package com.example.user.sunshine;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {


    public static String WEATHER_DATA_NAME;

    private static final String MAIN_ACTIVITY_LOG_TAG = "Main Acti";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Log.i(MAIN_ACTIVITY_LOG_TAG, "Inside on Create");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWeatherData();

        //<editor-fold desc="List View On Click event">
        //attach on item click handler for List View
        ListView weatherForecasTextView = (ListView) findViewById(R.id.listview_forecast);
        weatherForecasTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get TextView in a Row on which user clicked and
                TextView clickedTextView = (TextView) view;

                //get clicked weather forecast string from the Text View
                String weatherForecas = clickedTextView.getText().toString();

                //show the above string in Toast
                //Toast.makeText(MainActivity.this," The selected string : " + weatherForecas,Toast.LENGTH_LONG).show();

                //open the Detail Activity
                Intent detailActiIntent = new Intent(MainActivity.this, DetailActivity.class);

                //unique string for recognizing the weather data on the Detail Activity
                WEATHER_DATA_NAME = getPackageName() + "WEATHER_DATA";

                //put the weather data in Intent object
                detailActiIntent.putExtra(WEATHER_DATA_NAME, weatherForecas);
                startActivity(detailActiIntent);
            }
        });
        //</editor-fold>
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //region Function to actually get the weather data by calling the Async task
    private void getWeatherData() {

        //If the app does not have internet connection then, notify theuser and exit
        if (!SunshineUtilFunctions.hasTheInternetPermission(this)) {

            AlertDialog.Builder bldr = new AlertDialog.Builder(MainActivity.this);
            bldr.setTitle("Chek Internet Permission");
            bldr.setPositiveButton("Test Positive Button", null);
            bldr.setMessage("Please Make sure that app has the internet permission and then try again");

            AlertDialog msgBx = bldr.create();
            msgBx.show();

            return;
        }

        //Call the Async task to get the weather data in json format
        FetchWeatherTask weatherTask = new FetchWeatherTask(MainActivity.this, this);
        weatherTask.execute().toString();
    }
    //endregion

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Start the settings activity.
            Intent setingsActivityIntent = new Intent(this, SettingsActivityWithFragment.class);
            startActivity(setingsActivityIntent);
            return true;
        }

        else if(id == R.id.action_view_on_map){
            openLocationOnMap();
            return true;
        }

        else if( id == R.id.action_refresh){

            //get weather data
            getWeatherData();
        }

        return super.onOptionsItemSelected(item);
    }

    //Open the user specified location in the google map app
    void openLocationOnMap() {

        //get the Location set by the user
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(this);
        String preferredLocation = shPref.getString("location_preference", "singapore");


        //Log.d("LOCATION INFO", "The set location :" + preferredLocation);

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", preferredLocation)
                .build();

        Intent viewOnMapIntent = new Intent(Intent.ACTION_VIEW);
        viewOnMapIntent.setData(geoLocation);

        if (viewOnMapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(viewOnMapIntent);
        } else {
            Toast.makeText(this, "Could not open the location on the Map...", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        //Log.i(MAIN_ACTIVITY_LOG_TAG,"Inside on Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Log.i(MAIN_ACTIVITY_LOG_TAG, "Inside on Stop");
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Log.i(MAIN_ACTIVITY_LOG_TAG, "Inside on Resume");
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Log.i(MAIN_ACTIVITY_LOG_TAG, "Inside on Start");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Log.i(MAIN_ACTIVITY_LOG_TAG, "Inside on Destroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Log.i(MAIN_ACTIVITY_LOG_TAG, "Inside on Save Instance State");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Log.i(MAIN_ACTIVITY_LOG_TAG, "Inside on on Restore Instance State");
    }
}
