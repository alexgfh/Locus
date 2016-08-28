package layout.cadastrarusuario;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.dcc.hackathon.locus.MapsActivity;
import com.dcc.hackathon.locus.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class cadastrar_usuario extends AppCompatActivity {

    String allUsersJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cadastro_usuario);
    }
    public void cadastrar(View v) {
        //startActivity(new Intent(this, MapsActivity.class));
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute();

    }

    public void registrar(){
        for(User)

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

            String rec_url = "http://homepages.dcc.ufmg.br/~andre.assis/get_all_users.php";
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
                        allUsersJSON = sb.toString();
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
            registrar();
        }
    }

}
