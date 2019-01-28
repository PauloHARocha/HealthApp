package com.example.paulo.healthapp.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.paulo.healthapp.Fragment.HistoricoFragment;
import com.example.paulo.healthapp.Model.ModelHistoricoMedicamento;
import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.Service.ServiceHistoricoMedicamento;
import com.example.paulo.healthapp.Service.ServiceMedicamento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Paulo on 25/08/2016.
 */
public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoFragment.ViewHolder> {
    private Context context;
    private List<ModelMedicamento> listaMedicamento;

    public HistoricoAdapter(Context context, List<ModelMedicamento> listaMedicamento) {
        this.context = context;
        this.listaMedicamento = listaMedicamento;
    }

    @Override
    public HistoricoFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoricoFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(final HistoricoFragment.ViewHolder holder, final int position) {
        ModelMedicamento medicamento = listaMedicamento.get(position);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dataInicio = formato.format(medicamento.getDataInicial());
        String dataFinal = formato.format(medicamento.getDataFinal());
        int dosesTomadas;
        String tipo_tratamento = medicamento.getTratamento().getNomeTratamento();
        if(Objects.equals(tipo_tratamento, "Fase Intensiva")){
            dosesTomadas = 60 -medicamento.getTratamento().getDoses();
        }else{
            dosesTomadas = 120 -medicamento.getTratamento().getDoses();
        }
        holder.tv_info.setText(/*medicamento.getNomeMedicamento() + " - " + */
                medicamento.getTratamento().getNomeTratamento() + " de " + dataInicio + " a " + dataFinal
                + '\n' + "Doses Tomadas: " + dosesTomadas);

        List<ModelHistoricoMedicamento> historicoMedicamentoList = ServiceHistoricoMedicamento.getListByIdMedicamento(medicamento.getIdMedicamento());

        final HistoricoListAdapter historicoListAdapter = new HistoricoListAdapter(historicoMedicamentoList, context);
        holder.rv_lista_historico.setAdapter(historicoListAdapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(context);
        holder.rv_lista_historico.setLayoutManager(layout);
    }

    @Override
    public int getItemCount() {
        return this.listaMedicamento != null ? this.listaMedicamento.size() : 0;
    }

}

