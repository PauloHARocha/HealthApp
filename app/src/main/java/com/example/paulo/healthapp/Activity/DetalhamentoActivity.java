package com.example.paulo.healthapp.Activity;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;

import com.example.paulo.healthapp.Fragment.HistoricoFragment;
import com.example.paulo.healthapp.Model.ModelTratamento;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceTratamento;

import java.util.Calendar;

public class DetalhamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhamento_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Histórico");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HistoricoFragment historico = new HistoricoFragment();
       // Bundle bundle = new Bundle();
        //Calendar calendar = Calendar.getInstance();
        //calendar.set(getIntent().getIntExtra("ano",0), getIntent().getIntExtra("mes",0), getIntent().getIntExtra("dia",0));
        //bundle.putSerializable("data", calendar.getTime());
        //historico.setArguments(bundle);
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.add(R.id.detalhamento_layout, historico)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}

