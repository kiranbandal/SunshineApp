package com.example.user.sunshine;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 18-Oct-15.
 */
public class SunshineUtilFunctions {

    public static final String UTIL_FUNCITONS_CLASS = "UTIL FUNCITONS CLASS";

    public static boolean hasTheInternetPermission(Context cntxt) {

        if (ContextCompat.checkSelfPermission(cntxt,
                android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {

            return true;
        } else {

            return false;
        }


    }


}
