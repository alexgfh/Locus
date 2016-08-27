package com.dcc.hackathon.locus;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Alex on 8/27/2016.
 */
public class EventProvider {


   String titulo = "Testando";

    public static ArrayList<Event> getEventList() {
        ArrayList<Event> list = new ArrayList<Event>();
        Event event1 = new Event("primeiro", 4.0f, 20.0f);
        Event event2 = new Event("segundo", -5.2f, 18.5f);
        list.add(event1);
        list.add(event2);
        return list;
    }

    public static void addEvent(Event event,Context ctx) {
    String method = "register";
        String titulo = "bla";
        BackgroundTask backgroundTask = new BackgroundTask(ctx);
        backgroundTask.execute(method,titulo);
    }
}
