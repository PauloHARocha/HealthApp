package com.example.paulo.healthapp.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paulo.healthapp.Activity.Medicamento.MedicamentoNovoActivity;
import com.example.paulo.healthapp.Fragment.LoginDialogFragment;
import com.example.paulo.healthapp.Model.ModelMedicamento;
import com.example.paulo.healthapp.Model.ModelTratamento;
import com.example.paulo.healthapp.Fragment.TratamentoListFragment;
import com.example.paulo.healthapp.Service.ServiceLogin;
import com.example.paulo.healthapp.Service.ServiceMedicamento;

import java.util.ArrayList;
import java.util.List;


public class TratamentoListAdapter extends RecyclerView.Adapter<TratamentoListFragment.ViewHolder> {
    private List<ModelTratamento> listaTratamento;
    private Context context;

    public TratamentoListAdapter(Context context, List<ModelTratamento> listaTratamento) {
        this.listaTratamento = listaTratamento;
        this.context = context;

    }

    @Override
    public TratamentoListFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TratamentoListFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(TratamentoListFragment.ViewHolder holder, final int position) {
        List<ModelMedicamento> listaMedicamentos = ServiceMedicamento.getListByTratamento(position + 1);
        //adiciona a lista de medicamentos de cada tratamento
        holder.recyclerView.setAdapter(new MedicamentoListAdapter(listaMedicamentos,context));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.recyclerView.setLayoutManager(layout);

        holder.tv_nome.setText(listaTratamento.get(position).getNomeTratamento());
        holder.ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ServiceLogin.getLoginStatus(1).getStatus() == 0){
                    acessoLogin("Medicamento", listaTratamento.get(position).getIdTratamento());
                }else{
                    Intent i = new Intent(context.getApplicationContext(), MedicamentoNovoActivity.class);
                    i.putExtra("tratamentoId", listaTratamento.get(position).getIdTratamento());
                    context.startActivity(i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listaTratamento != null ? this.listaTratamento.size() : 0;
    }

    private void acessoLogin(String activity, int tratamentoId){
        ArrayList<String> valores = new ArrayList<>();
        valores.add(activity);
        valores.add(tratamentoId+"");
        Bundle args = new Bundle();
        args.putStringArrayList("activity", valores);

        android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.setArguments(args);
        loginDialogFragment.show(fm, null);
    }
}

