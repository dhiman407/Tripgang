package org.ninja.tripgang;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.ninja.tripgang.R;

public class CreateTrip extends Activity implements TripType.OnFragmentInteractionListener {
    String[] TripType = {"Camping", "Picnic", "Surfing", "Road Trip"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_type_triplist_view,TripType);
        ListView listview = (ListView) findViewById(R.id.trip_type);
        listview.setAdapter(adapter);
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
