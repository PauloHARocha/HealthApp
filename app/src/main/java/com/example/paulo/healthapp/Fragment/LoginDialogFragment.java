package com.example.paulo.healthapp.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.paulo.healthapp.Activity.Medicamento.MedicamentoNovoActivity;
import com.example.paulo.healthapp.Activity.Paciente.PacienteEditarActivity;
import com.example.paulo.healthapp.Activity.Paciente.PacienteNovoActivity;
import com.example.paulo.healthapp.Activity.Responsavel.ResponsavelEditarActivity;
import com.example.paulo.healthapp.Activity.Responsavel.ResponsavelListActivity;
import com.example.paulo.healthapp.Activity.Responsavel.ResponsavelNovoActivity;
import com.example.paulo.healthapp.Activity.Tratamento.TratamentoNovoActivity;
import com.example.paulo.healthapp.Model.ModelLogin;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceLogin;

import java.util.ArrayList;


/**
 * Created by Paulo on 18/08/2016.
 */
public class LoginDialogFragment extends DialogFragment {
    private AlertDialog.Builder loginDialogBuilder;
    private TextInputLayout inputLayoutLogin;
    private TextInputLayout inputLayoutSenha;
    private TextInputEditText et_login;
    private TextInputEditText et_senha;
    private Button bt_confirmar;
    private Activity activity;
    private View dialog_view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog_view = getActivity().getLayoutInflater().inflate(R.layout.dialog_login, null);

        activity = getActivity();

        bt_confirmar = (Button) dialog_view.findViewById(R.id.bt_confirmar);

        et_login = (TextInputEditText) dialog_view.findViewById(R.id.edt_login);
        et_senha = (TextInputEditText) dialog_view.findViewById(R.id.edt_senha);

        inputLayoutLogin = (TextInputLayout) dialog_view.findViewById(R.id.input_layout_login);
        inputLayoutSenha = (TextInputLayout) dialog_view.findViewById(R.id.input_layout_senha);

        et_login.addTextChangedListener(new LoginWatcher(et_login));
        et_senha.addTextChangedListener(new LoginWatcher(et_senha));

        loginDialogBuilder = new AlertDialog.Builder(getActivity());
        loginDialogBuilder.setView(dialog_view);

        ArrayList<String> valores= this.getArguments().getStringArrayList("activity");
        final String activity = valores.get(0);
        final int tratamentoId;
        final int responsavelId;
        if(valores.size() == 2){
            tratamentoId = Integer.parseInt(valores.get(1));
            responsavelId = Integer.parseInt(valores.get(1));
        }else{
            tratamentoId = 0;
            responsavelId = 0;
        }
        bt_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificaCadastro()){
                    dismiss();
                    ModelLogin modelLogin = ServiceLogin.getLoginStatus(1);
                    modelLogin.setStatus(1);
                    ServiceLogin.update(modelLogin);
                    switch (activity){
                        case "NovoPaciente":{
                            startActivity(new Intent(getActivity().getApplicationContext(), PacienteNovoActivity.class));
                            break;
                        }
                        case "EditarPaciente":{
                            startActivity(new Intent(getActivity().getApplicationContext(), PacienteEditarActivity.class));
                            break;
                        }
                        case "Responsavel":{
                            startActivity(new Intent(getActivity().getApplicationContext(), ResponsavelNovoActivity.class));
                            break;
                        }
                        case "EditarResponsavel":{
                            Intent i = new Intent(getActivity().getApplicationContext(), ResponsavelEditarActivity.class);
                            i.putExtra("responsavelId", responsavelId);
                            startActivity(i);
                            break;
                        }
                        case "Tratamento":{
                            startActivity(new Intent(getActivity().getApplicationContext(), TratamentoNovoActivity.class));
                            break;
                        }
                        case "Medicamento":{
                            Intent i = new Intent(getActivity().getApplicationContext(), MedicamentoNovoActivity.class);
                            i.putExtra("tratamentoId", tratamentoId);
                            startActivity(i);
                            break;
                        }
                        case "Login":{
                            Toast.makeText(getActivity().getApplicationContext(),"Login realizado com sucesso",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Login ou senha incorreto",Toast.LENGTH_SHORT).show();
                }

            }
        });
        loginDialogBuilder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return loginDialogBuilder.create();
    }

    private boolean verificaCadastro() {
        if (!validaLogin() || !validaSenha()) {
            return false;
        }else{
            return true;
        }
    }
    private boolean validaLogin() {
        if (et_login.getText().toString().trim().isEmpty()) {
            inputLayoutLogin.setErrorEnabled(true);
            inputLayoutLogin.setError("Insira o login de acesso");
            return false;
        } else {
            inputLayoutLogin.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validaSenha() {
        if (et_senha.getText().toString().length() < 5 ){
            inputLayoutSenha.setErrorEnabled(true);
            inputLayoutSenha.setError("Mínimo de 5 caracteres");
            return false;
        } else {
            inputLayoutSenha.setErrorEnabled(false);
        }
        return true;
    }

    private class LoginWatcher implements TextWatcher {

        private View view;

        private LoginWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edt_login:
                    validaLogin();
                    break;
                case R.id.edt_senha:
                    validaSenha();
                    break;

            }
        }
    }

}
