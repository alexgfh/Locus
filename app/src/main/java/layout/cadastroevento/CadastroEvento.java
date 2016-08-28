package layout.cadastroevento;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.dcc.hackathon.locus.R;

import java.util.Calendar;

public class CadastroEvento extends AppCompatActivity {

    Button btHoraInicio, btHoraFim, btDataInicio, btDataFim;
    int anoInicio, anoFim, mesInicio, mesFim, diaInicio, diaFim, horaInicio, horaFim, minutoInicio, minutoFim;

    EditText etDataInicio, etDataFim, etHoraFim, etHoraInicio;

    static final int DIALOG_ID_DI = 0;
    static final int DIALOG_ID_DF = 1;
    static final int DIALOG_ID_HI = 2;
    static final int DIALOG_ID_HF = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        showDialogOnButtonClick();
        showDialogOnButtonClick_DataFim();
        showTimePickedDialog_HI();
        showTimePickedDialog_HF();

        final Calendar cal = Calendar.getInstance();
        anoInicio = cal.get(Calendar.YEAR);
        anoFim = anoInicio;
        mesInicio = cal.get(Calendar.MONTH);
        mesFim = mesInicio;
        diaInicio = cal.get(Calendar.DAY_OF_MONTH);
        diaFim = diaInicio;
        horaInicio = 12;
        horaFim = 12;
        minutoInicio = 0;
        minutoFim = 0;

        etDataInicio = (EditText) findViewById(R.id.etDataInicio);
        etDataFim = (EditText) findViewById(R.id.etDataFim);
        etHoraInicio = (EditText) findViewById(R.id.etHoraInicio);
        etHoraFim = (EditText) findViewById(R.id.etHoraFim);

        etDataInicio.setText(diaInicio+"/"+(mesInicio+1)+"/"+anoInicio);
        etDataFim.setText(diaFim+"/"+(mesFim+1)+"/"+anoFim);
        etHoraInicio.setText(String.format("%02d", horaInicio)+":"+String.format("%02d", minutoInicio));
        etHoraFim.setText(String.format("%02d", horaFim)+":"+String.format("%02d", minutoFim));


        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Tipo de evento", "Festa", "Velório", "Aula"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    public void showDialogOnButtonClick() {
        btDataInicio = (Button)findViewById(R.id.btDataInicio);
        btDataInicio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog(DIALOG_ID_DI);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID_DI)
            return new DatePickerDialog(this, dpickerListener, anoInicio, mesInicio, diaInicio);
        if (id == DIALOG_ID_DF)
            return new DatePickerDialog(this, dpickerListener2, anoFim, mesFim, diaFim);
        if (id == DIALOG_ID_HI)
            return new TimePickerDialog(CadastroEvento.this, kTimePickerListener_HI, horaInicio, minutoInicio, true);
        if (id == DIALOG_ID_HF)
            return new TimePickerDialog(CadastroEvento.this, kTimePickerListener_HF, horaFim, minutoFim, true);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int a, int m, int d) {
                    anoInicio = a;
                    mesInicio = m;
                    diaInicio = d;
                    etDataInicio.setText(diaInicio+"/"+(mesInicio+1)+"/"+anoInicio);
                }
            };

    public void showDialogOnButtonClick_DataFim() {
        btDataFim = (Button)findViewById(R.id.btDataFim);
        btDataFim.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog(DIALOG_ID_DF);
                    }
                }
        );
    }


    private DatePickerDialog.OnDateSetListener dpickerListener2 =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int a, int m, int d) {
                    anoFim = a;
                    mesFim = m;
                    diaFim = d;
                    etDataFim.setText(diaFim+"/"+(mesFim+1)+"/"+anoFim);
                }
            };

    public void showTimePickedDialog_HI() {
        btHoraInicio = (Button)findViewById(R.id.btHoraInicio);
        btHoraInicio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog(DIALOG_ID_HI);
                    }
                }
        );
    }

    public void showTimePickedDialog_HF() {
        btHoraFim = (Button)findViewById(R.id.btHoraFim);
        btHoraFim.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog(DIALOG_ID_HF);
                    }
                }
        );
    }

    protected TimePickerDialog.OnTimeSetListener kTimePickerListener_HI =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int h, int m) {
                    horaInicio = h;
                    minutoInicio = m;
                    etHoraInicio.setText(String.format("%02d", horaInicio)+":"+String.format("%02d", minutoInicio));
                    etHoraFim.setText(String.format("%02d", horaFim)+":"+String.format("%02d", minutoFim));                }
            };

    protected TimePickerDialog.OnTimeSetListener kTimePickerListener_HF =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int h, int m) {
                    horaFim = h;
                    minutoFim = m;
                    etHoraFim.setText(String.format("%02d", horaFim)+":"+String.format("%02d", minutoFim));                }
            };

    public void cadastrar(View v) {
        EditText titulo = (EditText)findViewById(R.id.etTitulo);
        Spinner spinner = (Spinner)findViewById(R.id.spinner1);
        EditText datainicio  = (EditText)findViewById(R.id.etDataInicio);
        EditText horainicio  = (EditText)findViewById(R.id.etHoraInicio);
        EditText datafim  = (EditText)findViewById(R.id.etDataFim);
        EditText horafim  = (EditText)findViewById(R.id.etHoraFim);
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
}
