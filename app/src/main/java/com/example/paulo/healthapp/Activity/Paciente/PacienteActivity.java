package com.example.paulo.healthapp.Activity.Paciente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.paulo.healthapp.Fragment.LoginDialogFragment;
import com.example.paulo.healthapp.Model.ModelPaciente;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceLogin;
import com.example.paulo.healthapp.Service.ServicePaciente;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PacienteActivity extends AppCompatActivity {
    private ModelPaciente modelPaciente;
    private SimpleDateFormat simpleFormat;

    private TextView tv_nome;
    private TextView tv_responsavel;
    private TextView tv_numero_registro;
    private TextView tv_cartao_saude;
    private TextView tv_data_nascimento;
    private TextView tv_telefone;
    private TextView tv_endereco;
    private TextView tv_tuberculose;
    private TextView tv_peso;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializaCampos();

        preencheCampos();

    }

    @Override
    protected void onResume() {
        super.onResume();
        preencheCampos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.paciente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.editar_paciente:
                if(ServiceLogin.getLoginStatus(1).getStatus() == 0){
                    acessoLogin("EditarPaciente");
                }else{
                    startActivity(new Intent(getApplicationContext(), PacienteEditarActivity.class));
                }

                //startActivity(new Intent(getApplicationContext(),PacienteEditarActivity.class));
                //finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializaCampos(){
        tv_nome = (TextView) findViewById(R.id.paciente_tv_nome);
        tv_responsavel = (TextView) findViewById(R.id.paciente_tv_responsavel);
        tv_numero_registro = (TextView) findViewById(R.id.paciente_tv_numero_registro);
        tv_cartao_saude = (TextView) findViewById(R.id.paciente_tv_cartao_saude);
        tv_data_nascimento = (TextView) findViewById(R.id.visualizar_paciente_tv_data_nascimento);
        tv_telefone = (TextView) findViewById(R.id.paciente_tv_telefone);
        tv_endereco = (TextView) findViewById(R.id.paciente_tv_endereco);
        tv_tuberculose = (TextView) findViewById(R.id.paciente_tv_tuberculose);
        tv_peso = (TextView) findViewById(R.id.visualizar_paciente_tv_peso);
    }

    private void preencheCampos(){
        // Preencher view

        modelPaciente = ServicePaciente.getById(1);

        tv_nome.setText(modelPaciente.getNomePaciente());
        if(modelPaciente.getResponsavel().equals("null"))
            tv_responsavel.setText(modelPaciente.getNomePaciente());
        else
            tv_responsavel.setText(modelPaciente.getResponsavel());
        tv_numero_registro.setText(modelPaciente.getnDeRegistroDaUnidadeSaude());
        tv_cartao_saude.setText(modelPaciente.getCartaoNacionalDeSaude());

        simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            tv_data_nascimento.setText(simpleFormat.format(modelPaciente.getDataDeNascimento()));
        } catch (Exception e) {
        }

        tv_telefone.setText(modelPaciente.getTelefone());
        tv_endereco.setText(modelPaciente.getEndereco());

        tv_tuberculose.setText(modelPaciente.getTuberculose());

        tv_peso.setText(String.format("%d", modelPaciente.getPeso()));
    }
    private void acessoLogin(String activity){
        ArrayList<String> valores = new ArrayList<>();
        valores.add(activity);
        Bundle args = new Bundle();
        args.putStringArrayList("activity", valores);

        android.app.FragmentManager fm = getFragmentManager();
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.setArguments(args);
        loginDialogFragment.show(fm, null);
    }
}

