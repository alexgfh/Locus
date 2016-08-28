package layout.login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.dcc.hackathon.locus.MapsActivity;
import com.dcc.hackathon.locus.R;

import layout.cadastroevento.CadastroEvento;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
    }

    public void login(View v) {
        startActivity(new Intent(this, MapsActivity.class));
    }
}
