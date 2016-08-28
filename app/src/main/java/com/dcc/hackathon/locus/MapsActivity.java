package com.dcc.hackathon.locus;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, CreateEventDialog.EditNameDialogListener {

    String allEventsJSON;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute();
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);

        // Add a marker in Sydney and move the camera
/*        ArrayList<Event> list = EventProvider.getEventList(this);
        for (Event event : list) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(event.latitude, event.longitude)).title(event.title));
        }*/

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private ArrayList<Event> getEventsFromJSON(String allEventsJSON) {
        JSONArray jArray = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(allEventsJSON);

            //jArray = jsonObject.getJSONArray("server_response");
            jArray = jsonObject.getJSONArray("server_response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Event> result = new ArrayList<>();

        for(int i=0; i < jArray.length(); i++) {
            try {
                JSONObject jObject = jArray.getJSONObject(i);
                String titulo = jObject.getString("titulo");
                String descricao = jObject.getString("descricao");
                double latitude = jObject.getDouble("latitude");
                double longitude = jObject.getDouble("longitude");
                int tipo = jObject.getInt("tipo");
                String inicio = jObject.getString("inicio");
                String fim = jObject.getString("inicio");
                Event event = new Event(titulo, descricao, latitude, longitude);
                result.add(event);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void refreshAllMarkers() {
        ArrayList<Event> eventList = getEventsFromJSON(allEventsJSON);
        mMap.clear();
        for (Event event : eventList) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(event.latitude, event.longitude)).title(event.title));
        }
    }

    private void addEvent(String title, String description, LatLng latLng) {
        Event event = new Event(title, description, latLng.latitude, latLng.longitude);
        EventProvider.addEvent(event, this);
        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        Toast toast = Toast.makeText(this.getApplicationContext(), "Evento Criado!", Toast.LENGTH_LONG);
        toast.show();
    }

    LatLng currentCreation = null;

    @Override
    public void onMapLongClick(LatLng latLng) {

        FragmentManager fm = getSupportFragmentManager();
        CreateEventDialog createEventDialog = new CreateEventDialog();
        createEventDialog.show(fm, "fragment_edit_name");
        currentCreation = latLng;
    }

    @Override
    public void onFinishEditDialog(String title, String description) {
        this.addEvent(title, description, currentCreation);
    }


    public class BackgroundTask extends AsyncTask<String, Void, String> {

        Context ctx;

        BackgroundTask(Context ctx)
        {
            this.ctx = ctx;

        }

        @Override
        protected void onPreExecute(){

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String rec_url = "http://homepages.dcc.ufmg.br/~andre.assis/get_all_events.php";
            StringBuilder sb = null;
                try {
                    URL url = new URL(rec_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-length", "0");
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setAllowUserInteraction(false);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.connect();

                    int status = httpURLConnection.getResponseCode();
                    switch (status) {
                        case 200:
                        case 201:
                            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                            sb = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            br.close();
                            allEventsJSON = sb.toString();
                            return sb.toString();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(sb!=null)
                return sb.toString();
            else
                return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result)
        {
            refreshAllMarkers();
        }
    }

}
