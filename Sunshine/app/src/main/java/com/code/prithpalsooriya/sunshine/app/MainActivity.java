package com.code.prithpalsooriya.sunshine.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

            //<editor-fold desc="Add Fake Data">
            /*Add fake data*/
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
            //</editor-fold>

            //<editor-fold desc="Bind The Adapter">
            /*bind the adapter*/
            //get the listview
            ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
            //set arrayAdapter on listview
            listView.setAdapter(arrayAdapter);
            //</editor-fold>

            //<editor-fold desc="Add Networking Code">
            /*Add Networking code*/
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                // REDO THIS: because now requires an API key
                String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Bristol,GB&mode=json&units=metric&cnt=7";
                String apiKey = "&APPID=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY;
                URL url = new URL(baseUrl.concat(apiKey));

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
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
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                forecastJsonStr = null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            //</editor-fold>


            return rootView;
        }
    }

}
