package com.dcc.hackathon.locus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Alex on 8/27/2016.
 */
public class EventProvider {

    public static ArrayList<Event> getEventList() {
        ArrayList<Event> list = new ArrayList<Event>();
        Event event1 = new Event("primeiro", 4.0f, 20.0f);
        Event event2 = new Event("segundo", -5.2f, 18.5f);
        list.add(event1);
        list.add(event2);
        return list;

    }

    private static String url = "localhost";
    private static int timeout = 2;

    public static ArrayList<Event> getEventListFromURL() {
        JSONObject jArray = JSONfunctions.getJSON(url, 2);

        for(int i=0; i < jArray.length(); i++) {

            try {
                JSONObject jObject = jArray.getJSONObject(""+i);


            //String name = jObject.getString("name");
            //String tab1_text = jObject.getString("tab1_text");
            //int active = jObject.getInt("active");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void addEvent(Event event) {

    }
}
