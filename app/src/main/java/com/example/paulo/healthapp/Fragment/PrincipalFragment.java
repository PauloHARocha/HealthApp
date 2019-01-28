package com.example.paulo.healthapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.paulo.healthapp.Adapter.PrincipalAdapter;
import com.example.paulo.healthapp.Model.ModelHistoricoMedicamento;
import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.R;
import com.example.paulo.healthapp.Service.ServiceHistoricoMedicamento;
import com.example.paulo.healthapp.Service.ServiceMedicamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PrincipalFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);

        List<ModelHistoricoMedicamento> listaHistoricoMedicamentoHelper = ServiceHistoricoMedicamento.getListByDate(new Date(),new Date());
        int tamanhoLista = listaHistoricoMedicamentoHelper != null ? listaHistoricoMedicamentoHelper.size() : 0;
        List<ModelHistoricoMedicamento> listaHistoricoMedicamento = new ArrayList<>();
        String etapa = this.getArguments().getString("etapa");
        switch (etapa){
            case "Manhã":{
                for(int i = 0; i <tamanhoLista ; i++){
                    ModelHistoricoMedicamento historicoMedicamento = listaHistoricoMedicamentoHelper.get(i);
                    if(historicoMedicamento.getMedicamento().getHoraMedicamento().getHours()>=8 && historicoMedicamento.getMedicamento().getHoraMedicamento().getHours()<12)
                        listaHistoricoMedicamento.add(historicoMedicamento);
                }
                break;
            }case "Tarde":{
                for(int i = 0; i <tamanhoLista ; i++){
                    ModelHistoricoMedicamento historicoMedicamento = listaHistoricoMedicamentoHelper.get(i);
                    if(historicoMedicamento.getMedicamento().getHoraMedicamento().getHours()>=12 && historicoMedicamento.getMedicamento().getHoraMedicamento().getHours()<18)
                        listaHistoricoMedicamento.add(historicoMedicamento);
                }
                break;
            }
            case "Noite":{
                for(int i = 0; i <tamanhoLista ; i++){
                    ModelHistoricoMedicamento historicoMedicamento = listaHistoricoMedicamentoHelper.get(i);
                    if(historicoMedicamento.getMedicamento().getHoraMedicamento().getHours()>=18 && historicoMedicamento.getMedicamento().getHoraMedicamento().getHours()<24)
                        listaHistoricoMedicamento.add(historicoMedicamento);
                }
                break;
            }
        }

        PrincipalAdapter adapter = new PrincipalAdapter(recyclerView.getContext(),listaHistoricoMedicamento);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView info_medicamento;
        //public ImageButton confirmar;
        public LinearLayout layout_background;
        public RecyclerView recyclerView;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_principal,parent, false));
            info_medicamento = (TextView) itemView.findViewById(R.id.principal_txv_medicamento);
            //confirmar = (ImageButton) itemView.findViewById(R.id.principal_ibtn_sel_all);
            layout_background = (LinearLayout) itemView.findViewById(R.id.principal_ll_layout);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_principal);

        }

    }
}