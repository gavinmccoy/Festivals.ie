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


    // learn2 crack tutorial
    ListView list;
    TextView ver;
    TextView name;
    TextView api;
    Button Btngetdata;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    //URL to get JSON Array
    private static String url = "http://api.learn2crack.com/android/jsonos/";

    //JSON Node Names
    private static final String TAG_OS = "android";
    private static final String TAG_VER = "ver";
    private static final String TAG_NAME = "name";
    private static final String TAG_API = "api";

    JSONArray android = null;


    //JSON Node Names
    private static final String TAG_RESULTSPAGE = "resultsPage";
    private static final String TAG_RESULTS = "results";
    private static final String TAG_EVENTS = "event";
    private static final String TAG_ID = "id";
    private static final String TAG_TYPE = "type";
    private static final String TAG_URI = "uri";
    private static final String TAG_DISPLAYNAME = "displayName";
    private static final String TAG_START = "start";
    private static final String TAG_START_TIME = "time";
    private static final String TAG_START_DATE = "date";
    private static final String TAG_PERFORMANCE = "performance";
    private static final String TAG_PERFORMANCE_NAME = "displayName";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_LOCATION_CITY = "city";
    private static final String TAG_VENUE = "venue";
    private static final String TAG_VENUE_NAME = "displayName";
    private static final String TAG_STATUS = "status";
    private static final String TAG_EVENT_NAME = "eventName";
    private static final String TAG_PRICE = "billingIndex";
    private static final String TAG_LONGITUDE = "lng";
    private static final String TAG_LATITUDE = "lat";

    ArrayList<HashMap<String, String>> resultsPageList;

//    ListView list;

    TextView displayName, id;

    JSONArray events = null;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> eventList;
    private ProgressDialog pDialog;
    // private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    // URL for Songkick gigs in Dublin
    // private static String url = "http://api.songkick.com/api/3.0/metro_areas/29314/calendar.json?apikey=pSzVjIdFw6u0LfEC";


    // DUBLIN GPS Coordinates
//    private static final String DUB_LAT = "53.1691308";
//    private static final String DUB_LONG = "-6.8107381";

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
//        resultsPageList = new ArrayList<HashMap<String, String>>();
//
//        eventList = new ArrayList<HashMap<String, String>>();
//        // Keep an eye on this line!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        ListView listView = getListView();
//
//        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
//        new GetEvents().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ver = (TextView) findViewById(R.id.vers);
            name = (TextView) findViewById(R.id.name);
            api = (TextView) findViewById(R.id.api);
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
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                android = json.getJSONArray(TAG_OS);
                for (int i = 0; i < android.length(); i++) {
                    JSONObject c = android.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String ver = c.getString(TAG_VER);
                    String name = c.getString(TAG_NAME);
                    String api = c.getString(TAG_API);

                    // Adding value HashMap key => value

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_VER, ver);
                    map.put(TAG_NAME, name);
                    map.put(TAG_API, api);

                    oslist.add(map);
                    list = (ListView) findViewById(R.id.list);

                    ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist,
                            R.layout.list_item,
                            new String[]{TAG_VER, TAG_NAME, TAG_API}, new int[]{
                            R.id.vers, R.id.name, R.id.api});

                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Toast.makeText(MainActivity.this, "You Clicked at " + oslist.get(+position).get("name"), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            // Creating service handler class instance
//            ServiceHandler sh = new ServiceHandler();
//
//            // Making a request to url and getting response
//            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
//
//            Log.d("Response: ", "> " + jsonStr);
//
//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    JSONObject resultsPage = jsonObj.getJSONObject(TAG_RESULTSPAGE);
//                    JSONObject results = resultsPage.getJSONObject(TAG_RESULTS);
//                    // Getting JSON Array node
//
//                    events = results.getJSONArray(TAG_EVENTS);
//
//
//                    for (int i = 0; i < events.length(); i++) {
//                        JSONObject c = events.getJSONObject(i);
//
//                        String id = c.getString(TAG_ID);
//                        String type = c.getString(TAG_TYPE);
//                        String uri = c.getString(TAG_URI);
//                        String name = c.getString(TAG_DISPLAYNAME);
//                        //Log.d("MainActivity", "informations : " + id + " " + type + " " + uri + " " + name);
//
//                        // Start node is JSON Object
//                        JSONObject start = c.getJSONObject(TAG_START);
//                        String start_time = start.getString(TAG_START_TIME);
//
//                        /*Modifie le format de l'heure affiché (ex : 20H30)*/
//                        if (start_time == "null")
//                            start_time = "NC";
//                        else
//                        {
//                            String[] parts_time = start_time.split(":");
//                            String part_heures = parts_time[0];
//                            String part_minutes = parts_time[1];
//                            start_time = part_heures + "H" + part_minutes;
//                        }
//
//                        String start_date = start.getString(TAG_START_DATE);
//
//
//                        /*Modifie le format de la date affiché (ex : 12/02/2015)*/
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                        Date date = null;
//                        try
//                        {
//                            date = formatter.parse(start_date);
//                        } catch (Exception e) //ParseException
//                        {
//                            e.printStackTrace();
//                        }
//                        start_date = new SimpleDateFormat("dd/MM/yyyy").format(date);
//
//
//                        // Performance node is JSON Object
//                        // JSONObject performance = c.getJSONObject(TAG_PERFORMANCE);
//                        JSONArray Arrayperformance = c.getJSONArray(TAG_PERFORMANCE);
//                        JSONObject Operformance = Arrayperformance.getJSONObject(0);
//                        String performance_name = Operformance.getString(TAG_PERFORMANCE_NAME);
//                        String prix = Operformance.getString(TAG_PRICE);
//
//                        // Location node is JSON Object
//                        JSONObject location = c.getJSONObject(TAG_LOCATION);
//                        String city = location.getString(TAG_LOCATION_CITY);
//
//
//                        // Location node is JSON Object
//                        JSONObject venue = c.getJSONObject(TAG_VENUE);
//                        String venue_name = venue.getString(TAG_VENUE_NAME);
//                        String longitude = venue.getString(TAG_LONGITUDE);
//                        String latitude = venue.getString(TAG_LATITUDE);
//
//                        String status = c.getString(TAG_STATUS);
//
//                        // tmp hashmap for single event
//                        HashMap<String, String> event = new HashMap<String, String>();
//
//                        // adding each child node to HashMap key => value
//                        String address = venue_name + ", " + city;
//
//                        if (status.equals("ok"))
//                            status = "";
//                        else
//                            status = "Annulé";
//
//
//                        String[] parts_name = name.split("\\(");
//                        name = parts_name[0];
//
//                        event.put(TAG_ID, id);
//                        event.put(TAG_EVENT_NAME, name);
//                        event.put(TAG_VENUE, address);
//                        event.put(TAG_PERFORMANCE_NAME, performance_name);
//                        event.put(TAG_START_TIME, start_time);
//                        event.put(TAG_START_DATE, start_date);
//                        event.put(TAG_TYPE, type);
//                        event.put(TAG_STATUS, status);
//
//                        event.put(TAG_LOCATION_CITY, city);
//                        event.put(TAG_LONGITUDE, longitude);
//                        event.put(TAG_LATITUDE, latitude);
//                        event.put(TAG_PRICE, prix);
//
//                        Log.d("TEST", "TAG_DISPLAYNAME " + event.get("TAG_DISPLAYNAME"));
//                        Log.d("TEST", "TAG_VENUE_NAME " + event.get("TAG_VENUE_NAME"));
//                        Log.d("TEST", "TAG_PERFORMANCE_NAME " + event.get("TAG_PERFORMANCE_NAME"));
//
//                        // adding contact to contact list
//                        eventList.add(event);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Log.e("ServiceHandler", "Couldn't get any data from the url");
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//            /**
//             * Updating parsed JSON data into ListView
//             * */
//            ListAdapter adapter = new SimpleAdapter(
//                    MainActivity.this, eventList, R.layout.list_item, new String[]{TAG_EVENT_NAME,
//                    TAG_VENUE, TAG_PERFORMANCE_NAME, TAG_START_DATE, TAG_START_TIME, TAG_TYPE,
//                    TAG_STATUS, TAG_LOCATION_CITY, TAG_LONGITUDE, TAG_LATITUDE, TAG_PRICE},
//                    new int[]{R.id.name, R.id.venue_name, R.id.performance_name, R.id.start_date,
//                            R.id.start_time, R.id.type, R.id.status, R.id.city, R.id.longitude, R.id.latitude, R.id.price});
//
//
//
//            setListAdapter(adapter);
//        }
//
    }



        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
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
    }
