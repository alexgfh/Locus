package layout.cadastroevento;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.dcc.hackathon.locus.R;

public class CadastroEvento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Tipo de evento", "Festa", "Velório", "Aula"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    public void cadastrar(View v) {
        EditText titulo = (EditText)findViewById(R.id.etTitulo);
        Spinner spinner = (Spinner)findViewById(R.id.spinner1);
        EditText datainicio  = (EditText)findViewById(R.id.editText);
        EditText horainicio  = (EditText)findViewById(R.id.editText2);
        EditText datafim  = (EditText)findViewById(R.id.editText5);
        EditText horafim  = (EditText)findViewById(R.id.editText6);
        EditText local  = (EditText)findViewById(R.id.editText3);
        EditText descricao  = (EditText)findViewById(R.id.editText7);

        Intent intent = new Intent();

        intent.putExtra("titulo",  titulo.getText().toString());
        intent.putExtra("tipo",  spinner.getSelectedItemId());
        intent.putExtra("datainicio",  datainicio.getText().toString());
        intent.putExtra("horainicio",  horainicio.getText().toString());
        intent.putExtra("datafim",  datafim.getText().toString());
        intent.putExtra("horafim",  horafim.getText().toString());
        intent.putExtra("local",  local.getText().toString());
        intent.putExtra("descricao",  descricao.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void cancel(View v) {
        setResult(Activity.RESULT_CANCELED, new Intent());
        finish();
    }
}
