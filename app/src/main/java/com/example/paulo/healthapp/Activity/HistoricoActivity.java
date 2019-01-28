package com.example.paulo.healthapp.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.example.paulo.healthapp.Fragment.HistoricoFragment;
import com.example.paulo.healthapp.Model.ModelHistoricoMedicamento;
import com.example.paulo.healthapp.Model.ModelTratamento;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceHistoricoMedicamento;
import com.example.paulo.healthapp.Service.ServiceTratamento;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HistoricoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CalendarView calendarViewHistorico = (CalendarView)findViewById(R.id.calendarView_historico);

        List<ModelTratamento> listTratamento = ServiceTratamento.getList();

        Calendar auxiliarInicial = Calendar.getInstance();
        auxiliarInicial.setTime(listTratamento.get(0).getDataInicio());

        Calendar auxiliarFinal = Calendar.getInstance();
        auxiliarFinal.setTime(listTratamento.get(0).getDataTermino());

        for(int i = 0; i < listTratamento.size();i++){
            if(auxiliarInicial.getTime().compareTo(listTratamento.get(i).getDataInicio()) > 0)
                auxiliarInicial.setTime(listTratamento.get(i).getDataInicio());

            if(auxiliarFinal.getTime().compareTo(listTratamento.get(i).getDataTermino()) < 0)
                auxiliarFinal.setTime(listTratamento.get(i).getDataTermino());
        }
        Date minDate = auxiliarInicial.getTime();
        Date maxDate = auxiliarFinal.getTime();

        calendarViewHistorico.setMinDate(minDate.getTime());
        calendarViewHistorico.setMaxDate(maxDate.getTime());
        calendarViewHistorico.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                Intent i = new Intent(getApplicationContext(), DetalhamentoActivity.class);
                i.putExtra("ano", year);
                i.putExtra("mes", month);
                i.putExtra("dia", dayOfMonth);
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
