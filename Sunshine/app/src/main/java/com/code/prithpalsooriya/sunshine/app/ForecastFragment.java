package com.code.prithpalsooriya.sunshine.app;

/**
 * Created by Prithpal Sooriya on 06/09/2016.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    //empty constructor
    public ForecastFragment() {
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


        return rootView;
    }

    /**
     * Inner class FetchWeatherTask
     * Will extend AsyncTask so we can create a background worker thread to handle fetching weather data
     */
    private class FetchWeatherTask extends AsyncTask <Void, Void, Void> {

        //constant so we can use this class name when using logcat/logging errors and stuff
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {
            //move the networking code into here!
            //this is where we will connect to the API url and retrieve the data we want

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
                    return null; //doInBackground() complete, null returned
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
                    return null; //bail, did not work so end thread
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null; //bail! did not work so end thread
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            //</editor-fold>
            return null;
        }
    }
}