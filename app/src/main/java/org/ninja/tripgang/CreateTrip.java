package org.ninja.tripgang;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ninja.tripgang.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CreateTrip extends Activity implements TripType.OnFragmentInteractionListener, TripDestination.OnFragmentInteractionListener {
    String[] TripType = {"Camping", "Picnic", "Surfing", "Road Trip"};
    private static final String LOG_TAG = "TripGang";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/textsearch";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyBq5rdwERDh09yRC1j6NVD4SmlCiYFuDjA";
    private ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&query=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString("formatted_address"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        final FragmentManager fm= getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        final Fragment tripType = (Fragment)fm.findFragmentById(R.id.trip_type_fragment);
        final Fragment tripDest = (Fragment)fm.findFragmentById(R.id.trip_type_destination);
        final Activity a = (Activity)this;
        ft.hide(tripDest);
        ft.commit();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_type_triplist_view,TripType);
        ListView listview = (ListView) findViewById(R.id.trip_type);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.show(tripDest);
                ft.hide(tripType);
                ft.commit();
            }
        });
        EditText edit = (EditText)findViewById(R.id.destination_txt);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count%3 == 1){
                    ArrayList results = new ArrayList<String>();
                    results = autocomplete(s.toString());
                    ListView tripsuggestions = (ListView)findViewById(R.id.tripsuggestions);
                    ArrayAdapter adapter1 = new ArrayAdapter<String>(a,R.layout.activity_type_triplist_view,results);
                    tripsuggestions.setAdapter(adapter1);
                    tripsuggestions.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
