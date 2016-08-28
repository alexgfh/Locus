package layout.cadastrarusuario;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.dcc.hackathon.locus.BackgroundTask;
import com.dcc.hackathon.locus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class cadastrar_usuario extends AppCompatActivity {

    String allUsersJSON;
    EditText nome;
    EditText usuario;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cadastro_usuario);
    }
    public void cadastrar(View v) {

        nome = (EditText) findViewById(R.id.etNome);
        usuario = (EditText) findViewById(R.id.etUsuario);
        senha = (EditText) findViewById(R.id.etSenha);
        GetUsersTask backgroundTask = new GetUsersTask(this);

        backgroundTask.execute();
        //startActivity(new Intent(this, MapsActivity.class));
    }

    public void verificar(){
        ArrayList<String> users = getUsersFromJSON(allUsersJSON);
        for(String user : users) {
            if(user.equals(usuario.getText().toString())) {
                Toast.makeText(this, "Esse usuário já existe.", Toast.LENGTH_LONG).show();
                return;
            }


        }
        String method = "registerUser";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,nome.getText().toString(),usuario.getText().toString(),senha.getText().toString());
        Toast.makeText(this, "Cadastro realizado com sucesso!.", Toast.LENGTH_LONG).show();
        finish();
    }

    private ArrayList<String> getUsersFromJSON(String allUsersJSON) {
        JSONArray jArray = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(allUsersJSON);

            //jArray = jsonObject.getJSONArray("server_response");
            jArray = jsonObject.getJSONArray("server_response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> result = new ArrayList<>();

        for(int i=0; i < jArray.length(); i++) {
            try {
                JSONObject jObject = jArray.getJSONObject(i);
                String usuario = jObject.getString("usuario");
                result.add(usuario);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public class GetUsersTask extends AsyncTask<String, Void, String> {
        Context ctx;

        GetUsersTask(Context ctx)
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
            verificar();
        }
    }

}
