package org.ninja.tripgang;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Tripslist extends ActionBarActivity {
    private Context context = this;
    String[] tripArray = {"Tahoe", "Mount Shasta", "Caramel", "Crator Lake"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripslist);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview,tripArray);
        ListView listview = (ListView) findViewById(R.id.trips_list);
        listview.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tripslist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_add_new_trip:
                openNewTrip();
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void openNewTrip(){
        Intent intent = new Intent(context,CreateTrip.class);
        startActivity(intent);
    }
}
