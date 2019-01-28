package com.example.paulo.healthapp.Activity.Tratamento;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.Model.ModelTratamento;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceTratamento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class TratamentoNovoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ModelTratamento modelTratamento;

    private LinearLayout linearLayoutInicial;
    private LinearLayout linearLayoutfinal;

    private DatePickerDialog datePickerDialogInicial;
    private DatePickerDialog datePickerDialogFinal;

    private TextView textViewInicialData;
    private TextView textViewInicialDia;
    private TextView textViewFinalData;
    private TextView textViewFinalDia;

    private Date dateInicio;
    private Date dateFinal;

    private Spinner spinner_nome_tratamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tratamento_novo_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configuraDatePicker();

        carregaSpinner();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.responsavel, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.confirmar:
                //if(verificaCadastro()){
                    insereTratamento();
                    finish();
                //}
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),R.string.novotratamento_resposta_descartado,Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
    private boolean verificaCadastro() {
        if (!validaData()) {
            return false;
        }else{
            return true;
        }
    }

    private boolean validaData(){
        Date Inicial = modelTratamento.getDataInicio();
        Date Final = modelTratamento.getDataTermino();

        if(Final.getTime() - Inicial.getTime() <= 86399000) //valor de um dia de diferenca
        {
            Toast.makeText(getApplicationContext(), "Data de termino anterior ou igual a de inicio", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    }
    private void insereTratamento(){

        ServiceTratamento.insert(modelTratamento);

        Toast.makeText(getApplicationContext(),R.string.novotratamento_resposta_inserido,Toast.LENGTH_SHORT).show();

    }

    private void configuraDatePicker(){
        Calendar newCalendar = Calendar.getInstance();

        modelTratamento = new ModelTratamento();

        dateInicio = modelTratamento.getDataInicio();
        dateFinal = modelTratamento.getDataTermino();

        linearLayoutInicial =(LinearLayout) findViewById(R.id.medicamento_lnl_inicial);
        textViewInicialData = (TextView) findViewById(R.id.medicamento_txv_inicialdata);
        textViewInicialDia = (TextView) findViewById(R.id.medicamento_txv_inicialdia);

        textViewInicialData.setText(new SimpleDateFormat("EEE, MM/yyyy").format(dateInicio));
        textViewInicialDia.setText(new SimpleDateFormat("dd").format(dateInicio));

        linearLayoutfinal =(LinearLayout) findViewById(R.id.medicamento_lnl_final);
        textViewFinalData = (TextView) findViewById(R.id.medicamento_txv_finaldata);
        textViewFinalDia = (TextView) findViewById(R.id.medicamento_txv_finaldia);

        Calendar dataTermino = Calendar.getInstance();

        dataTermino.setTime(dateFinal);

        dataTermino.add(Calendar.DAY_OF_MONTH, 60);

        modelTratamento.setDataTermino(dataTermino.getTime());

        textViewFinalData.setText( new SimpleDateFormat("EEE, MM/yyyy").format(dataTermino.getTime()));
        textViewFinalDia.setText(new SimpleDateFormat("dd").format(dataTermino.getTime()));

        datePickerDialogInicial = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar dataInicial = Calendar.getInstance();
                dataInicial.set(year, monthOfYear, dayOfMonth,0,0,0);
                dataInicial.set(Calendar.MILLISECOND,0);

                modelTratamento.setDataInicio(dataInicial.getTime());

                dateInicio =  dataInicial.getTime();

                String data_inicial;

                data_inicial = new SimpleDateFormat("EEE, MM/yyyy").format(dataInicial.getTime());
                textViewInicialData.setText(data_inicial);

                data_inicial = new SimpleDateFormat("dd").format(dataInicial.getTime());
                textViewInicialDia.setText(data_inicial);

                if (Objects.equals(modelTratamento.getNomeTratamento(), "Fase de Manutenção")){
                    dataInicial.add(Calendar.DAY_OF_MONTH, 120);
                }else{
                    dataInicial.add(Calendar.DAY_OF_MONTH, 60);
                }

                modelTratamento.setDataTermino(dataInicial.getTime());

                dateFinal = dataInicial.getTime();


                textViewFinalData.setText(new SimpleDateFormat("EEE, MM/yyyy").format(dataInicial.getTime()));
                textViewFinalDia.setText(new SimpleDateFormat("dd").format(dataInicial.getTime()));


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /*datePickerDialogFinal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar dataFinal = Calendar.getInstance();
                dataFinal.set(year, monthOfYear, dayOfMonth,23,59,59);
                dataFinal.set(Calendar.MILLISECOND,0);

                modelTratamento.setDataTermino(dataFinal.getTime());

                String data_final;

                data_final = new SimpleDateFormat("EEE, MM/yyyy").format(dataFinal.getTime());
                textViewFinalData.setText(data_final);

                data_final = new SimpleDateFormat("dd").format(dataFinal.getTime());
                textViewFinalDia.setText(data_final);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        */

        //Abrir calendario
        linearLayoutInicial.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                datePickerDialogInicial.show();
            }
        });

        /*linearLayoutfinal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                datePickerDialogFinal.show();
            }
        });
        */
    }

    private void carregaSpinner(){

        spinner_nome_tratamento = (Spinner) findViewById(R.id.tratamento_spn_nome);

        // Spinner click listener
        spinner_nome_tratamento.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        ArrayList<String> nomes_tratamento = new ArrayList<>();
        nomes_tratamento.add("Fase Intensiva");
        nomes_tratamento.add("Fase de Manutenção");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomes_tratamento);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //CustomSpinnerAdapter customAdapter =  new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, cats, msg);

        // attaching data adapter to spxinner
        spinner_nome_tratamento.setAdapter(dataAdapter);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tipo_tratamento = parent.getItemAtPosition(position).toString();
        linearLayoutfinal =(LinearLayout) findViewById(R.id.medicamento_lnl_final);
        textViewFinalData = (TextView) findViewById(R.id.medicamento_txv_finaldata);
        textViewFinalDia = (TextView) findViewById(R.id.medicamento_txv_finaldia);

        Calendar dataTermino = Calendar.getInstance();
        dataTermino.setTime(dateInicio);

        modelTratamento.setDataTermino(dataTermino.getTime());
        if (Objects.equals(tipo_tratamento, "Fase Intensiva")){
            modelTratamento.setDoses(60);
            Log.e("doses", modelTratamento.getDoses()+ "");
            dataTermino.add(Calendar.DAY_OF_MONTH, 60);
        }else{
            modelTratamento.setDoses(120);
            Log.e("doses", modelTratamento.getDoses()+ "");
            dataTermino.add(Calendar.DAY_OF_MONTH, 120);
        }
        modelTratamento.setNomeTratamento(tipo_tratamento);
        modelTratamento.setDataTermino(dataTermino.getTime());

        textViewFinalData.setText(new SimpleDateFormat("EEE, MM/yyyy").format(dataTermino.getTime()));
        textViewFinalDia.setText(new SimpleDateFormat("dd").format(dataTermino.getTime()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
