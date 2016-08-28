package com.dcc.hackathon.locus;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Alex on 8/28/2016.
 */
public class MyLocationListener implements LocationListener {
    public Location location;
    public boolean available = false;
    public boolean everBeenAvailable = false;
    private GoogleMap mMap;
    @Override
    public void onLocationChanged(Location loc) {
        location = loc;
    }

    public MyLocationListener(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public LatLng getLatLng() {
        if(available)
            return new LatLng(location.getLatitude(), location.getLongitude());
        else
            return null;
    }

    @Override
    public void onProviderDisabled(String provider) {available = false;}

    @Override
    public void onProviderEnabled(String provider) {
        if(!everBeenAvailable) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
            everBeenAvailable = true;
        }
        available = true;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}