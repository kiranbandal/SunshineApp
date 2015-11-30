package com.example.user.sunshine;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import android.preference.PreferenceManager;

/**
 * Created by User on 18-Oct-15.
 */
//                                                  input,update,return
public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

    private ArrayAdapter<String> mForecastAdapter;

    public static final String FETCH_WEATHER_TASK = "FetchWeatherTask";

    private Context appContxt;
    private Activity homePage;



    //Constructor is added to get reference to the Context object

    public FetchWeatherTask(Context appContext,Activity homePageActiviy){
        this.appContxt = appContext;
        this.homePage = homePageActiviy;
    }

    @Override
    protected String[] doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        String countOfDays;

        String LOCATION,COUNT_OF_DAYS;

        try {

            //get number of days and location which have been set by the user in the Settings
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.appContxt);
            COUNT_OF_DAYS = pref.getString("count_of_preference","3");
            LOCATION = pref.getString("location_preference","Singapore");


            String queryFirstPart = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Singapore&units=metric&cnt=";
            String queryLastPart = "&mode=json&appid=84f2893fe49677da9ef8999c120c63f5";


            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + LOCATION +
                    "&units=metric&cnt=" + COUNT_OF_DAYS + "&mode=json&appid=84f2893fe49677da9ef8999c120c63f5");
            //URL url = new URL(weatherForecastQuery);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(FETCH_WEATHER_TASK, "IOException occured, ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } catch (Exception e) {

            Log.e(FETCH_WEATHER_TASK, "Generic Exception Occured ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(FETCH_WEATHER_TASK, "Error closing stream", e);
                }
            }
        }

        //Log.v(FETCH_WEATHER_TASK, " The Open Weather Map JSON String : " + forecastJsonStr);

        //process the raw JSON and get the Array of neatly formatted Strings here
        WeatherParser jsonWatherParser = new WeatherParser();
        String weatherData[] = {};

        try {
            weatherData = jsonWatherParser.getWeatherDataFromJson(forecastJsonStr,Integer.parseInt(COUNT_OF_DAYS));
        }catch (JSONException jsonEx){
            Log.e(FETCH_WEATHER_TASK,"Error in the function getWeatherData, : " + jsonEx.getMessage());
        }

        return weatherData;
    }


    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param strings The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(String[] forecastDataArray) {


        //create Array List from the passed in array
        ArrayList<String> arrayListWeatherForecasts = new ArrayList<>(Arrays.asList(forecastDataArray));

        //create Array Adapater for List View
        mForecastAdapter = new ArrayAdapter<String>(this.appContxt,
                R.layout.list_item_forecast,R.id.list_item_forecast_textview, arrayListWeatherForecasts);


        //get List View
        ListView weatherForecaseListView = (ListView)this.homePage.findViewById(R.id.listview_forecast);
        weatherForecaseListView.setAdapter(mForecastAdapter);
    }
}


