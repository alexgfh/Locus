package layout.visualizarEvento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dcc.hackathon.locus.R;
import com.dcc.hackathon.locus.TiposDeEvento;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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


        DateFormat dbDF = new SimpleDateFormat("y-M-d H:m:s");
        String inicio = intent.getStringExtra("inicio");
        String fim = intent.getStringExtra("fim");
        Date dateInicio = null, dateFim = null;
        try {
            dateInicio = dbDF.parse(inicio);
            dateFim = dbDF.parse(fim);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat displayDF = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String displayInicio = displayDF.format(dateInicio);
        String displayFim = displayDF.format(dateFim);

        titulo.setText(intent.getStringExtra("titulo"));
        tipo.setText(TiposDeEvento.get(intent.getIntExtra("tipo", 0)));
        descricao.setText(intent.getStringExtra("descricao"));
        datahora.setText(displayInicio + " até " + displayFim);
        String locationText = intent.getStringExtra("local");
        local.setText(locationText);

        ImageView imgCapa = (ImageView)findViewById(R.id.imCapa);
        ImageView imgMapa = (ImageView)findViewById(R.id.imMapa);

        imgCapa.setScaleType(ImageView.ScaleType.FIT_XY);
        imgMapa.setScaleType(ImageView.ScaleType.FIT_XY);

    }
}
