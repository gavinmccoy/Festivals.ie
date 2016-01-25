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
import android.widget.EditText;
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
public class SearchFestivals extends AppCompatActivity {

    EditText editText;
    ListView list = null;
    TextView name;
    TextView id;
    Button Btngetdata;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    String searchString;

    JSONObject android = null;

    //JSON Node Names

    //    public String baseUrl  = "http://api.songkick.com/api/3.0/search/artists.json?query=";
//    public String searchString = ""; //editText.getText().toString();;// = "red hot chili peppers".replace(" ", "%20");
//    public String apiKey = "&apikey=pSzVjIdFw6u0LfEC";
//
//
//    public final String url = baseUrl + searchString + apiKey;
    //public static final String url="http://api.songkick.com/api/3.0/search/venues.json?query=whelans&apikey=pSzVjIdFw6u0LfEC";
    public static final String TAG_RESULTSPAGE="resultsPage";
    public static final String TAG_PERPAGE="perPage";
    public static final String TAG_PAGE="page";
    public static final String TAG_TOTALENTRIES="totalEntries";
    public static final String TAG_RESULTS="results";
    public static final String TAG_VENUE="venue";
    public static final String TAG_ZIP="zip";
    public static final String TAG_WEBSITE="website";
    public static final String TAG_LNG="lng";
    public static final String TAG_CITY="city";
    public static final String TAG_COUNTRY="country";
    public static final String TAG_DISPLAYNAME="displayName";
    public static final String TAG_CITY_OBJ_DISPLAYNAME="displayName";
    public static final String TAG_ID="id";
    public static final String TAG_URI="uri";
    public static final String TAG_VENUE_OBJ_DISPLAYNAME="displayName";
    public static final String TAG_DESCRIPTION="description";
    public static final String TAG_VENUE_OBJ_URI="uri";
    public static final String TAG_CAPACITY="capacity";
    public static final String TAG_PHONE="phone";
    public static final String TAG_METROAREA="metroArea";
    public static final String TAG_METROAREA_OBJ_COUNTRY="country";
    public static final String TAG_METROAREA_OBJ_COUNTRY_OBJ_DISPLAYNAME="displayName";
    public static final String TAG_METROAREA_OBJ_DISPLAYNAME="displayName";
    public static final String TAG_METROAREA_OBJ_ID="id";
    public static final String TAG_METROAREA_OBJ_URI="uri";
    public static final String TAG_STREET="street";
    public static final String TAG_VENUE_OBJ_ID="id";
    public static final String TAG_LAT="lat";
    public static final String TAG_STATUS="status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_festivals_by_artist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText editText = (EditText) findViewById(R.id.editText);

        oslist = new ArrayList<HashMap<String, String>>();

        Btngetdata = (Button) findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                oslist.clear();
                searchString = editText.getText().toString().replace(" ", "%20");
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
            id = (TextView) findViewById(R.id.id);
            //api = (TextView) findViewById(R.id.api);

            pDialog = new ProgressDialog(SearchFestivals.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();


        }

        @Override
        protected JSONObject doInBackground(String... args) {


            JSONParser jParser = new JSONParser();

            String baseUrl  = "http://api.songkick.com/api/3.0/search/venues.json?query=";
            //String searchString = ""; //editText.getText().toString();;// = "red hot chili peppers".replace(" ", "%20");
            String apiKey = "&apikey=pSzVjIdFw6u0LfEC";


            String url = baseUrl + searchString + apiKey;
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

                JSONArray venue = results_obj.getJSONArray(TAG_VENUE);
                for(int venue_i = 0; venue_i < venue.length(); venue_i++){
                    JSONObject venue_obj=venue.getJSONObject(venue_i);
                    String str_zip = venue_obj.getString(TAG_ZIP);

                    String str_website = venue_obj.getString(TAG_WEBSITE);

                    String str_lng = venue_obj.getString(TAG_LNG);

                    JSONObject city_obj = venue_obj.getJSONObject(TAG_CITY);

                    JSONObject country_obj = city_obj.getJSONObject(TAG_COUNTRY);

                    String str_displayName = country_obj.getString(TAG_DISPLAYNAME);

                    String str_city_obj_displayName = city_obj.getString(TAG_CITY_OBJ_DISPLAYNAME);




                    String str_id = city_obj.getString(TAG_ID);


                    String str_uri = city_obj.getString(TAG_URI);

                    String str_venue_obj_displayName = venue_obj.getString(TAG_VENUE_OBJ_DISPLAYNAME);


                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_VENUE_OBJ_DISPLAYNAME, str_venue_obj_displayName);


                    String str_description = venue_obj.getString(TAG_DESCRIPTION);

                    String str_venue_obj_uri = venue_obj.getString(TAG_VENUE_OBJ_URI);

                    String str_capacity = venue_obj.getString(TAG_CAPACITY);

                    String str_phone = venue_obj.getString(TAG_PHONE);

                    JSONObject metroArea_obj = venue_obj.getJSONObject(TAG_METROAREA);

                    JSONObject metroArea_obj_country_obj = metroArea_obj.getJSONObject(TAG_METROAREA_OBJ_COUNTRY);

                    String str_metroArea_obj_country_obj_displayName = metroArea_obj_country_obj.getString(TAG_METROAREA_OBJ_COUNTRY_OBJ_DISPLAYNAME);

                    String str_metroArea_obj_displayName = metroArea_obj.getString(TAG_METROAREA_OBJ_DISPLAYNAME);

                    String str_metroArea_obj_id = metroArea_obj.getString(TAG_METROAREA_OBJ_ID);

                    String str_metroArea_obj_uri = metroArea_obj.getString(TAG_METROAREA_OBJ_URI);

                    String str_street = venue_obj.getString(TAG_STREET);

                    String str_venue_obj_id = venue_obj.getString(TAG_VENUE_OBJ_ID);



                    map.put(TAG_VENUE_OBJ_ID, str_venue_obj_id);

                    oslist.add(map);


                    String str_lat = venue_obj.getString(TAG_LAT);

                }
                String str_status = resultsPage_obj.getString(TAG_STATUS);

            } catch (JSONException e){}
// Adding value HashMap key => value
//
//            HashMap<String, String> map = new HashMap<String, String>();
//
//            map.put(TAG_EVENT_OBJ_DISPLAYNAME, str_event_obj_displayName);
//
//            oslist.add(map);

            list = (ListView) findViewById(R.id.list);

            ListAdapter adapter = new SimpleAdapter(SearchFestivals.this, oslist, R.layout.list_item, new String[]{TAG_VENUE_OBJ_DISPLAYNAME
            //        , TAG_VENUE_OBJ_ID
            }, new int[]{R.id.name
            //        , R.id.id
            });

            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,  int position, long id) {
                    //Toast.makeText(FindFestivalsByArtist.this, "You Clicked at " + oslist.get(+position).get(TAG_DISPLAYNAME), Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(getApplicationContext(), SearchFestivalsDetails.class);
                    intent.putExtra("new_variable_name", oslist.get(+position).get(TAG_VENUE_OBJ_ID));
                    startActivity(intent);
                }
            });
        }
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
        // automatically handle clicks on the Home/Up buttonshape, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}