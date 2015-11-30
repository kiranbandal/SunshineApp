package com.example.user.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get the intent that triggered this intent
        Intent parentIntent = getIntent();

        String weatherDataName = getPackageName() + "WEATHER_DATA";
        //extract he values packed in the intent
        String weatherDataPassedInIntent = parentIntent.getStringExtra(weatherDataName);

        TextView tvShowWeatherData = (TextView) findViewById(R.id.tvWeatherData);
        tvShowWeatherData.setText(weatherDataPassedInIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    //When the menu item is selected, the event is first passed to the Activity that contains the fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //We will handle this event in the fragment, so return false here
        if (id == R.id.action_settings_detail) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
