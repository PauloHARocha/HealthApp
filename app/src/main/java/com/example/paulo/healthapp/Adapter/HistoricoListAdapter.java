package com.example.paulo.healthapp.Adapter;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paulo.healthapp.Fragment.HistoricoFragment;
import com.example.paulo.healthapp.Model.ModelHistoricoMedicamento;
import com.example.paulo.healthapp.Model.ModelTratamento;
import com.example.paulo.healthapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HistoricoListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ModelHistoricoMedicamento> listaHistorico;

    public HistoricoListAdapter(List<ModelHistoricoMedicamento> listaHistorico,Context context) {
        this.context = context;
        this.listaHistorico = listaHistorico;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lista_historico, parent, false);
        HistoricoListViewHolder holder = new HistoricoListViewHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final HistoricoListViewHolder holder = (HistoricoListViewHolder) viewHolder;
        ModelHistoricoMedicamento historico = listaHistorico.get(position);

        Calendar data = Calendar.getInstance();
        data.setTime(historico.getHoraPrevista());

        String dia_da_semana = data.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT,Locale.getDefault());
        String mes = data.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault());

        String dia = String.valueOf(data.get(Calendar.DAY_OF_MONTH));


        holder.tv_data.setText(dia_da_semana + ", " + mes + " " + dia);

        if(historico.getQtdMedicamento() == historico.getQtdTomados())
            holder.iv_pilula.setImageResource(R.mipmap.pilula_verde);
        else
            holder.iv_pilula.setImageResource(R.mipmap.pilua_vermelha);

        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");

        String horaPrevista = "Hora prevista: " + formato.format(historico.getMedicamento().getHoraMedicamento());
        holder.tv_horaPrevista.setText(horaPrevista);

        String horaReal;
        if(historico.getHoraReal().getTime() == 0)
            horaReal = "Hora real: --:--";
        else
            horaReal = "Hora real: " + formato.format(historico.getHoraReal());
        holder.tv_horaReal.setText(horaReal);

        String qtdTomados = "Tomou " + historico.getQtdTomados() + "/" + historico.getQtdMedicamento();
        holder.tv_qtdTomados.setText(qtdTomados);
    }

    @Override
    public int getItemCount() {
        return this.listaHistorico != null ? this.listaHistorico.size() : 0;
    }

}
