package com.example.user.sunshine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {


    ShareActionProvider mShareActionProvider;

    //Empty constructor
    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Add menu item from menu_detail to the Options Menu in the Detail Activity
        inflater.inflate(R.menu.menu_detail, menu);

        try{

            //Documentation : http://developer.android.com/reference/android/widget/ShareActionProvider.html

            //get Share menu item
            MenuItem shareMenuItem = menu.findItem(R.id.action_share_weather);

            // Get the provider and hold onto it to set/change the share intent.
            mShareActionProvider = (ShareActionProvider) shareMenuItem.getActionProvider();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Kiran Here");
            mShareActionProvider.setShareIntent(shareIntent);

        }catch (Exception ex){

            Log.e("SHARE ERROR","Error is : " + ex.getMessage());
        }
    }

    //Open the Settings Activity here when the Settings menu item is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings_detail) {

            //get the application context to pass into the activity constructor
            Context appContext = getActivity().getApplicationContext();

            //Toast.makeText(appContext,"Hi from menu click",Toast.LENGTH_LONG).show();

            //Start the settings activity.
            Intent setingsActivityIntent = new Intent(appContext, SettingsActivityWithFragment.class);
            startActivity(setingsActivityIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);

    }
}
