package layout.cadastrarusuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.dcc.hackathon.locus.BackgroundTask;
import com.dcc.hackathon.locus.MapsActivity;
import com.dcc.hackathon.locus.R;


public class cadastrar_usuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cadastro_usuario);
    }
    public void cadastrar(View v) {
        String method = "registerUser";
        EditText nome = (EditText) findViewById(R.id.etNome);
        EditText usuario = (EditText) findViewById(R.id.etUsuario);
        EditText senha = (EditText) findViewById(R.id.etSenha);
        BackgroundTask backgroundTask = new BackgroundTask(this);

        backgroundTask.execute(method,nome.getText().toString(),usuario.getText().toString(),senha.getText().toString());
        //startActivity(new Intent(this, MapsActivity.class));
    }

}
