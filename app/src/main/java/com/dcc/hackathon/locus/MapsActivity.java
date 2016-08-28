package com.dcc.hackathon.locus;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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

import layout.cadastroevento.CadastroEvento;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, CreateEventDialog.EditNameDialogListener {

    private String allEventsJSON;
    private GoogleMap mMap;

    private final LatLng ICExLocation = new LatLng(-19.869324, -43.965365);

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
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener locationListener = new MyLocationListener(mMap);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ICExLocation));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16.0f));
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

    private LatLng currentCreation = null;
    static final int CADASTRO_EVENTO = 1;
    @Override
    public void onMapLongClick(LatLng latLng) {
        startActivityForResult(new Intent(this, CadastroEvento.class), CADASTRO_EVENTO);
        currentCreation = latLng;
        /*FragmentManager fm = getSupportFragmentManager();
        CreateEventDialog createEventDialog = new CreateEventDialog();
        createEventDialog.show(fm, "fragment_edit_name");
        currentCreation = latLng;
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CADASTRO_EVENTO) {
            if (resultCode == RESULT_OK) {
                String titulo = data.getStringExtra("titulo");
                String descricao = data.getStringExtra("descricao");
                addEvent(titulo, descricao, currentCreation);
            }
        }
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
