package com.example.paulo.healthapp.Activity.Tratamento;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.paulo.healthapp.Fragment.LoginDialogFragment;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Fragment.TratamentoListFragment;
import com.example.paulo.healthapp.Service.ServiceLogin;
import com.example.paulo.healthapp.Service.ServicePaciente;

import java.util.ArrayList;


public class TratamentoListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tratamento_list_activity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TratamentoListFragment tratamento = new TratamentoListFragment();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.add(R.id.tratamento_layout, tratamento)
                .commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_tratamento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifica se existe um paciente cadastrado para inserir um novo tratamento
                if(ServicePaciente.getById(1).getNomePaciente() != null){
                    if(ServiceLogin.getLoginStatus(1).getStatus() == 0){
                        acessoLogin("Tratamento");
                    }else{
                        startActivity(new Intent(getApplicationContext(), TratamentoNovoActivity.class));
                    }
                    //Intent i = new Intent(getApplicationContext(), TratamentoNovoActivity.class);
                    //startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Paciente não cadastrado",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        TratamentoListFragment tratamento = new TratamentoListFragment();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.tratamento_layout, tratamento)
                .commit();

        super.onResume();
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
