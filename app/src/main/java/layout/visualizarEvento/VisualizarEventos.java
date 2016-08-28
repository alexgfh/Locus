package layout.visualizarEvento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dcc.hackathon.locus.R;

import org.w3c.dom.Text;

public class VisualizarEventos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_eventos);

        Intent intent = getIntent();

        TextView titulo = (TextView)findViewById(R.id.etTitulo);
        TextView tipo = (TextView)findViewById(R.id.etTipo);
        TextView datahora  = (TextView) findViewById(R.id.editText);
        TextView local  = (TextView)findViewById(R.id.textLocal);
        TextView descricao  = (TextView)findViewById(R.id.editText7);

        titulo.setText(intent.getStringExtra("titulo"));
        //tipo.setText(intent.getStringExtra("tipo"));
        descricao.setText(intent.getStringExtra("descricao"));
        datahora.setText(intent.getStringExtra("inicio") + " até " + intent.getStringExtra("fim"));
        String locationText = intent.getDoubleExtra("latitude", 0.0) + ", " + intent.getDoubleExtra("longitude", 0.0);
        local.setText(locationText);

        ImageView imgCapa = (ImageView)findViewById(R.id.imCapa);
        ImageView imgMapa = (ImageView)findViewById(R.id.imMapa);

        imgCapa.setScaleType(ImageView.ScaleType.FIT_XY);
        imgMapa.setScaleType(ImageView.ScaleType.FIT_XY);

    }
}
