package com.dcc.hackathon.locus;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.Date;

/**
 * Created by Alex on 8/27/2016.
 */
public class EventProvider {


   String titulo = "Testando";

    public static ArrayList<Event> getEventListf() {
        ArrayList<Event> list = new ArrayList<Event>();
        Event event1 = new Event("primeiro", "descricao1", 4.0f, 20.0f);
        Event event2 = new Event("segundo", "descricao2", -5.2f, 18.5f);
        list.add(event1);
        list.add(event2);
        return list;

    }

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
                Event event = new Event(titulo, descricao, latitude, longitude);
                result.add(event);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void addEvent(Event event,Context ctx) {

        String method = "registerEvent";

        int tipo = 7; //Deletar essa variável após o Event estiver pronto e preenchendo as variáveis.
        Date inicio = new Date(); //Deletar essa variável após o Event estiver pronto e preenchendo as variáveis.
        Date fim = new Date(); //Deletar essa variável após o Event estiver pronto e preenchendo as variáveis.

        BackgroundTask backgroundTask = new BackgroundTask(ctx);
        backgroundTask.execute(method,event.title, event.description, String.valueOf(event.latitude),
                String.valueOf(event.longitude),String.valueOf(tipo),String.valueOf(inicio),String.valueOf(fim) );

        /*
        String method = "registerUser";

        String nome = "Teste";
        String senha = "Testando";

        BackgroundTask backgroundTask = new BackgroundTask(ctx);
        backgroundTask.execute(method,nome,senha);
        */
    }
}
