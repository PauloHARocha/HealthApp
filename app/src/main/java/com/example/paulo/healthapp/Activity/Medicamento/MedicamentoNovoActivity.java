package com.example.paulo.healthapp.Activity.Medicamento;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
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
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.paulo.healthapp.AlertReceiver;
import com.example.paulo.healthapp.Model.ModelHistoricoMedicamento;
import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.Model.ModelPaciente;
import com.example.paulo.healthapp.Model.ModelTratamento;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceHistoricoMedicamento;
import com.example.paulo.healthapp.Service.ServiceMedicamento;
import com.example.paulo.healthapp.Service.ServicePaciente;
import com.example.paulo.healthapp.Service.ServiceTratamento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class MedicamentoNovoActivity extends AppCompatActivity {


    ModelMedicamento modelMedicamento;

    private TextView tv_nomeTratamento;
    private TextView tv_Qtd;
    private TextView tv_lembrete;
    private TextView tv_dataInicial;
    private TextView tv_dataFinal;

    //private RadioGroup rgpCores;

    private TimePickerDialog LembreteDialog;
    private DatePickerDialog dataInicialDialog;
    private DatePickerDialog dataFinalDialog;
    private AlertDialog.Builder quantidadeDialog;

    private Toolbar toolbar;

    private int tratamentoId;
    private int quantidade = 0;

    private Calendar dataInicialCalendar;
    private Calendar dataFinalCalendar;

    private ModelTratamento modelTratamento;

    //private Spinner spinner_nome_medicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.medicamento_novo_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tratamentoId = getIntent().getIntExtra("tratamentoId",0);
        inicializaNovoMedicamento();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.medicamento, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.confirmar:
                if(verificaCadastro()){
                    insereMedicamento();
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),R.string.medicamento_resposta_nao_inserido,Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }


    private void inicializaNovoMedicamento(){

        modelMedicamento = new ModelMedicamento();

        //rgpCores = (RadioGroup) findViewById(R.id.medicamento_rgp_cor);

        modelTratamento = ServiceTratamento.getById(tratamentoId);

        Calendar calendar = Calendar.getInstance();

        tv_nomeTratamento = (TextView) findViewById(R.id.tratamento_txv_nome);
        tv_nomeTratamento.setText(modelTratamento.getNomeTratamento());

        //carregaSpinner();

        setQuantidadeDialog();

        setLembreteDialog(calendar);

        setDataInicialDialog(calendar, modelTratamento.getDataInicio(), modelTratamento.getDataTermino());

        setDataFinalDialog(calendar, modelTratamento.getDataInicio(), modelTratamento.getDataTermino());

    }

    private void setQuantidadeDialog(){

        ModelPaciente modelPaciente = ServicePaciente.getById(1);
        int peso = modelPaciente.getPeso();

        if (Objects.equals(modelTratamento.getNomeTratamento(), "Fase Intensiva")){
            if (peso <= 35){
                quantidade = 2;
            }else if (peso <= 50){
                quantidade = 3;
            }else if (peso > 50){
                quantidade = 4;
            }
        }else{
            if (peso <= 35){
                quantidade = 2;
            }else if (peso <= 50){
                quantidade = 3;
            }else if (peso > 50){
                quantidade = 4;
            }
        }

        tv_Qtd = (TextView) findViewById(R.id.medicamento_txv_qtd);
        tv_Qtd.setText(String.format("%d", quantidade));
        quantidadeDialog = new AlertDialog.Builder(this);
        final NumberPicker np_quantidade = new NumberPicker(this);
        np_quantidade.setMinValue(0);
        np_quantidade.setMaxValue(10);
        np_quantidade.setWrapSelectorWheel(true);
        quantidadeDialog.setTitle("Defina a quantidade");
        quantidadeDialog.setView(np_quantidade);
        quantidadeDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quantidade = np_quantidade.getValue();
                tv_Qtd.setText(String.valueOf(quantidade));
            }
        });
        final AlertDialog alertDialog = quantidadeDialog.create();
        tv_Qtd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    private void setLembreteDialog(Calendar calendar){
        tv_lembrete = (TextView) findViewById(R.id.medicamento_tv_horario_lembrete);
        tv_lembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LembreteDialog.show();
            }
        });

        LembreteDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar lembreteCalendar = Calendar.getInstance();
                lembreteCalendar.set(0, 0, 0, hourOfDay, minute);
                String lembrete;
                lembrete = new SimpleDateFormat("HH:mm").format(lembreteCalendar.getTime());
                tv_lembrete.setText(lembrete);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY) , calendar.get(Calendar.MINUTE),true);
        LembreteDialog.setTitle("Selecione o horario do lembrete");
    }

    private void setDataInicialDialog(Calendar calendar, final Date dataInicio, final Date dataTermino){
        tv_dataInicial = (TextView) findViewById(R.id.medicamento_tv_data_inicio);
        tv_dataInicial.setText(new SimpleDateFormat("dd/MM/yyyy").format(dataInicio.getTime()));
        tv_dataInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataInicialDialog.show();
            }
        });
        calendar.setTime(dataInicio);
        dataInicialDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                dataInicialCalendar = Calendar.getInstance();
                dataInicialCalendar.set(year, monthOfYear, dayOfMonth,0,0,0);
                dataInicialCalendar.set(Calendar.MILLISECOND,0);

                //Se for verdadeiro a data inicial do medicamento vem depois da data inicial e antes da data de termino do tratamento
                if (!dataInicialCalendar.getTime().before(dataInicio) && !dataInicialCalendar.getTime().after(dataTermino)){
                    String data_inicial;

                    data_inicial = new SimpleDateFormat("dd/MM/yyyy").format(dataInicialCalendar.getTime());
                    tv_dataInicial.setText(data_inicial);

                }else if (dataInicialCalendar.getTime().before(dataInicio)){
                    Toast.makeText(getApplicationContext(),"Data anterior ao inicio do tratamento",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Data posterior ou igual ao termino do tratamento",Toast.LENGTH_SHORT).show();
                }
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dataInicialDialog.setTitle("Selecione a data inicial");

    }

    private void setDataFinalDialog(Calendar calendar, final Date dataInicio, final Date dataTermino){
        tv_dataFinal = (TextView) findViewById(R.id.medicamento_tv_data_final);
        tv_dataFinal.setText(new SimpleDateFormat("dd/MM/yyyy").format(dataTermino.getTime()));
        tv_dataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataFinalDialog.show();
            }
        });
        calendar.setTime(dataTermino);
        dataFinalDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dataFinalCalendar = Calendar.getInstance();
                dataFinalCalendar.set(year, monthOfYear, dayOfMonth,23,59,59);
                dataFinalCalendar.set(Calendar.MILLISECOND,0);

                String data_final;
                //Se for verdadeiro a data final do medicamento vem antes da data de termino e depois da data inicial do tratamento
                if (!dataFinalCalendar.getTime().after(dataTermino) && !dataFinalCalendar.getTime().before(dataInicio) ) {
                    data_final = new SimpleDateFormat("dd/MM/yyyy").format(dataFinalCalendar.getTime());
                    tv_dataFinal.setText(data_final);

                } else if (dataFinalCalendar.getTime().after(dataTermino)) {
                    Toast.makeText(getApplicationContext(), "Data posterior ao termino do tratamento", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Data anterior ao inicio do tratamento", Toast.LENGTH_SHORT).show();
                }

            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dataFinalDialog.setTitle("Selecione a data final");
    }

    private void insereMedicamento(){

        //int selecionado = rgpCores.getCheckedRadioButtonId();

        modelMedicamento.setQtdMedicamento(quantidade);
        modelMedicamento.setQtdTomados(0);
        modelMedicamento.setTratamento(modelTratamento);
        SimpleDateFormat formatLembrete = new SimpleDateFormat("HH:mm");
        try {
            modelMedicamento.setHoraMedicamento(formatLembrete.parse(tv_lembrete.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Datas de inicio e termino inseridas no modelMedicamento na funcao verificaData()

        /*switch (selecionado) {
            case R.id.medicamento_rbn_amarelo:
                modelMedicamento.setCorMedicamento("Amarelo");
                break;
            case R.id.medicamento_rbn_azul:
                modelMedicamento.setCorMedicamento("Azul");
                break;
            case R.id.medicamento_rbn_rosa:
                modelMedicamento.setCorMedicamento("Rosa");
                break;
            case R.id.medicamento_rbn_roxo:
                modelMedicamento.setCorMedicamento("Roxo");
                break;
            case R.id.medicamento_rbn_verde:
                modelMedicamento.setCorMedicamento("Verde");
                break;
            case R.id.medicamento_rbn_vermelho:
                modelMedicamento.setCorMedicamento("Vermelho");
                break;
            default:

        }*/

        ServiceMedicamento.insert(modelMedicamento);

        ServiceHistoricoMedicamento.refreshNewMedicamentos();

        //inicializaAlarmes();

        Toast.makeText(getApplicationContext(),R.string.medicamento_resposta_inserido,Toast.LENGTH_SHORT).show();

    }

    private boolean verificaCadastro() {
        if (!validaData() || !validaQtd()) {
            return false;
        }else{
            return true;
        }
    }

    private boolean validaData(){
        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
        Date Inicial = null,Final = null;
        try {
            Inicial = formatData.parse(tv_dataInicial.getText().toString());
            Final = formatData.parse(tv_dataFinal.getText().toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(Final.getTime() - Inicial.getTime() <= 86399000) {
            Toast.makeText(getApplicationContext(), "Data de termino anterior ou igual a de inicio", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            modelMedicamento.setDataInicial(Inicial);
            modelMedicamento.setDataFinal(Final);
            return true;
        }
    }
    private boolean validaQtd() {
        if (quantidade == 0) {
            Toast.makeText(getApplicationContext(), "Insira quantidade", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
   /* private void carregaSpinner(){

        spinner_nome_medicamento = (Spinner) findViewById(R.id.medicamento_spn_nome);

        // Spinner click listener
        spinner_nome_medicamento.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        ArrayList<String> nomes_medicamento = new ArrayList<>();
        nomes_medicamento.add("Rifampicina");
        nomes_medicamento.add("Isoniazida");
        nomes_medicamento.add("Pirazinamida");
        nomes_medicamento.add("Etambutol");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomes_medicamento);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //CustomSpinnerAdapter customAdapter =  new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, cats, msg);

        // attaching data adapter to spxinner
        spinner_nome_medicamento.setAdapter(dataAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        modelMedicamento.setNomeMedicamento(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/


    /*private void inicializaAlarmes(){
        List<ModelHistoricoMedicamento> listHistoricoMedicamento = ServiceHistoricoMedicamento.getListByDate(new Date(), new Date());
        if(listHistoricoMedicamento != null)
            for(int i = 0; i < listHistoricoMedicamento.size();i++){
                 ModelHistoricoMedicamento historicoMedicamento = listHistoricoMedicamento.get(i);
                 if(historicoMedicamento.getQtdTomados() < historicoMedicamento.getQtdMedicamento())
                     setAlarm(historicoMedicamento.getMedicamento().getHoraMedicamento(),
                            historicoMedicamento.getQtdTomados(),
                            historicoMedicamento.getMedicamento().getQtdMedicamento(),
                            historicoMedicamento.getIdHistoricoMedicamento());
            }
    }
    public void setAlarm(Date horario,int qtdTomados ,int qtdMedicamento, int id){


        Calendar lembrete = Calendar.getInstance();
        lembrete.setTime(horario);

        Calendar alertTime = Calendar.getInstance();
        alertTime.setTime(new Date());
        alertTime.set(Calendar.HOUR_OF_DAY, lembrete.get(Calendar.HOUR_OF_DAY));
        alertTime.set(Calendar.MINUTE, lembrete.get(Calendar.MINUTE));
        alertTime.set(Calendar.SECOND,0);
        alertTime.set(Calendar.MILLISECOND, 0);

        boolean alarmAtivo = (PendingIntent.getBroadcast(this, id, new Intent(this, AlertReceiver.class), PendingIntent.FLAG_NO_CREATE) == null);

        if (alarmAtivo){
            Intent alertIntent = new Intent(this, AlertReceiver.class);

            SimpleDateFormat formato = new SimpleDateFormat("HH:mm");

            alertIntent.putExtra("horario",formato.format(horario));
            alertIntent.putExtra("qtdTomados",qtdTomados);
            alertIntent.putExtra("qtdMedicamento",qtdMedicamento);
            alertIntent.putExtra("id",id);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime.getTime().getTime(),
                    PendingIntent.getBroadcast(this,id, alertIntent, 0));
        }

    }*/

}
