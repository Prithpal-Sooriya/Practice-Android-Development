package com.code.prithpalsooriya.sunshine.app;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
                    .add(R.id.container, new ForecastFragment())
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


}
