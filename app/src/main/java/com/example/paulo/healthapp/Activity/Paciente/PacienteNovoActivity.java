package com.example.paulo.healthapp.Activity.Paciente;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paulo.healthapp.Model.ModelPaciente;
import com.example.paulo.healthapp.Model.ModelResponsavel;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServicePaciente;
import com.example.paulo.healthapp.Service.ServiceResponsavel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Paulo on 18/08/2016.
 */
public class PacienteNovoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputEditText et_nome;
    private TextInputEditText et_registro;
    private TextInputEditText et_cartao;
    private TextInputEditText et_telefone;
    private TextInputEditText et_endereco;

    private TextInputLayout inputLayoutNome;
    private TextInputLayout inputLayoutRegistro;
    private TextInputLayout inputLayoutCartao;
    private TextInputLayout inputLayoutTelefone;
    private TextInputLayout inputLayoutEndereco;

    private TextView tv_data_nascimento;
    private TextView tv_peso;
    private int peso = 1;

    private DatePickerDialog datePickerDialogInicial;
    private RadioGroup rpgTipoTuberculose;

    private List<String> categories;
    private Spinner spinner;

    private AlertDialog.Builder pesoDialog;

    private SimpleDateFormat simpleFormat;

    private ModelPaciente modelPaciente;
    private Intent intent;

    private String responsavel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_editar_activity);

        carregaSpinner();

        carregaDatePicker();

        inicializaCampos();

        carregaTuberculose();

        setPesoDialog();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editar_paciente, menu);
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
                    adicionaPaciente();
                    finish();
                }else
                    Toast.makeText(getApplicationContext(), R.string.resposta_incompleto, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int resposta;
        resposta = R.string.paciente_resposta_nao_inserido;
        Toast.makeText(getApplicationContext(),resposta, Toast.LENGTH_SHORT).show();
        finish();
        super.onBackPressed();
    }

    private void adicionaPaciente(){

        int resposta;
        //Salvar paciente
        modelPaciente.setNomePaciente(et_nome.getText().toString());
        modelPaciente.setResponsavel(responsavel);
        modelPaciente.setnDeRegistroDaUnidadeSaude(et_registro.getText().toString());
        modelPaciente.setCartaoNacionalDeSaude(et_cartao.getText().toString());
        modelPaciente.setTelefone(et_telefone.getText().toString());
        modelPaciente.setEndereco(et_endereco.getText().toString());
        modelPaciente.setPeso(peso);

        int selecionado = rpgTipoTuberculose.getCheckedRadioButtonId();
        switch (selecionado) {
            case R.id.paciente_rdbtn_pulmonar:
                modelPaciente.setTuberculose(getApplicationContext().getResources().getString(R.string.paciente_tuberculose_pulmonar));
                break;
            case R.id.paciente_rdbtn_extrapulmonar:
                modelPaciente.setTuberculose(getApplicationContext().getResources().getString(R.string.paciente_tuberculose_extrapulmonar));
                break;
            case R.id.paciente_rdbtn_mista:
                modelPaciente.setTuberculose(getApplicationContext().getResources().getString(R.string.paciente_tuberculose_mista));
                break;
        }
        try {
            modelPaciente.setDataDeNascimento(simpleFormat.parse(tv_data_nascimento.getText().toString()));
        }
        catch (ParseException e) {

        }

        ServicePaciente.insert(modelPaciente);
        resposta = R.string.paciente_resposta_inserido;

        Toast.makeText(getApplicationContext(), resposta, Toast.LENGTH_SHORT).show();

    }

    private void inicializaCampos(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_nome           = (TextInputEditText) findViewById(R.id.paciente_edt_nome);
        et_registro = (TextInputEditText) findViewById(R.id.paciente_edt_numero_registro);
        et_cartao    = (TextInputEditText) findViewById(R.id.paciente_edt_cartao_saude);
        et_telefone       = (TextInputEditText) findViewById(R.id.paciente_edt_telefone);
        et_endereco       = (TextInputEditText) findViewById(R.id.paciente_edt_endereco);

        inputLayoutNome = (TextInputLayout) findViewById(R.id.input_layout_nome_paciente);
        inputLayoutRegistro = (TextInputLayout) findViewById(R.id.input_layout_registro_paciente);
        inputLayoutCartao = (TextInputLayout) findViewById(R.id.input_layout_cartao_paciente);
        inputLayoutTelefone = (TextInputLayout) findViewById(R.id.input_layout_telefone_paciente);
        inputLayoutEndereco = (TextInputLayout) findViewById(R.id.input_layout_endereco_paciente);

        //Adiciona os listeners para validar o conteudo que esta sendo inserido nos TextInputEditTexts
        et_nome.addTextChangedListener(new CadastroWatcher(et_nome));
        et_registro.addTextChangedListener(new CadastroWatcher(et_registro));
        et_cartao.addTextChangedListener(new CadastroWatcher(et_cartao));
        et_telefone.addTextChangedListener(new CadastroWatcher(et_telefone));
        et_endereco.addTextChangedListener(new CadastroWatcher(et_endereco));

        modelPaciente = new ModelPaciente();


    }
    private boolean verificaCadastro() {
        if (!validaNome() || !validaRegistro() || !validaCartao() || !validaTelefone()  || !validaEndereco()) {
            return false;
        }else{
            return true;
        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean validaNome() {
        if (et_nome.getText().toString().trim().isEmpty()) {
            inputLayoutNome.setErrorEnabled(true);
            inputLayoutNome.setError("Insira o nome completo");
            requestFocus(et_nome);
            return false;
        } else {
            inputLayoutNome.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validaRegistro() {
        if (et_registro.getText().toString().length() < 5) {
            inputLayoutRegistro.setError("Insira um registro válido");
            requestFocus(et_registro);
            return false;
        } else {
            inputLayoutRegistro.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validaCartao() {
        if (et_cartao.getText().toString().length() < 5) {
            inputLayoutCartao.setError("Insira um cartao válido");
            requestFocus(et_cartao);
            return false;
        } else {
            inputLayoutCartao.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validaTelefone() {
        if (et_telefone.getText().toString().trim().isEmpty()) {
            inputLayoutTelefone.setError("Insira um número de telefone");
            requestFocus(et_telefone);
            return false;
        } else {
            inputLayoutTelefone.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validaEndereco() {
        if (et_endereco.getText().toString().trim().isEmpty()) {
            inputLayoutEndereco.setError("Insira o endereço completo");
            requestFocus(et_endereco);
            return false;
        } else {
            inputLayoutEndereco.setErrorEnabled(false);
        }
        return true;
    }

    private class CadastroWatcher implements TextWatcher {

        private View view;

        private CadastroWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.paciente_edt_nome:
                    validaNome();
                    break;
                case R.id.paciente_edt_numero_registro:
                    validaRegistro();
                    break;
                case R.id.paciente_edt_cartao_saude:
                    validaCartao();
                    break;
                case R.id.paciente_edt_telefone:
                    validaTelefone();
                    break;
                case R.id.paciente_edt_endereco:
                    validaEndereco();
                    break;
            }
        }
    }

    private void carregaTuberculose(){
        rpgTipoTuberculose = (RadioGroup) findViewById(R.id.paciente_rdg_tuberculose);

        rpgTipoTuberculose.check(R.id.paciente_rdbtn_pulmonar);

    }

    private void carregaDatePicker(){

        Calendar newCalendar = Calendar.getInstance();

        simpleFormat = new SimpleDateFormat("dd/MM/yyyy");

        tv_data_nascimento = (TextView) findViewById(R.id.paciente_tv_data_nascimento);

        tv_data_nascimento.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                datePickerDialogInicial.show();
            }
        });
        datePickerDialogInicial = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                String data_nascimento;

                data_nascimento = new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime());
                tv_data_nascimento.setText(data_nascimento);

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private void carregaSpinner(){

        spinner = (Spinner) findViewById(R.id.paciente_spn_responsavel);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        categories = new ArrayList<>();

        List<ModelResponsavel> responsaveis = ServiceResponsavel.getList();

        for(ModelResponsavel it: responsaveis){
            categories.add(it.getNomeResponsavel());
        }

        TextView respMsg = (TextView) findViewById(R.id.paciente_txt_sem_responsavel);

        if(responsaveis.size() > 0){
            respMsg.setVisibility(View.GONE);
        }else{
            respMsg.setVisibility(View.VISIBLE);
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //CustomSpinnerAdapter customAdapter =  new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, cats, msg);

        // attaching data adapter to spxinner
        spinner.setAdapter(dataAdapter);

    }

    private void setPesoDialog(){

        tv_peso = (TextView) findViewById(R.id.paciente_tv_peso);
        pesoDialog = new AlertDialog.Builder(this);
        final NumberPicker np_peso = new NumberPicker(this);
        np_peso.setMinValue(0);
        np_peso.setMaxValue(300);
        np_peso.setWrapSelectorWheel(true);
        pesoDialog.setTitle("Defina a quantidade");
        pesoDialog.setView(np_peso);
        pesoDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                peso = np_peso.getValue();
                tv_peso.setText(String.valueOf(peso));
            }
        });
        final AlertDialog alertDialog = pesoDialog.create();
        tv_peso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        responsavel = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}


