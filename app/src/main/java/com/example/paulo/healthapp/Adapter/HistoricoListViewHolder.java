package com.example.paulo.healthapp.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paulo.healthapp.R;


public class HistoricoListViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_data;
    public TextView tv_horaPrevista;
    public TextView tv_horaReal;
    public TextView tv_qtdTomados;
    public ImageView iv_pilula;

    public HistoricoListViewHolder(View view, final Context context) {
        super(view);
        tv_data = (TextView) itemView.findViewById(R.id.lista_historico_tv_data);
        tv_horaPrevista = (TextView) itemView.findViewById(R.id.lista_historico_tv_horaPrevista);
        tv_horaReal = (TextView) itemView.findViewById(R.id.lista_historico_tv_horaReal);
        tv_qtdTomados = (TextView) itemView.findViewById(R.id.lista_historico_tv_qtdTomados);
        iv_pilula = (ImageView) itemView.findViewById(R.id.lista_historico_iv_pilula);
    }

}
