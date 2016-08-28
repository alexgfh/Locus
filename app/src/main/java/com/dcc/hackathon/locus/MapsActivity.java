package com.dcc.hackathon.locus;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import layout.cadastroevento.CadastroEvento;
import layout.visualizarEvento.VisualizarEventos;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowClickListener {

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
        mMap.setOnInfoWindowClickListener(this);

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

        Toast.makeText(MapsActivity.this, "Para adicionar um evento, pressione num ponto do mapa", Toast.LENGTH_LONG).show();
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
                String local = jObject.getString("local");
                double latitude = jObject.getDouble("latitude");
                double longitude = jObject.getDouble("longitude");
                int tipo = jObject.getInt("tipo");
                String inicio = jObject.getString("inicio");
                String fim = jObject.getString("fim");

                DateFormat df = new SimpleDateFormat("y-M-d H:m:s");
                Event event = new Event(titulo, descricao, latitude, longitude, local, tipo, df.parse(inicio), df.parse(fim));
                result.add(event);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void refreshAllMarkers() {
        ArrayList<Event> eventList = getEventsFromJSON(allEventsJSON);
        mMap.clear();
        for (Event event : eventList) {
            Date now = new Date();
            if (event.endDate.before(now)) {
                continue;
            }
            float hue = BitmapDescriptorFactory.HUE_RED;
            if (event.startDate.before(now) && event.endDate.after(now) ) {
                hue = BitmapDescriptorFactory.HUE_GREEN;
            }
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(event.latitude, event.longitude)).title(event.title)
            .icon(BitmapDescriptorFactory.defaultMarker(hue)));
            marker.setTag(event);
        }
    }

    private void addEvent(String title, String description, LatLng latLng, String local, int tipo, Date startDate, Date endDate) {
        Event event = new Event(title, description, latLng.latitude, latLng.longitude, local, tipo, startDate, endDate);
        EventProvider.addEvent(event, this);
        Date now = new Date();
        float hue = BitmapDescriptorFactory.HUE_RED;
        if (event.startDate.before(now) && event.endDate.after(now) ) {
            hue = BitmapDescriptorFactory.HUE_GREEN;
        }
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(title).icon(BitmapDescriptorFactory.defaultMarker(hue)));
        marker.setTag(event);
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
                String local = data.getStringExtra("local");
                int tipo = data.getIntExtra("tipo", 0);
                DateFormat df = new SimpleDateFormat("y-M-d H:m:s");
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = df.parse(data.getStringExtra("inicio"));
                    endDate = df.parse(data.getStringExtra("fim"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                addEvent(titulo, descricao, currentCreation, local, tipo, startDate, endDate);
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, VisualizarEventos.class);
        Event event = (Event) marker.getTag();
        intent.putExtra("titulo", event.title);
        intent.putExtra("latitude", event.latitude);
        intent.putExtra("longitude", event.longitude);
        intent.putExtra("local", event.local);
        intent.putExtra("tipo", event.tipo);
        DateFormat df = new SimpleDateFormat("y-M-d H:m:s");
        intent.putExtra("inicio", df.format(event.startDate));
        intent.putExtra("fim", df.format(event.endDate));
        intent.putExtra("descricao", event.description);
        startActivity(intent);
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
