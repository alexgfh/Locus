package layout.editarevento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.dcc.hackathon.locus.R;

public class editarevento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarevento);
        EditText etTituloDoEvento, etDataInicio, etHoraInicio, etDataFim, etHoraFim, etLocal, etDescricao;
        Intent intent = getIntent();
        etTituloDoEvento = (EditText) findViewById(R.id.etEdTitulo);
        etDataInicio = (EditText) findViewById(R.id.etEdDataInicio);
        etDataFim = (EditText) findViewById(R.id.etEdDataFim);
        etHoraInicio = (EditText) findViewById(R.id.etEdHoraInicio);
        etHoraFim = (EditText) findViewById(R.id.etEdHoraFim);
        etLocal = (EditText) findViewById(R.id.etEdLocal);
        etDescricao = (EditText) findViewById(R.id.etEdDescricao);

        etTituloDoEvento.setText(intent.getStringExtra("titulo"));
        etDescricao.setText(intent.getStringExtra("descricao"));


    }
}
