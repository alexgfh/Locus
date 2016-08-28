package layout.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.dcc.hackathon.locus.MapsActivity;
import com.dcc.hackathon.locus.R;
import com.dcc.hackathon.locus.User;

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

import layout.cadastrarusuario.cadastrar_usuario;
import layout.cadastroevento.CadastroEvento;

public class Login extends AppCompatActivity {

    String allUsersJSON;

    EditText usuario;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        usuario = (EditText) findViewById(R.id.etUsuario);
        senha = (EditText) findViewById(R.id.etSenha);
    }

    public void login(View v) {

        GetUsersTask backgroundTask = new GetUsersTask(this);
        backgroundTask.execute();

        //startActivity(new Intent(this, MapsActivity.class));
    }

    public void verificar() {
        ArrayList<User> users = getUsersFromJSON(allUsersJSON);
        for(User user : users) {
            if(user.getUsuario().equals(usuario.getText().toString())) {
                if(user.getSenha().equals(senha.getText().toString())) {
                    startActivity(new Intent(this, MapsActivity.class));
                    return;
                }
                else {
                    Toast.makeText(this, "Cadastro realizado com sucesso!.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    public void cadastrar(View v) {
        startActivity(new Intent(this, cadastrar_usuario.class));
    }

    private ArrayList<User> getUsersFromJSON(String allUsersJSON) {
        JSONArray jArray = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(allUsersJSON);

            //jArray = jsonObject.getJSONArray("server_response");
            jArray = jsonObject.getJSONArray("server_response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<User> result = new ArrayList<>();

        for(int i=0; i < jArray.length(); i++) {
            try {
                JSONObject jObject = jArray.getJSONObject(i);
                String usuario = jObject.getString("usuario");
                String senha = jObject.getString("senha");
                result.add(new User("", usuario, senha));
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
