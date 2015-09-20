package com.example.android.sunshine.app;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by pankajanand on 20/8/15.
 */
    /**
     * A forecast fragment containing a simple view.
     */
public class ForecastFragment extends Fragment {

    ArrayAdapter<String> mForcastAadapter;
    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.forecastfragment, menu);
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            int id= item.getItemId();

            if (id==R.id.action_refresh){
                updateWeather();
                return true;
            }

            return super.onOptionsItemSelected(item);
        }


    private void updateWeather(){
        FetchWeatherTask fetchtask = new FetchWeatherTask(getActivity(), mForcastAadapter);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = prefs.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
        String mode = prefs.getString(getString(R.string.pref_system),getString(R.string.pref_syncConnectionTypes_default));
        fetchtask.execute(location,mode);
    }

    @Override
    public void onStart(){
        super.onStart();
        updateWeather();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mForcastAadapter= new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forcast,
                R.id.list_item_forecast_textview,
                new ArrayList<String>());

        ListView listviw= (ListView) rootView.findViewById(R.id.listView_forcast);

        listviw.setAdapter(mForcastAadapter);

        listviw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // The fileUrl is a string URL, such as "http://www.example.com/image.png"

                String forcast=mForcastAadapter.getItem(i);
                Intent intent1 = new Intent(getActivity(), ForecastDetail.class).putExtra(Intent.EXTRA_TEXT,forcast);
                startActivity(intent1);
            }
        });
        return rootView;
    }
}
