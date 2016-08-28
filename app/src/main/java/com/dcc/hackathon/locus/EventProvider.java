package com.dcc.hackathon.locus;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.Date;

/**
 * Created by Alex on 8/27/2016.
 */
public class EventProvider {


   String titulo = "Testando";

    private static String url = "http://homepages.dcc.ufmg.br/~andre.assis/get_all_events.php";
    private static int timeout = 2;

    public static ArrayList<Event> getEventList(Context ctx) {
        String method = "receive";

        BackgroundTask backgroundTask = new BackgroundTask(ctx);
        backgroundTask.execute(method);
        String JSONString = null;
        try {
            JSONString = backgroundTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        JSONObject jArray = null;
        try {
            jArray = new JSONObject(JSONString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Event> result = new ArrayList<>();

        for(int i=0; i < jArray.length(); i++) {
            try {
                JSONObject jObject = jArray.getJSONObject(""+i);
                String titulo = jObject.getString("titulo");
                String descricao = jObject.getString("descricao");
                double latitude = jObject.getDouble("latitude");
                double longitude = jObject.getDouble("longitude");
                String startDate = jObject.getString("inicio");
                String endDate = jObject.getString("fim");
                DateFormat df = new SimpleDateFormat("y-M-d H:m:s");
                Event event = new Event(titulo, descricao, latitude, longitude, df.parse(startDate), df.parse(endDate));
                result.add(event);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void addEvent(Event event,Context ctx) {

        String method = "registerEvent";

        int tipo = 7; //Deletar essa variável após o Event estiver pronto e preenchendo as variáveis.
        Date inicio = event.startDate; //Deletar essa variável após o Event estiver pronto e preenchendo as variáveis.
        Date fim = event.endDate; //Deletar essa variável após o Event estiver pronto e preenchendo as variáveis.

        DateFormat df = new SimpleDateFormat("y-M-d H:m:s");

        BackgroundTask backgroundTask = new BackgroundTask(ctx);
        backgroundTask.execute(method,event.title, event.description, String.valueOf(event.latitude),
                String.valueOf(event.longitude),String.valueOf(tipo),df.format(inicio),df.format(fim) );

        /*

        */
    }
}
