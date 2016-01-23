package com.gavinmccoy.festivalsie;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.gavinmccoy.festivalsie.JSONParser;
public class MainActivity extends AppCompatActivity {

    ListView list;
    TextView name;
    Button Btngetdata;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    //JSON Node Names
    //private static final String TAG_NAME = "name";


    JSONObject android = null;


    //JSON Node Names
    public static final String url="http://api.songkick.com/api/3.0/metro_areas/29314/calendar.json?apikey=pSzVjIdFw6u0LfEC";
    public static final String TAG_RESULTSPAGE="resultsPage";
    public static final String TAG_PERPAGE="perPage";
    public static final String TAG_PAGE="page";
    public static final String TAG_TOTALENTRIES="totalEntries";
    public static final String TAG_RESULTS="results";
    public static final String TAG_EVENT="event";
    public static final String TAG_VENUE="venue";
    public static final String TAG_LNG="lng";
    public static final String TAG_DISPLAYNAME="displayName";
    public static final String TAG_METROAREA="metroArea";
    public static final String TAG_COUNTRY="country";
    public static final String TAG_COUNTRY_OBJ_DISPLAYNAME="displayName";
    public static final String TAG_METROAREA_OBJ_DISPLAYNAME="displayName";
    public static final String TAG_ID="id";
    public static final String TAG_URI="uri";
    public static final String TAG_VENUE_OBJ_ID="id";
    public static final String TAG_VENUE_OBJ_URI="uri";
    public static final String TAG_LAT="lat";
    public static final String TAG_PERFORMANCE="performance";
    public static final String TAG_ARTIST="artist";
    public static final String TAG_IDENTIFIER="identifier";
    public static final String TAG_MBID="mbid";
    public static final String TAG_HREF="href";
    public static final String TAG_ARTIST_OBJ_DISPLAYNAME="displayName";
    public static final String TAG_ARTIST_OBJ_ID="id";
    public static final String TAG_ARTIST_OBJ_URI="uri";
    public static final String TAG_PERFORMANCE_OBJ_DISPLAYNAME="displayName";
    public static final String TAG_BILLINGINDEX="billingIndex";
    public static final String TAG_PERFORMANCE_OBJ_ID="id";
    public static final String TAG_BILLING="billing";
    public static final String TAG_EVENT_OBJ_DISPLAYNAME="displayName";
    public static final String TAG_POPULARITY="popularity";
    public static final String TAG_START="start";
    public static final String TAG_DATE="date";
    public static final String TAG_DATETIME="datetime";
    public static final String TAG_TIME="time";
    public static final String TAG_AGERESTRICTION="ageRestriction";
    public static final String TAG_LOCATION="location";
    public static final String TAG_LOCATION_OBJ_LNG="lng";
    public static final String TAG_CITY="city";
    public static final String TAG_LOCATION_OBJ_LAT="lat";
    public static final String TAG_EVENT_OBJ_ID="id";
    public static final String TAG_TYPE="type";
    public static final String TAG_EVENT_OBJ_URI="uri";
    public static final String TAG_STATUS="status";
    public static final String TAG_RESULTSPAGE_OBJ_STATUS="status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        oslist = new ArrayList<HashMap<String, String>>();

        Btngetdata = (Button) findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new JSONParse().execute();

            }
        });
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //ver = (TextView) findViewById(R.id.vers);
            name = (TextView) findViewById(R.id.name);
            //api = (TextView) findViewById(R.id.api);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrlByGet(url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                JSONObject resultsPage_obj = json.getJSONObject(TAG_RESULTSPAGE);

                String str_perPage = resultsPage_obj.getString(TAG_PERPAGE);

                String str_page = resultsPage_obj.getString(TAG_PAGE);

                String str_totalEntries = resultsPage_obj.getString(TAG_TOTALENTRIES);

                JSONObject results_obj = resultsPage_obj.getJSONObject(TAG_RESULTS);

                JSONArray event = results_obj.getJSONArray(TAG_EVENT);
                for (int event_i = 0; event_i < event.length(); event_i++) {
                    JSONObject event_obj = event.getJSONObject(event_i);
                    JSONObject venue_obj = event_obj.getJSONObject(TAG_VENUE);

                    String str_lng = venue_obj.getString(TAG_LNG);

                    String str_displayName = venue_obj.getString(TAG_DISPLAYNAME);

                    JSONObject metroArea_obj = venue_obj.getJSONObject(TAG_METROAREA);

                    JSONObject country_obj = metroArea_obj.getJSONObject(TAG_COUNTRY);

                    String str_country_obj_displayName = country_obj.getString(TAG_COUNTRY_OBJ_DISPLAYNAME);

                    String str_metroArea_obj_displayName = metroArea_obj.getString(TAG_METROAREA_OBJ_DISPLAYNAME);

                    String str_id = metroArea_obj.getString(TAG_ID);

                    String str_uri = metroArea_obj.getString(TAG_URI);

                    String str_venue_obj_id = venue_obj.getString(TAG_VENUE_OBJ_ID);

                    String str_venue_obj_uri = venue_obj.getString(TAG_VENUE_OBJ_URI);

                    String str_lat = venue_obj.getString(TAG_LAT);

                    JSONArray performance = event_obj.getJSONArray(TAG_PERFORMANCE);
                    for (int performance_i = 0; performance_i < performance.length(); performance_i++) {
                        JSONObject performance_obj = performance.getJSONObject(performance_i);
                        JSONObject artist_obj = performance_obj.getJSONObject(TAG_ARTIST);

                        JSONArray identifier = artist_obj.getJSONArray(TAG_IDENTIFIER);
                        for (int identifier_i = 0; identifier_i < identifier.length(); identifier_i++) {
                            JSONObject identifier_obj = identifier.getJSONObject(identifier_i);
                            String str_mbid = identifier_obj.getString(TAG_MBID);

                            String str_href = identifier_obj.getString(TAG_HREF);
                        }
                        String str_artist_obj_displayName = artist_obj.getString(TAG_ARTIST_OBJ_DISPLAYNAME);

                        String str_artist_obj_id = artist_obj.getString(TAG_ARTIST_OBJ_ID);

                        String str_artist_obj_uri = artist_obj.getString(TAG_ARTIST_OBJ_URI);

                        String str_performance_obj_displayName = performance_obj.getString(TAG_PERFORMANCE_OBJ_DISPLAYNAME);

                        String str_billingIndex = performance_obj.getString(TAG_BILLINGINDEX);

                        String str_performance_obj_id = performance_obj.getString(TAG_PERFORMANCE_OBJ_ID);

                        String str_billing = performance_obj.getString(TAG_BILLING);

                    }
                    String str_event_obj_displayName = event_obj.getString(TAG_EVENT_OBJ_DISPLAYNAME);

                    // Have to keep these three lines of code underneath the JSON object/array/string that is to be parsed.
                    // While leaving the rest of the list code after the catch JSONException
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_EVENT_OBJ_DISPLAYNAME, str_event_obj_displayName);
                    oslist.add(map);

                    String str_popularity = event_obj.getString(TAG_POPULARITY);

                    JSONObject start_obj = event_obj.getJSONObject(TAG_START);

                    String str_date = start_obj.getString(TAG_DATE);

                    String str_datetime = start_obj.getString(TAG_DATETIME);

                    String str_time = start_obj.getString(TAG_TIME);

                    String str_ageRestriction = event_obj.getString(TAG_AGERESTRICTION);

                    JSONObject location_obj = event_obj.getJSONObject(TAG_LOCATION);

                    String str_location_obj_lng = location_obj.getString(TAG_LOCATION_OBJ_LNG);

                    String str_city = location_obj.getString(TAG_CITY);

                    String str_location_obj_lat = location_obj.getString(TAG_LOCATION_OBJ_LAT);

                    String str_event_obj_id = event_obj.getString(TAG_EVENT_OBJ_ID);

                    String str_type = event_obj.getString(TAG_TYPE);

                    String str_event_obj_uri = event_obj.getString(TAG_EVENT_OBJ_URI);

                    String str_status = event_obj.getString(TAG_STATUS);

                }
                String str_resultsPage_obj_status = resultsPage_obj.getString(TAG_RESULTSPAGE_OBJ_STATUS);


            }
            catch (JSONException e) {}

// Adding value HashMap key => value
//
//            HashMap<String, String> map = new HashMap<String, String>();
//
//            map.put(TAG_EVENT_OBJ_DISPLAYNAME, str_event_obj_displayName);

//            oslist.add(map);
            list = (ListView) findViewById(R.id.list);

            ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist, R.layout.list_item, new String[]{TAG_EVENT_OBJ_DISPLAYNAME}, new int[]{R.id.name});

            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(MainActivity.this, "You Clicked at " + oslist.get(+position).get(TAG_EVENT_OBJ_DISPLAYNAME), Toast.LENGTH_SHORT).show();

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, oslist.get(+position).get(TAG_EVENT_OBJ_DISPLAYNAME) + " is happening! Who's going? \n\n #Festivals.ie #Songkick");
                    startActivity(Intent.createChooser(shareIntent, "Share this event"));
                }
            });}



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds i-tems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gotoBrowseByDate(View view) {
        Intent intent = new Intent(this, BrowseByDateAndLocationActivity.class);
        startActivity(intent);
    }

    public void gotoSearchFestivals(View view) {
        Intent intent = new Intent(this, SearchFestivals.class);
        startActivity(intent);
    }

    public void gotoFindFestivalsByArtist(View view) {
        Intent intent = new Intent(this, FindFestivalsByArtist.class);
        startActivity(intent);
    }

    public void gotoGigRecommender(View view) {
        Intent intent = new Intent(this, GigRecommender.class);
        startActivity(intent);
    }
}
