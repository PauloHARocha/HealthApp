package com.example.paulo.healthapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paulo.healthapp.Activity.Responsavel.ResponsavelEditarActivity;
import com.example.paulo.healthapp.Fragment.LoginDialogFragment;
import com.example.paulo.healthapp.Fragment.ResponsavelListFragment;
import com.example.paulo.healthapp.Model.ModelResponsavel;
import com.example.paulo.healthapp.Service.ServiceLogin;

import java.util.ArrayList;
import java.util.List;

public class ResponsavelListAdapter extends RecyclerView.Adapter<ResponsavelListFragment.ViewHolder> {
    private List<ModelResponsavel> listaResponsavel;
    private Context context;

    public ResponsavelListAdapter(Context context, List<ModelResponsavel> listaResponsavel) {
        this.listaResponsavel = listaResponsavel;
        this.context = context;
    }

    public void remove(int position) {
        listaResponsavel.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ResponsavelListFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ResponsavelListFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ResponsavelListFragment.ViewHolder holder, final int position) {
        holder.tv_nome.setText(listaResponsavel.get(position).getNomeResponsavel());
        holder.tv_cpf.setText(listaResponsavel.get(position).getCpf());
        holder.tv_telefone.setText(listaResponsavel.get(position).getTelefone());
        holder.tv_endereco.setText(listaResponsavel.get(position).getEndereco());

        holder.ib_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ServiceLogin.getLoginStatus(1).getStatus() == 0){
                    acessoLogin("EditarResponsavel", listaResponsavel.get(position).getIdResponsavel());
                }else{
                    Intent i = new Intent(context.getApplicationContext(), ResponsavelEditarActivity.class);
                    i.putExtra("responsavelId", listaResponsavel.get(position).getIdResponsavel());
                    context.startActivity(i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listaResponsavel != null ? this.listaResponsavel.size() : 0;
    }
    private void acessoLogin(String activity, int responsavelId){
        ArrayList<String> valores = new ArrayList<>();
        valores.add(activity);
        valores.add(responsavelId+"");
        Bundle args = new Bundle();
        args.putStringArrayList("activity", valores);

        android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.setArguments(args);
        loginDialogFragment.show(fm, null);
    }
}

