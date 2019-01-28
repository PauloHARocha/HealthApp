package com.example.paulo.healthapp.Activity.Responsavel;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.paulo.healthapp.Fragment.LoginDialogFragment;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Fragment.ResponsavelListFragment;
import com.example.paulo.healthapp.Service.ServiceLogin;

import java.util.ArrayList;


public class ResponsavelListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responsavel_list_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //inicializa e insere um fragment com a lista de responsaveis na activity
        ResponsavelListFragment responsavel = new ResponsavelListFragment();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.add(R.id.responsavel_layout, responsavel)
                    .commit();
        // abre a activity de novo responsavel se o botao for clicado
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_responsavel);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ServiceLogin.getLoginStatus(1).getStatus() == 0){
                    acessoLogin("Responsavel");
                }else{
                    startActivity(new Intent(getApplicationContext(), ResponsavelNovoActivity.class));
                }

                //Intent i = new Intent(getApplicationContext(), ResponsavelNovoActivity.class);
                //startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        // atualiza o fragment da lista de responsaveis
        ResponsavelListFragment responsavel = new ResponsavelListFragment();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.responsavel_layout, responsavel)
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
