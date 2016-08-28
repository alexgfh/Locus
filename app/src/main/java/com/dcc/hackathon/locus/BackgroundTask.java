package com.dcc.hackathon.locus;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by andre on 27/08/2016.
 */
public class BackgroundTask extends AsyncTask<String, Void, String>{

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

        String reg_url = "http://homepages.dcc.ufmg.br/~andre.assis/register.php";
        String reg_url2 = "http://homepages.dcc.ufmg.br/~andre.assis/registerUser.php";
        String rec_url = "http://homepages.dcc.ufmg.br/~andre.assis/get_all_events.php";
        String method = params[0];
        if(method.equals("registerEvent"))
        {
            String title = params[1];
            String description = params[2];
            String latitude = params[3];
            String longitude = params[4];
            String local = params[5];
            String tipo = params[6];
            String inicio = params[7];
            String fim = params[8];

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("titulo","UTF-8") + "=" + URLEncoder.encode(title,"UTF-8")
                        + "&" + URLEncoder.encode("descricao","UTF-8") + "=" + URLEncoder.encode(description,"UTF-8")
                        + "&" + URLEncoder.encode("latitude","UTF-8") + "=" + URLEncoder.encode(latitude,"UTF-8")
                        + "&" + URLEncoder.encode("longitude","UTF-8") + "=" + URLEncoder.encode(longitude,"UTF-8")
                        + "&" + URLEncoder.encode("local","UTF-8") + "=" + URLEncoder.encode(local,"UTF-8")
                        + "&" + URLEncoder.encode("tipo","UTF-8") + "=" + URLEncoder.encode(tipo,"UTF-8")
                        + "&" + URLEncoder.encode("inicio","UTF-8") + "=" + URLEncoder.encode(inicio,"UTF-8")
                        + "&" + URLEncoder.encode("fim","UTF-8") + "=" + URLEncoder.encode(fim,"UTF-8");
                // Para adicionar mais basta colocar um + " & " + URLEncoder.encode("descricao","UTF-8") + " = " + URLEncoder.encode(descricao,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registration Success";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (method.equals("receive")) {
            try {
                URL url = new URL(reg_url);
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
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        return sb.toString();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("registerUser"))
        {
            String nome = params[1];
            String senha = params[2];
            try {
                URL url = new URL(reg_url2);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("nome","UTF-8") + "=" + URLEncoder.encode(nome,"UTF-8")
                        + "&" + URLEncoder.encode("senha","UTF-8") + "=" + URLEncoder.encode(senha,"UTF-8");
                // Para adicionar mais basta colocar um + "&" + URLEncoder.encode("descricao","UTF-8") + "=" + URLEncoder.encode(descricao,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(IS));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                IS.close();
                return "Registration Success";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result)
    {

    }
}
