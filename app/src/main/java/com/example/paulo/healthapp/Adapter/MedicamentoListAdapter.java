package com.example.paulo.healthapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paulo.healthapp.Activity.Medicamento.MedicamentoVisualizarActivity;
import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.R;

import java.text.SimpleDateFormat;
import java.util.List;


public class MedicamentoListAdapter extends RecyclerView.Adapter {

    private List<ModelMedicamento> listaMedicamentos;
    private Context context;

    public MedicamentoListAdapter(List<ModelMedicamento> listaMedicamentos, Context context) {
        this.listaMedicamentos = listaMedicamentos;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicamento, parent, false);
        MedicamentoListViewHolder holder = new MedicamentoListViewHolder(view,context);
        return holder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MedicamentoListViewHolder holder = (MedicamentoListViewHolder) viewHolder;
        ModelMedicamento medicamento = listaMedicamentos.get(position);

        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");

        String info = /*medicamento.getNomeMedicamento()*/ "Comprimido" + " (Tomar "
                        + medicamento.getQtdMedicamento() + ") "
                        + formato.format(medicamento.getHoraMedicamento());
        holder.info.setText(info);

       /* switch (medicamento.getCorMedicamento()){
            case "Amarelo":
                holder.cor.setImageResource(R.drawable.amarelo);
                break;
            case "Azul":
                holder.cor.setImageResource(R.drawable.azul);
                break;
            case "Verde":
                holder.cor.setImageResource(R.drawable.verde);
                break;
            case "Vermelho":
                holder.cor.setImageResource(R.drawable.coral);
                break;
            case "Rosa":
                holder.cor.setImageResource(R.drawable.rosa);
                break;
            case "Roxo":
                holder.cor.setImageResource(R.drawable.roxo);
                break;
        }*/

        final int medicamentoId = medicamento.getIdMedicamento();
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), MedicamentoVisualizarActivity.class);
                i.putExtra("medicamentoId",medicamentoId);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listaMedicamentos != null ? this.listaMedicamentos.size() : 0;
    }
}
