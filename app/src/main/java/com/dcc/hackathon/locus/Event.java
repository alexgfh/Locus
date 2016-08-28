package com.dcc.hackathon.locus;

import java.util.Date;

/**
 * Created by Alex on 8/27/2016.
 */
public class Event {
    String title;
    String description;
    double latitude;
    double longitude;
    Date startDate;
    Date endDate;

    public Event(String title, String description, double latitude, double longitude, Date startDate, Date endDate) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
