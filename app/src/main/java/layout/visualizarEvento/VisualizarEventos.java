package layout.visualizarEvento;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.dcc.hackathon.locus.R;

public class VisualizarEventos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_eventos);

        ImageView imgCapa = (ImageView)findViewById(R.id.imCapa);
        ImageView imgMapa = (ImageView)findViewById(R.id.imMapa);

        imgCapa.setScaleType(ImageView.ScaleType.FIT_XY);
        imgMapa.setScaleType(ImageView.ScaleType.FIT_XY);

    }
}
