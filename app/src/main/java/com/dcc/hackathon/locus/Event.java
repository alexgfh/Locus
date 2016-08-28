package com.dcc.hackathon.locus;

/**
 * Created by Alex on 8/27/2016.
 */
public class Event {
    String title;
    String description;
    double latitude;
    double longitude;

    public Event(String title, String description, double latitude, double longitude) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
