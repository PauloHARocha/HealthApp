package com.example.paulo.healthapp.Activity.Responsavel;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.example.paulo.healthapp.Model.ModelResponsavel;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceResponsavel;


public class ResponsavelNovoActivity  extends AppCompatActivity {

    private TextInputEditText et_nome;
    private TextInputEditText et_cpf;
    private TextInputEditText et_telefone;
    private TextInputEditText et_endereco;
    private TextInputLayout inputLayoutNome;
    private TextInputLayout inputLayoutCPF;
    private TextInputLayout inputLayoutTelefone;
    private TextInputLayout inputLayoutEndereco;

    private ModelResponsavel modelResponsavel;

    private ProgressDialog pDialog;

    // url to create new responsavel
    private static String url_create_responsavel = "http://192.168.0.114/Projetos/create_responsavel.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_novo_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TextInputEditTexts
        et_nome = (TextInputEditText) findViewById(R.id.responsavel_edt_nome);
        et_cpf = (TextInputEditText) findViewById(R.id.responsavel_edt_cpf);
        et_telefone = (TextInputEditText) findViewById(R.id.responsavel_edt_telefone);
        et_endereco = (TextInputEditText) findViewById(R.id.responsavel_edt_endereco);
        //TextInputLayouts
        inputLayoutNome = (TextInputLayout) findViewById(R.id.input_layout_nome_responsavel);
        inputLayoutCPF = (TextInputLayout) findViewById(R.id.input_layout_cpf_responsavel);
        inputLayoutTelefone = (TextInputLayout) findViewById(R.id.input_layout_telefone_responsavel);
        inputLayoutEndereco = (TextInputLayout) findViewById(R.id.input_layout_endereco_responsavel);
        //Adiciona os listeners para validar o conteudo que esta sendo inserido nos TextInputEditTexts
        et_nome.addTextChangedListener(new CadastroWatcher(et_nome));
        et_cpf.addTextChangedListener(new CadastroWatcher(et_cpf));
        et_telefone.addTextChangedListener(new CadastroWatcher(et_telefone));
        et_endereco.addTextChangedListener(new CadastroWatcher(et_endereco));

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
                if(verificaCadastro()){
                    //se o cadastro for validado o responsavel é inserido no banco de dados
                    insereResponsavel();
                    finish();
                }else
                    Toast.makeText(getApplicationContext(),R.string.resposta_incompleto,Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),R.string.responsavel_resposta_nao_inserido,Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private void insereResponsavel(){

        modelResponsavel = new ModelResponsavel();

        modelResponsavel.setNomeResponsavel(et_nome.getText().toString());
        modelResponsavel.setCpf(et_cpf.getText().toString());
        modelResponsavel.setEndereco(et_telefone.getText().toString());
        modelResponsavel.setTelefone(et_endereco.getText().toString());

        ServiceResponsavel.insert(modelResponsavel);

        //insereResponsavelServidor();

        Toast.makeText(getApplicationContext(),R.string.responsavel_resposta_inserido,Toast.LENGTH_SHORT).show();

    }
    private boolean verificaCadastro() {
        if (!validaNome() || !validaCPF() || !validaTelefone()  || !validaEndereco()) {
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
    private boolean validaCPF() {
        if (et_cpf.getText().toString().length() < 11) {
            inputLayoutCPF.setError("Insira um CPF válido");
            requestFocus(et_cpf);
            return false;
        } else {
            inputLayoutCPF.setErrorEnabled(false);
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
                case R.id.responsavel_edt_nome:
                    validaNome();
                    break;
                case R.id.responsavel_edt_cpf:
                    validaCPF();
                    break;
                case R.id.responsavel_edt_telefone:
                    validaTelefone();
                    break;
                case R.id.responsavel_edt_endereco:
                    validaEndereco();
                    break;
            }
        }
    }

    private void insereResponsavelServidor(){

        final String nome = modelResponsavel.getNomeResponsavel();
        final String cpf = modelResponsavel.getCpf();
        final String telefone = modelResponsavel.getTelefone();
        final String endereco = modelResponsavel.getEndereco();
        pDialog = new ProgressDialog(ResponsavelNovoActivity.this);
        pDialog.setMessage("Creating Product..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_create_responsavel,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Resposta: " , response);
                        Toast.makeText(ResponsavelNovoActivity.this,"Responsavel inserido",Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResponsavelNovoActivity.this,"Responsavel nao pode ser inserido",Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                        finish();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("nome",nome);
                params.put("cpf",cpf);
                params.put("telefone",telefone);
                params.put("endereco",endereco);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

