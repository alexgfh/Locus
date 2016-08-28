package layout.cadastrarusuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

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
        //startActivity(new Intent(this, MapsActivity.class));
    }

}
