package org.ninja.tripgang;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.ninja.tripgang.R;

public class CreateTrip extends Activity implements TripType.OnFragmentInteractionListener, TripDestination.OnFragmentInteractionListener {
    String[] TripType = {"Camping", "Picnic", "Surfing", "Road Trip"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        final FragmentManager fm= getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        final Fragment tripType = (Fragment)fm.findFragmentById(R.id.trip_type_fragment);
        final Fragment tripDest = (Fragment)fm.findFragmentById(R.id.trip_type_destination);
        ft.hide(tripDest);
        ft.commit();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_type_triplist_view,TripType);
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
