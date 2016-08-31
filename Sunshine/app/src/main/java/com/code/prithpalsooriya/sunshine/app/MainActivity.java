package com.code.prithpalsooriya.sunshine.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //creation of activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //no saved instance state, so create the fragment
        if (savedInstanceState == null) {
            //create the fragment
            getSupportFragmentManager().beginTransaction()
                    //add the new fragment to the container (main layout)
                    .add(R.id.container, new PlaceholderFragment())
                    //commit to thee changes
                    .commit();
        }
    }

    //creation of options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu, adds items to the actionbar if present
        //get the XML for menu, and place inside the Menu menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //override of what happens when something in menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Handle action bar item clicks here
         * Action bar will handle clicks on Home/Up button so as long as
         *      you specify parent activity in AndroidManifest.xml
         */
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        //empty constructor
        public PlaceholderFragment() {
        }

        //on create
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //adding some fake data :)
            //arraylist of strings for the weather
            ArrayList<String> fakeData = new ArrayList<>();

            fakeData.add("Today - Sunny - 88/63");
            fakeData.add("Tomorrow - Foggy - 70/46");
            fakeData.add("Weds - Cloudy - 37/63");
            fakeData.add("Thurs - Rainy - 64/51");
            fakeData.add("Fri - Foggy - 70/46");
            fakeData.add("Sat - Sunny - 76/68");

            //params for array adapter
            //param1 =  context = getActivity()
            //param2 = name of layout
            //param3 = id of item in layout to populate
            //param4 = List of data (ArrayList, Array, Set, ...)
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    getActivity(),
                    R.layout.list_item_forecast,
                    R.id.list_item_forecast_textview,
                    fakeData
            );

            /*bind the adapter*/
            //get the listview
            ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
            //set arrayAdapter on listview
            listView.setAdapter(arrayAdapter);

            return rootView;
        }
    }

}
