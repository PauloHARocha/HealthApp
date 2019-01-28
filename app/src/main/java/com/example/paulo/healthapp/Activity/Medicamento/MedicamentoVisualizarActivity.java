package com.example.paulo.healthapp.Activity.Medicamento;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.Model.ModelTratamento;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceMedicamento;
import com.example.paulo.healthapp.Service.ServiceTratamento;

import java.text.SimpleDateFormat;


public class MedicamentoVisualizarActivity extends AppCompatActivity {

    private SimpleDateFormat formato;

    ModelMedicamento modelMedicamento;

    private TextView tvNome;
    private TextView tvQtd;
    private TextView tvHorario;
    private TextView tvDataInicio;
    private TextView tvDataFinal;

    private ImageView iv_cor;

    private TextView tv_nomeTratamento;

    private Toolbar toolbar;

    private int medicamentoId;

    private ModelTratamento modelTratamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.medicamento_visualizar_acitivity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        medicamentoId = getIntent().getIntExtra("medicamentoId",0);
        inicializaVisualizarMedicamento();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void inicializaVisualizarMedicamento(){


        //tvNome = (TextView) findViewById(R.id.medicamento_txv_nome);
        tvQtd = (TextView) findViewById(R.id.medicamento_txv_qtd);
        tvHorario = (TextView) findViewById(R.id.medicamento_txv_horario_lembrete);
        tvDataInicio = (TextView) findViewById(R.id.medicamento_txv_data_inicio);
        tvDataFinal = (TextView) findViewById(R.id.medicamento_txv_data_final);

        //iv_cor = (ImageView) findViewById(R.id.medicamento_iv_cor);

        modelMedicamento = ServiceMedicamento.getById(medicamentoId);
        modelTratamento = ServiceTratamento.getById(modelMedicamento.getTratamento().getIdTratamento());

        tv_nomeTratamento = (TextView) findViewById(R.id.tratamento_txv_nome);
        tv_nomeTratamento.setText(modelTratamento.getNomeTratamento());

        //tvNome.setText(modelMedicamento.getNomeMedicamento());
        tvQtd.setText(modelMedicamento.getQtdMedicamento() + "");

        formato = new SimpleDateFormat("HH:mm");

        tvHorario.setText(formato.format(modelMedicamento.getHoraMedicamento()));

        formato = new SimpleDateFormat("dd/MM/yyyy");

        tvDataInicio.setText(formato.format(modelMedicamento.getDataInicial()));

        tvDataFinal.setText(formato.format(modelMedicamento.getDataFinal()));

        /*switch (modelMedicamento.getCorMedicamento()){
            case "Amarelo":
                iv_cor.setImageResource(R.drawable.amarelo);
                break;
            case "Azul":
                iv_cor.setImageResource(R.drawable.azul);
                break;
            case "Verde":
                iv_cor.setImageResource(R.drawable.verde);
                break;
            case "Vermelho":
                iv_cor.setImageResource(R.drawable.coral);
                break;
            case "Roxo":
                iv_cor.setImageResource(R.drawable.roxo);
                break;
            case "Rosa":
                iv_cor.setImageResource(R.drawable.rosa);
                break;
            default:
        }*/
    }

}
